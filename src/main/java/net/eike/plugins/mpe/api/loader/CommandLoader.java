package net.eike.plugins.mpe.api.loader;

import net.eike.plugins.mpe.api.MultipleCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandLoader {

    private final FileConfiguration file;
    private final List<MultipleCommand> multipleCommands;

    public CommandLoader(JavaPlugin javaPlugin) {
        this.file = javaPlugin.getConfig();

        this.multipleCommands = load();
    }

    private List<MultipleCommand> load() {
        List<MultipleCommand> commands = new ArrayList<>();

        for (String key : getFile().getConfigurationSection("Commands").getKeys(false)) {
            String path = "Commands." + key + ".";

            List<String> params = (ArrayList<String>) valueOrAbsent(path + "Params", new ArrayList<>());
            List<String> cmds = (ArrayList<String>) valueOrAbsent(path + "Commands", new ArrayList<>());
            String id = (String) Objects.requireNonNull(valueOrAbsent(path + "ID", null));
            String permission = (String) valueOrAbsent(path + "Permission", "mpe.execute");

            commands.add(new MultipleCommand(cmds, permission, params, id) {});

            System.out.println("Command " + id + " loaded!");
        }

        return commands;
    }

    public List<MultipleCommand> getMultipleCommands() {
        return multipleCommands;
    }

    private FileConfiguration getFile() {
        return file;
    }

    private Object valueOrAbsent(String path, Object absent) {
        return getFile().contains(path) ? getFile().get(path) : absent;
    }
}
