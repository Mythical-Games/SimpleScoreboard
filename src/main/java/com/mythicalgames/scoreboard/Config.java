package com.mythicalgames.scoreboard;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import java.util.List;

@Header({
    "###############################################",
    "# Thank you for downloading SimpleScoreboards",
    "# You are now part of our Mythical Ecosystem",
    "###############################################"
})
public class Config extends OkaeriConfig {

    @Comment({
        "How often scoreboard should be updated in ticks (20 ticks = 1 second)",
        "0 = disabled, ENABLE THIS IF YOU USE PLACEHOLDERS"
    })
    public int update = 0;

    @Comment("Scoreboard title")
    public String title = "§l§bHeavenPE §fNetwork";

    @Comment({
        "Scoreboard lines",
        "You can use § for colors and placeholders from PlaceholderAPI",
        "https://github.com/AllayMC/PlaceholderAPI",
        "Note: two lines can't display the same text"
    })
    public List<String> text = List.of(
        "§l§7-------------",
        "§l§bMythicalGames",
        "§l§8| §l§7Ｇａｍｅｍｏｄｅ: §r§c{game_mode}",
        "§l§8| §l§7Ｏｓ: §r§f{device_os}",
        "§l§8| §l§7ʟᴏʙʙʏ: §r§f#1",
        "§l",
        "§l§bServers",
        "§l§6⚔ §l§7Factions: §r§b✔",
        "§l§c❤ §l§7Lifesteal: §r§b✔",
        "§l§e⚡ §l§7Bedwars: §r§b✔",
        " ",
        "§r§l§7-------------",
        "§bplay.heavenpe.net"
    );

    @Comment("List of worlds where the scoreboard should be disabled")
    public List<String> noScoreboardWorlds = List.of("exampleWorld");
}
