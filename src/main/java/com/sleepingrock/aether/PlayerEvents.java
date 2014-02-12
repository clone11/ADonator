package com.sleepingrock.aether;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Chris on 2/9/14.
 */
public class PlayerEvents implements Listener {

    public ADonate plugin;
    public PlayerEvents(ADonate plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.dbConnection.addPlayer(event.getPlayer().getName());
    }
}
