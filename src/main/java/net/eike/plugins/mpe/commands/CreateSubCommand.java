package net.eike.plugins.mpe.commands;

import net.eike.plugins.mpe.api.MultipleCommand;
import net.eike.plugins.mpe.api.command.MPECommand;
import net.eike.plugins.mpe.api.loader.CommandLoader;
import net.eike.plugins.mpe.api.messages.Message;
import net.eike.plugins.mpe.commands.player.StatePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CreateSubCommand extends MPECommand {

    private CommandLoader commandLoader;
    private Message message;
    private Map<StatePlayer, Integer> queuedPlayers;

    public CreateSubCommand(JavaPlugin javaPlugin, CommandLoader commandLoader, Message message) {
        super("mpe", javaPlugin, "create", true, false);

        this.commandLoader = commandLoader;
        this.message = message;
        this.queuedPlayers = new HashMap<>();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou must be a player to executed this command.");
            return;
        }

        Player p = (Player) sender;

        if (args.length != 2) {
            p.sendMessage("§cDid you mean /mpe create (new ID)?");

            return;
        }

        String id = args[1];

        MultipleCommand multipleCommand = commandLoader.getMultipleCommands()
                .stream()
                .filter(mpc -> mpc.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (multipleCommand != null) {
            p.sendMessage(message.EXISTENT_ID);

            return;
        }

        /*
        0 = Permission state
        1 = Params state
        2 = Commands state
        3 = Create state
         */
        queuedPlayers.put(new StatePlayer(p, id), 0);

        p.sendMessage(message.STATE_0);
    }

    public Map<StatePlayer, Integer> getQueuedPlayers() {
        return queuedPlayers;
    }

    public Message getMessage() {
        return message;
    }

    public CommandLoader getCommandLoader() {
        return commandLoader;
    }

}
