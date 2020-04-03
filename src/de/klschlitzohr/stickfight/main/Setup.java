package de.klschlitzohr.stickfight.main;

import de.klschlitzohr.stickfight.game.Arena;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class Setup {

    public static void setup() {
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//Games.yml"));
        List<String> games = cfg.getStringList("Av-Games");
        for (String gamename : games) {
            Arena game = new Arena(gamename);
        }
    }

}
