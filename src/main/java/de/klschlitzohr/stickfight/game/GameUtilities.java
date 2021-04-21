package de.klschlitzohr.stickfight.game;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameUtilities {

    public static ArrayList<Arena> loadArenas() {
        ArrayList<Arena> arenas = new ArrayList<>();
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//Games.yml"));
        List<String> games = cfg.getStringList("available-games");
        for (String gamename : games) {
            Arena arena = new Arena(gamename);
            arenas.add(arena);
        }
        return arenas;
    }
}
