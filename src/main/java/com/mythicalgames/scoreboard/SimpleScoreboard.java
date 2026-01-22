package com.mythicalgames.scoreboard;

import org.allaymc.api.player.Player;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.server.Server;
import org.allaymc.api.scheduler.Task;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleScoreboard extends Plugin {

    public static SimpleScoreboard INSTANCE;
    public Config CONFIG;

    @Override
    public void onLoad() {
        INSTANCE = this;
        log.info("Loading configuration file...");
        CONFIG = ConfigManager.create(Config.class, config -> {
            config.withConfigurer(new YamlSnakeYamlConfigurer());
            config.withBindFile(pluginContainer.dataFolder().resolve("config.yml"));
            config.withRemoveOrphans(true);
            config.saveDefaults();
            config.load(true);
        });
    }

    @Override
public void onEnable() {
    Server.getInstance().getEventBus().registerListener(new PlayerListener());

    int updateInterval = CONFIG.update;

    if (updateInterval > 0) {
        log.info("Starting scoreboard updater every {} ticks.", updateInterval);

        Server.getInstance().getScheduler().scheduleRepeating(INSTANCE, new Task() {
                @Override
                public boolean onRun() {
                for (Player player : Server.getInstance().getPlayerManager().getPlayers().values()) {
                    if (player == null || player.getControlledEntity().getWorld() == null || !player.isConnected()) {
                        continue;
                    }
                    PlayerScoreboardManager.update(player);
                }
                return true;
            }
            },
            updateInterval, true
        );
    } else {
        log.info("Scoreboard auto-updating is disabled (update=0 in config).");
    }

        log.info("SimpleScoreboard has been enabled!");
    }

    @Override
    public void onDisable() {
        log.info("SimpleScoreboard has been disabled!");
    }
}
