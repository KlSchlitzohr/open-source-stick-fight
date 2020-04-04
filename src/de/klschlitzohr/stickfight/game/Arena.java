package de.klschlitzohr.stickfight.game;

import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class Arena {

    public String getName() {
        return name;
    }

    public HashMap<Player, Integer> getPlayersinarena() {
        return playersinarena;
    }

    private HashMap<Player, Integer> playersinarena = new HashMap<>();

    private ItemStack nockbackstick;

    private String name;
    private Location firstspawn;
    private Location secoundspawn;

    private int deathHigh;

    private ScoreBoardManager scorebaordManager;

    public Arena(String name) {
        this.scorebaordManager = Main.getPlugin().getScorebaordManager();
        this.name = name.toUpperCase();
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//Games.yml"));
        firstspawn = cfg.getLocation(name + ".1");
        secoundspawn = cfg.getLocation(name + ".2");
        deathHigh = cfg.getInt(name + ".death");
        setNockbackStick();
    }

    private void setNockbackStick() {
        nockbackstick = new ItemStackBuilder(Material.STICK,1).setDisplayName("§cStick")
                .addEnchantment(Enchantment.KNOCKBACK,2,true).build();
    }

    public void joinArena(Player player) {
        playersinarena.put(player,0);
    }

    public void leaveArena(Player player) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playersinarena.remove(player);
        },"leaveArena");
        player.getInventory().clear();
        scorebaordManager.updateScoreBoard(this,false);
    }

    public boolean playerIsInArena(Player checkplayer) {
        for (Player player : playersinarena.keySet()) {
            if (player == checkplayer) {
                return true;
            }
        }
        return false;
    }

    public void startArena() {
        boolean first = true;
        for (Player player : playersinarena.keySet()) {
            if (first) {
                player.teleport(firstspawn);
                first = false;
            } else
                player.teleport(secoundspawn);
            setInventory(player);
        }
        scorebaordManager.updateScoreBoard(this,true);
    }

    private void setInventory(Player player) {
        player.closeInventory();
        player.getInventory().clear();
        player.getInventory().addItem(nockbackstick);
        player.getInventory().addItem(new ItemStack(Material.SANDSTONE,5));
    }

    public void killPlayer(Player player) {
        Player otherPlayer = getOtherPlayer(player);
        playersinarena.put(otherPlayer,playersinarena.get(otherPlayer) + 1);
        setInventory(player);
        scorebaordManager.updateScoreBoard(this,true);
        if (otherPlayer.getLocation().distance(firstspawn) > otherPlayer.getLocation().distance(secoundspawn))
            player.teleport(firstspawn);
        else
            player.teleport(secoundspawn);
    }

    public Player getOtherPlayer(Player player) {
        for (Player p : playersinarena.keySet()) {
            if (!p.equals(player))
                return p;
        }
        return null;
    }

    public int getDeathHigh() {
        return deathHigh;
    }

    public Integer getPlayerCount() {
        return playersinarena.size();
    }

    public boolean isFull() {
        return playersinarena.size() == 2;
    }
}
