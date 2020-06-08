package net.eike.plugins.mpe.api.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MPECommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public MPECommand(String name, JavaPlugin javaPlugin) {
        this.plugin = javaPlugin;

        getPlugin().getCommand(name).setExecutor(this);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

}
