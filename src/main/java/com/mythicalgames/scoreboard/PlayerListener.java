package com.mythicalgames.scoreboard;

import org.allaymc.api.player.Player;
import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.server.PlayerJoinEvent;
import org.allaymc.api.eventbus.event.server.PlayerQuitEvent;
//import org.allaymc.api.eventbus.event.player.PlayerLevelChangeEvent;

public class PlayerListener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerScoreboardManager.create(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerScoreboardManager.remove(player);
    }

    // TO HANDLE MULTIPLE WORLD SOON.
}

