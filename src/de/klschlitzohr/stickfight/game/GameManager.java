package de.klschlitzohr.stickfight.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {

    public ArrayList<Arena> getAllArena() {
        return allArena;
    }

    // allArenas
    private ArrayList<Arena> allArena;
    // activeGames
    private ArrayList<Arena> activeArenas;
    // availableArenas
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

    public void playerLeave(Player player) {
        for (Arena arena : allArena) {
            if (arena.playerisinArena(player)) {
                arena.leavearena(player);
                avivableArenas.add(arena);
                activeArenas.remove(arena);
            }
        }
    }
}
