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
    }

    private FileConfiguration getConfig() {
        return config;
    }

    private String from(String path) {
        return getConfig().contains("Messages." + path) ?
               getConfig().getString("Messages." + path, "").replace('&', '§')
               : "that path doesn't exists";
    }

    public final String INTERNAL_ERROR;
    public final String INSUFFICIENT_PERMISSIONS;
    public final String PARAMS_REQUIRED;
    public final String NO_PERMISSION;
    public final String SUCCESS;
    public final String NO_ID;

}
