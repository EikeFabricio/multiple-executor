package net.eike.plugins.mpe.commands;

import net.eike.plugins.mpe.api.MultipleCommand;
import net.eike.plugins.mpe.api.command.MPECommand;
import net.eike.plugins.mpe.api.loader.CommandLoader;
import net.eike.plugins.mpe.api.messages.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListSubCommand extends MPECommand {

    private final CommandLoader commandLoader;
    private final Message message;

    public ListSubCommand(JavaPlugin javaPlugin, CommandLoader commandLoader, Message message) {
        super("mpe", javaPlugin, "list", true, false);

        this.commandLoader = commandLoader;
        this.message = message;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String FORMAT = message.LIST_FORMAT;

        List<String> COMMANDS = new ArrayList<>();

        for (MultipleCommand command : commandLoader.getMultipleCommands()) {
            Map<String, String> map = new HashMap<>();

            map.put("<params>", listToString(command.getParams()));
            map.put("<permission>", command.getPermission());
            map.put("<id>", command.getId());
            map.put("<commands>", listToString(command.getCommands()));

            COMMANDS.add(replaceAsMap(FORMAT, map));
        }

        sender.sendMessage(new String[] {message.AVAILABLE_COMMANDS, ""});

        COMMANDS.forEach(sender::sendMessage);
    }

    private String replaceAsMap(String s, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            s = s.replace(entry.getKey(), entry.getValue());
        }

        return s;
    }


    private String listToString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int x = 0; x < stringList.size(); x++) {
            stringBuilder.append(stringList.get(x));

            if ((x + 1) < stringList.size()) stringBuilder.append(",").append(" ");
        }

        return stringBuilder.toString().trim();
    }
}
