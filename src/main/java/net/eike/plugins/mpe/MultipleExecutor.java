package net.eike.plugins.mpe;

import net.eike.plugins.mpe.api.loader.CommandLoader;
import net.eike.plugins.mpe.api.messages.Message;
import net.eike.plugins.mpe.commands.CreateSubCommand;
import net.eike.plugins.mpe.commands.ExecuteCommand;
import net.eike.plugins.mpe.commands.ListSubCommand;
import net.eike.plugins.mpe.listener.CreateListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MultipleExecutor extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();

        saveDefaultConfig();

        CommandLoader commandLoader = new CommandLoader(this);
        Message message = new Message(getConfig());

        new ExecuteCommand(this, commandLoader, message);
        new ListSubCommand(this, commandLoader, message);

        CreateSubCommand subCommand = new CreateSubCommand(this, commandLoader, message);

        getServer().getPluginManager().registerEvents(new CreateListener(subCommand), this);
    }

}
