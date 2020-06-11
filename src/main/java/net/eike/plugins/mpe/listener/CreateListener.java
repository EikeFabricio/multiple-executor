package net.eike.plugins.mpe.listener;

import net.eike.plugins.mpe.api.MultipleCommand;
import net.eike.plugins.mpe.api.messages.Message;
import net.eike.plugins.mpe.commands.CreateSubCommand;
import net.eike.plugins.mpe.commands.player.StatePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateListener implements Listener {

    private final CreateSubCommand command;
    private final Map<Player, MultipleCommand> map;

    public CreateListener(CreateSubCommand createSubCommand) {
        this.command = createSubCommand;
        this.map = new HashMap<>();
    }

    public CreateSubCommand getCommand() {
        return command;
    }

    public Map<Player, MultipleCommand> getMap() {
        return map;
    }

    @EventHandler
    void on(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        StatePlayer statePlayer = getCommand()
                .getQueuedPlayers()
                .keySet()
                .stream()
                .filter(sp -> sp.getPlayer() == player)
                .findFirst()
                .orElse(null);

        if (statePlayer == null) return;

        int state = getCommand().getQueuedPlayers().get(statePlayer);

        String msg = e.getMessage();
        MultipleCommand multipleCommand = map.getOrDefault(player,
                new MultipleCommand(
                        new ArrayList<>(),
                        new ArrayList<>(),
                        statePlayer.getCommandId()
                ) {});

        // Permission state
        if (state == 0) {
            if (!msg.equalsIgnoreCase(".end")) {
                multipleCommand.setPermission(msg);
            }

        }

        // Params state
        if (state == 1) {
            List<String> params = multipleCommand.getParams();

            if (!msg.equalsIgnoreCase(".end")) {
                params.add(msg);
            } else {
                state++;
            }

            multipleCommand.setParams(params);
        }

        // Commands state
        if (state == 2) {
            List<String> commands = multipleCommand.getCommands();

            if (!msg.equalsIgnoreCase(".end")) {
                commands.add(msg);
            } else {
                state++;
            }

            multipleCommand.setCommands(commands);
        }

        if (msg.equalsIgnoreCase(".end")) {
            state++;

            if (state < 3) {
                try {
                    player.sendMessage(getMessageByState(getCommand().getMessage(), state));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (state >= 3) {
            if (msg.equalsIgnoreCase(".confirm")) {
                player.sendMessage(getCommand().getMessage().COMMAND_CREATED);

                Map<String, Object> map = new HashMap<>();

                map.put("ID", multipleCommand.getId());
                map.put("Params", multipleCommand.getParams());
                map.put("Commands", multipleCommand.getCommands());

                if (!multipleCommand.getPermission().equals("mpe.execute"))
                    map.put("Permission", multipleCommand.getPermission());

                setAsMap("Commands." + multipleCommand.getId(), map);
                getCommand().getCommandLoader().getMultipleCommands().add(multipleCommand);
            } else {
                player.sendMessage(getCommand().getMessage().CANCELLED);
            }

            map.remove(player);
            getCommand().getQueuedPlayers().remove(statePlayer);
        }

    }

    private String getMessageByState(Message message, int state) throws NoSuchFieldException {
        return message.getClass().getField("STATE_" + state).toString();
    }

    private void setAsMap(String defaultPath, Map<String, Object> map) {
        for (Map.Entry entry : map.entrySet()) {
            getCommand().getPlugin().getConfig().set(defaultPath + "." + entry.getKey(), entry.getValue());
        }

        getCommand().getPlugin().saveConfig();
    }
}
