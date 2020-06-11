package net.eike.plugins.mpe.commands;

import net.eike.plugins.mpe.api.MultipleCommand;
import net.eike.plugins.mpe.api.command.MPECommand;
import net.eike.plugins.mpe.api.loader.CommandLoader;
import net.eike.plugins.mpe.api.messages.Message;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ExecuteCommand extends MPECommand {

    private final CommandLoader commandLoader;
    private final Message message;

    public ExecuteCommand(JavaPlugin javaPlugin, CommandLoader commandLoader, Message message) {
        super("mpe", javaPlugin, null, false, false);

        this.commandLoader = commandLoader;
        this.message = message;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Message message = getMessage();

        if (!sender.hasPermission("mpe.execute")) {
            sender.sendMessage(message.NO_PERMISSION);
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(message.NO_ID);
            return;
        }

        List<MultipleCommand> commands = getCommandLoader().getMultipleCommands();

        MultipleCommand multipleCommand = commands
                .stream()
                .filter(mpc -> mpc.getId()
                        .equals(args[0]))
                .findFirst()
                .orElse(null);

        if (multipleCommand == null) {
            sender.sendMessage(message.NO_ID);

            return ;
        }

        List<String> params = multipleCommand.getParams();

        if (!params.isEmpty()) {
            if ((args.length - 1) != params.size()) {
                sender.sendMessage(message.PARAMS_REQUIRED.replace("{params}", listToString(params)));
                return;
            }
        }

        if (!sender.hasPermission(multipleCommand.getPermission())) {
            sender.sendMessage(message.INSUFFICIENT_PERMISSIONS);
            return;
        }

        for (String cmd : multipleCommand.getCommands()) {
            try {
                cmd = cmd.startsWith("/") ? cmd.substring(1) : cmd;
                String[] commandParams = cmd.split(" ");
                String[] trueParams = StringUtils.join(args, " ", 1, args.length).split(" ");

                for (int param = 0; param < params.size(); param++) {
                    for (String par : commandParams) {
                        if (par.equals(params.get(param))) {
                            cmd = cmd.replace(par, trueParams[param]);
                        }
                    }
                }

                Bukkit.dispatchCommand(sender, cmd);
            } catch (Exception e) {
                sender.sendMessage(message.INTERNAL_ERROR);

                e.printStackTrace();
            }
        }

        sender.sendMessage(message.SUCCESS);
    }

    private CommandLoader getCommandLoader() {
        return commandLoader;
    }

    private Message getMessage() {
        return message;
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
