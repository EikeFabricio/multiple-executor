package net.eike.plugins.mpe;

import net.eike.plugins.mpe.api.loader.CommandLoader;
import net.eike.plugins.mpe.api.messages.Message;
import net.eike.plugins.mpe.commands.ExecuteCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class MultipleExecutor extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();

        saveDefaultConfig();

        CommandLoader commandLoader = new CommandLoader(this);

        Message message = new Message(getConfig());

        new ExecuteCommand(this, commandLoader, message);
    }

}
