package de.klschlitzohr.stickfight.main;

import de.klschlitzohr.stickfight.commands.CommandStickfightExecutor;
import de.klschlitzohr.stickfight.commands.TabComplete;
import de.klschlitzohr.stickfight.game.Arena;
import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.listner.*;
import de.klschlitzohr.stickfight.message.console.ConsoleMessageBuilder;
import de.klschlitzohr.stickfight.message.language.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;

public class Main extends JavaPlugin {

    public static Main plugin;

    private GameManager gameManager;

    private LanguageManager languageManager;

    @Override
    public void onEnable() {
        plugin = this;

        languageManager = new LanguageManager();
        languageManager.load();

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Setup.setup();
                Main.getPlugin().setGameManager(new GameManager());

                PluginCommand sfCommand = getCommand("stickfight");

                sfCommand.setExecutor(new CommandStickfightExecutor());
                sfCommand.setTabCompleter(new TabComplete());

                PluginManager pluginManager = Bukkit.getPluginManager();

                pluginManager.registerEvents(new PlayerLeaveListner(), Main.getPlugin());
                pluginManager.registerEvents(new PlayerMoveListner(), Main.getPlugin());
                pluginManager.registerEvents(new DamageListner(), Main.getPlugin());
                pluginManager.registerEvents(new BlockBreakPlaceListner(), Main.getPlugin());
                pluginManager.registerEvents(new FoodListner(), Main.getPlugin());
                pluginManager.registerEvents(new DropListner(), Main.getPlugin());
            }
        };
        runnable.runTaskLater(Main.getPlugin(), 1L);

        languageManager.checkResources();

        new ConsoleMessageBuilder("Das Stickfight Plugin ist aktiviert.", true).send();
    }
    @Override
    public void onDisable() {
        if (languageManager.getMessages() != null)
            new ConsoleMessageBuilder("Das Stickfight Plugin wurde deaktiviert.", true).send();
    }


    public static Main getPlugin() {
        return plugin;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

}
