package net.eike.plugins.mpe.commands.player;

import org.bukkit.entity.Player;

public class StatePlayer {

    private final Player player;
    private final String commandId;

    public StatePlayer(Player player, String commandId) {
        this.player = player;
        this.commandId = commandId;
    }

    public Player getPlayer() {
        return player;
    }

    public String getCommandId() {
        return commandId;
    }

}
