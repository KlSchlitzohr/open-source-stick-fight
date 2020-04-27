package de.klschlitzohr.stickfight.game;

import de.klschlitzohr.stickfight.message.console.ConsoleMessageBuilder;
import de.klschlitzohr.stickfight.message.console.ConsoleMessageType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private HashMap<Player, Location> lastLocation = new HashMap<>();

    private ArrayList<Arena> allArena;

    private ArrayList<Arena> activeArenas;

    private ArrayList<Arena> avivableArenas;

    private ArrayList<Player> playersInQueue;

    public GameManager() {
       allArena = GameUtilities.loadArenas();
       activeArenas = new ArrayList<>();
       avivableArenas = new ArrayList<>(allArena);
       playersInQueue = new ArrayList<>();
    }

    public boolean joinArenaName(String name, Player player) {
        for (Arena arena : avivableArenas) {
            if (arena.getName().equalsIgnoreCase(name)) {
                    arena.joinArena(player);
                    if (arena.isFull()) {
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

    public boolean joinQueue(Player player) {
        if (playersInQueue.contains(player))
            return true;
            playersInQueue.add(player);
        System.out.println("aaa" + player.getName());
            if (playersInQueue.size() == 2) {
                System.out.println("aaaaaa");
                for (Arena arena : avivableArenas) {
                    System.out.println("aaasdasda");
                    if (arena.getPlayersinarena().size() == 0) {
                        for (Player playerinque : playersInQueue) {
                            joinArenaName(arena.getName(),playerinque);
                        }
                        if (!playersInQueue.isEmpty())
                            new ConsoleMessageBuilder("Warteschlangenfehler").setType(ConsoleMessageType.CONSOLE_ERROR).send();
                        playersInQueue.clear();
                        return true;
                    }
                }
            } else {
                return true;
            }

        return false;
    }

    public void addArena(String name) {
        Arena arena = new Arena(name);
        allArena.add(arena);
        avivableArenas.add(arena);
    }

    public boolean removeArena(String name) {
        Arena toRemove = null;

        for (Arena arena : allArena) {
            if (arena.getName().equalsIgnoreCase(name)) {
                if (!activeArenas.contains(arena))
                    toRemove = arena;
                break;
            }
        }

        if (toRemove == null)
            return false;

        allArena.remove(toRemove);
        avivableArenas.remove(toRemove);
        return true;
    }

    public void playerLeave(Player player, boolean serverLeave) {
        for (Arena arena : allArena) {
            if (arena.playerIsInArena(player)) {
                for (Player playersinarena : arena.getPlayersinarena().keySet()) {
                    if (!serverLeave) {
                        playersinarena.teleport(lastLocation.get(playersinarena));
                    }
                    lastLocation.remove(playersinarena);
                    arena.leaveArena(playersinarena);
                }
                avivableArenas.add(arena);
                activeArenas.remove(arena);
                break;
            }
        }
        if (playersInQueue.contains(player))
            playersInQueue.remove(player);
    }


    //Getter & Setters

    public HashMap<Player, Location> getLastLocation() {
        return lastLocation;
    }
    public ArrayList<Arena> getAllArena() {
        return allArena;
    }

    public ArrayList<Arena> getActiveArenas() {
        return activeArenas;
    }
}
