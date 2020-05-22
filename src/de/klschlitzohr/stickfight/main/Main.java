package de.klschlitzohr.stickfight.main;

import de.klschlitzohr.stickfight.commands.CommandStickfightExecutor;
import de.klschlitzohr.stickfight.commands.TabComplete;
import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.listner.*;
import de.klschlitzohr.stickfight.message.console.ConsoleMessageBuilder;
import de.klschlitzohr.stickfight.message.language.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;

    private GameManager gameManager;

    private LanguageManager languageManager;
    @Override
    public void onEnable() {
        plugin = this;

        languageManager = new LanguageManager();
        languageManager.load();

        gameManager = new GameManager();

        PluginCommand sfCommand = getCommand("stickfight");

        sfCommand.setExecutor(new CommandStickfightExecutor());
        sfCommand.setTabCompleter(new TabComplete());

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerLeaveListner(), this);
        pluginManager.registerEvents(new PlayerMoveListner(), this);
        pluginManager.registerEvents(new DamageListner(), this);
        pluginManager.registerEvents(new BlockBreakPlaceListner(), this);
        pluginManager.registerEvents(new FoodListner(), this);
        pluginManager.registerEvents(new DropListner(), this);


        Setup.setup();

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

    public static void setPlugin(Main plugin) {
        Main.plugin = plugin;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

}
