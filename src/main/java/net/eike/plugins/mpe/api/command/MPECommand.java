package net.eike.plugins.mpe.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MPECommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private boolean subCommand;
    private boolean required;
    private String subName;

    public MPECommand(String name, JavaPlugin javaPlugin, String subName, boolean subCommand, boolean required) {
        this.plugin = javaPlugin;
        this.subName = subName;
        this.subCommand = subCommand;
        this.required = required;

        if (!subCommand) getPlugin().getCommand(name).setExecutor(this);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public abstract void execute(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!subCommand) {
            execute(sender, args);

            return true;
        }

        if (args.length == 0) {
            if (required) {
                sender.sendMessage("§cUse /mpe " + subName + ".");
            } else {
                execute(sender, args);
            }

            return true;
        }

        if (args[0].equals(subName)) {
            execute(sender, args);
        }

        return false;
    }
}
