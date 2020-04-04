package de.klschlitzohr.stickfight.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private HashMap<Player, Location> lastLocation = new HashMap<>();

    private ArrayList<Arena> allArena;

    private ArrayList<Arena> activeArenas;

    private ArrayList<Arena> avivableArenas;

    public GameManager() {
       allArena = GameUtilities.loadArenas();
       activeArenas = new ArrayList<>();
       avivableArenas = new ArrayList<>(allArena);
    }

    public boolean joinArenaName(String name, Player player) {
        for (Arena arena : avivableArenas) {
            if (arena.getName().equalsIgnoreCase(name)) {
                    arena.joinarena(player);
                    if (arena.isfull()) {
                        avivableArenas.remove(arena);
                        activeArenas.add(arena);
                        for (Player player1 : arena.getPlayersinarena().keySet()) {
                            lastLocation.put(player1, player1.getLocation());
                            player1.setHealth(player1.getHealthScale());
                            player1.setFoodLevel(20);
                        }
                        arena.startArena();
                    }
                    return true;
            }
        }
        return false;
    }

    public void addArena(String name) {
        Arena arena = new Arena(name);
        allArena.add(arena);
        avivableArenas.add(arena);
    }

    public void playerLeave(Player player, boolean serverLeave) {
        for (Arena arena : allArena) {
            if (arena.playerisinArena(player)) {
                for (Player playersinarena : arena.getPlayersinarena().keySet()) {
                    if (!serverLeave) {
                        playersinarena.teleport(lastLocation.get(playersinarena));
                    }
                    lastLocation.remove(playersinarena);
                    arena.leavearena(playersinarena);
                }
                avivableArenas.add(arena);
                activeArenas.remove(arena);
                break;
            }
        }
    }


    //Getter & Setters
    public HashMap<Player, Location> getLastLocation() {
        return lastLocation;
    }

    public ArrayList<Arena> getAllArena() {
        return allArena;
    }
}
