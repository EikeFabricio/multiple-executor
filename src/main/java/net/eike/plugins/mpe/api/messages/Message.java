package net.eike.plugins.mpe.api.messages;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class Message {

    private final FileConfiguration config;

    public Message(FileConfiguration config) {
        this.config = Objects.requireNonNull(config);

        INTERNAL_ERROR = from("Error.INTERNAL");
        INSUFFICIENT_PERMISSIONS = from("Error.INSUFFICIENT_PERMISSIONS");
        PARAMS_REQUIRED = from("Error.PARAMS_REQUIRED");
        NO_PERMISSION = from("Error.NO_PERMISSION");
        SUCCESS = from("Success.DONE");
        NO_ID = from("Error.NO_ID");
        LIST_FORMAT = from("Misc.LIST_FORMAT");
        AVAILABLE_COMMANDS = from("Misc.AVAILABLE_COMMANDS");
        COMMAND_CREATED = from("Success.CREATED");
        EXISTENT_ID = from("Error.EXISTENT_ID");
        STATE_0 = from("State.0");
        STATE_1 = from("State.1");
        STATE_2 = from("State.2");
        STATE_3 = from("State.3");
        CANCELLED = from("Success.CANCELLED");
    }

    private FileConfiguration getConfig() {
        return config;
    }

    private String from(String path) {
        FileConfiguration file = getConfig();

        return file.contains("Messages." + path) ?
               file.getString("Messages." + path, "").replace('&', '§')
               : "that path doesn't exists, is it a old version?";
    }

    public final String INTERNAL_ERROR;
    public final String INSUFFICIENT_PERMISSIONS;
    public final String PARAMS_REQUIRED;
    public final String NO_PERMISSION;
    public final String SUCCESS;
    public final String NO_ID;
    public final String LIST_FORMAT;
    public final String AVAILABLE_COMMANDS;
    public final String COMMAND_CREATED;
    public final String EXISTENT_ID;
    public final String STATE_0;
    public final String STATE_1;
    public final String STATE_2;
    public final String STATE_3;
    public final String CANCELLED;

}
