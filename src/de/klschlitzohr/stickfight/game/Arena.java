package de.klschlitzohr.stickfight.game;

import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import net.minecraft.server.v1_15_R1.ChatComponentText;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

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

    public Arena(String name) {
        this.name = name.toUpperCase();
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//Games.yml"));
        firstspawn = cfg.getLocation(name + ".1");
        secoundspawn = cfg.getLocation(name + ".2");
        deathHigh = cfg.getInt(name + ".death");
        setNockbackstick();
    }

    private void setNockbackstick() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 2,true);
        itemMeta.setDisplayName("\u00a7cStick");
        itemStack.setItemMeta(itemMeta);
        nockbackstick = itemStack;
    }

    public void joinarena(Player player) {
        playersinarena.put(player,0);
    }

    public void leavearena(Player player) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playersinarena.remove(player);
        },"leaveArena");
        player.getInventory().clear();
        setScoreBoard(false);
    }

    public boolean playerisinArena(Player checkplayer) {
        for (Player player : playersinarena.keySet()) {
            if (player == checkplayer) {
                return true;
            }
        }
        return false;
    }

    public boolean isfull() {
        return playersinarena.size() == 2;
    }

    public Integer getplayercount() {
        return playersinarena.size();
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
        setScoreBoard(true);
    }

    private void setInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().addItem(nockbackstick);
        player.getInventory().addItem(new ItemStack(Material.SANDSTONE,5));
    }

    public void killPlayer(Player player) {
        Player otherPlayer = getOtherPlayer(player);
        playersinarena.put(otherPlayer,playersinarena.get(otherPlayer) + 1);
        setInventory(player);
        setScoreBoard(true);
        player.setFallDistance(0f);
        player.setHealth(20);
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

    private void setScoreBoard(boolean show) {
        for (Player player : playersinarena.keySet()) {
            if (show) {
                Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                Objective objective = scoreboard.registerNewObjective("abcd", "abcd");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName("\u00a73\u00a7lScore");
                objective.getScore( "\u00a7a\u00a7l" + playersinarena.get(player) + " \u00a7a" + player.getName())
                        .setScore(2);
                Player otherPlayer = getOtherPlayer(player);
                objective.getScore("\u00a7c\u00a7l" + playersinarena.get(otherPlayer) + " \u00a7c" +
                        otherPlayer.getName()).setScore(1);
                player.setScoreboard(scoreboard);
            } else {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }
    }

    public int getDeathHigh() {
        return deathHigh;
    }
}
