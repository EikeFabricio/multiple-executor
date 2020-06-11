package net.eike.plugins.mpe.api;

import java.util.List;

public abstract class MultipleCommand {

    private List<String> commands, params;
    private String permission, id;

    public MultipleCommand(List<String> commands, List<String> params, String id) {
        this.commands = commands;
        this.params = params;
        this.permission = "mpe.execute";
        this.id = id;
    }

    public MultipleCommand(List<String> commands, String permission, List<String> params, String id) {
        this.commands = commands;
        this.permission = permission;
        this.params = params;
        this.id = id;
    }

    public List<String> getCommands() {
        return commands;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getParams() {
        return params;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
