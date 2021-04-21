package de.klschlitzohr.stickfight.main;

import de.klschlitzohr.stickfight.game.Arena;
import de.klschlitzohr.stickfight.message.console.ConsoleMessageBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class Setup {

    public static void setup() {
        new ConsoleMessageBuilder("Loading Game:", true).send();
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//Games.yml"));
        List<String> games = cfg.getStringList("available-games");
        for (String gamename : games) {
            Arena game = new Arena(gamename);
            new ConsoleMessageBuilder(" - " + game.getName(), true).send();
        }
        new ConsoleMessageBuilder("GamesLoaded!", true).send();
    }
}
