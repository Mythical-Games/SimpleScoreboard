package com.mythicalgames.scoreboard;

import org.allaymc.api.player.Player;
import org.allaymc.api.scoreboard.Scoreboard;
import org.allaymc.api.scoreboard.data.DisplaySlot;
import org.allaymc.api.server.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerScoreboardManager {

    private static final Map<String, Scoreboard> scoreboards = new HashMap<>();

    public static void create(Player player) {
        if (shouldDisableInWorld(player)) return;

        Config config = SimpleScoreboard.INSTANCE.CONFIG;
        String title = config.title;

        if (isPlaceholderApiLoaded()) {
            try {
                title = org.allaymc.papi.PlaceholderAPI.getAPI().setPlaceholders(player.getControlledEntity(), title);
                if (title == null || title.isBlank()) title = config.title;
            } catch (Exception e) {
                title = config.title;
            }
        }

        Scoreboard scoreboard = new Scoreboard(title);
        scoreboards.put(player.getOriginName(), scoreboard);
        scoreboard.addViewer(player, DisplaySlot.SIDEBAR);

        update(player);
    }

    public static void remove(Player player) {
        Player nrmalplayer = (Player) player;
        Scoreboard scoreboard = scoreboards.remove(nrmalplayer.getOriginName());
        if (scoreboard != null) {
            scoreboard.removeViewer(nrmalplayer, DisplaySlot.SIDEBAR);
        }
    }

    public static boolean hasScoreboard(Player player) {
        Player nrmalplayer = (Player) player;
        return scoreboards.containsKey(nrmalplayer.getOriginName());
    }

    public static void update(Player player) {
        Player nrmalplayer = (Player) player;
        if (shouldDisableInWorld(player)) {
            remove(player);
            return;
        }

        Scoreboard scoreboard = scoreboards.get(nrmalplayer.getOriginName());
        if (scoreboard == null) {
            create(player);
            return;
        }

        Config config = SimpleScoreboard.INSTANCE.CONFIG;

        List<String> processedLines;

        if (isPlaceholderApiLoaded()) {
            try {
                processedLines = config.text.stream()
                    .map(line -> {
                        try {
                            String replaced = org.allaymc.papi.PlaceholderAPI.getAPI().setPlaceholders(player.getControlledEntity(), line);
                            return (replaced == null || replaced.isBlank()) ? line : replaced;
                        } catch (Exception e) {
                            return line;
                        }
                    })
                    .filter(line -> !line.isBlank())
                    .distinct()
                    .collect(Collectors.toList());
            } catch (Exception e) {
                processedLines = config.text;
            }
        } else {
            processedLines = config.text;
        }

        scoreboard.setLines(processedLines);
    }

    private static boolean shouldDisableInWorld(Player player) {
        String worldName = player.getControlledEntity().getWorld().getName();
        return SimpleScoreboard.INSTANCE.CONFIG.noScoreboardWorlds.contains(worldName);
    }

    private static boolean isPlaceholderApiLoaded() {
        return Server.getInstance().getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
}
