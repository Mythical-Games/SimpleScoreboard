package com.mythicalgames.scoreboard;

import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.player.PlayerJoinEvent;
import org.allaymc.api.eventbus.event.player.PlayerQuitEvent;
//import org.allaymc.api.eventbus.event.player.PlayerLevelChangeEvent;

public class PlayerListener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        EntityPlayer player = event.getPlayer();
        PlayerScoreboardManager.create(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        EntityPlayer player = event.getPlayer();
        PlayerScoreboardManager.remove(player);
    }

    // TO HANDLE MULTIPLE WORLD SOON.
}

