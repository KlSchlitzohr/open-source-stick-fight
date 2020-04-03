package de.klschlitzohr.stickfight.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;

public class Arena {

    public String getName() {
        return name;
    }

    public ArrayList<Player> getPlayersinarena() {
        return playersinarena;
    }

    private ArrayList<Player> playersinarena = new ArrayList<>();

    private ItemStack nockbackstick;

    private String name;
    private Location firstspawn;
    private Location secoundspawn;

    public Arena(String name) {
        this.name = name.toUpperCase();
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//Games.yml"));
        firstspawn = cfg.getLocation(name + ".1");
        secoundspawn = cfg.getLocation(name + ".2");
        setNockbackstick();
    }

    private void setNockbackstick() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 2,true);
        itemMeta.setDisplayName("§cStick");
        itemStack.setItemMeta(itemMeta);
        nockbackstick = itemStack;
    }

    public void joinarena(Player player) {
        playersinarena.add(player);
    }

    public void leavearena(Player player) {
        playersinarena.remove(player);
        //TODO TELeport away
    }

    public boolean playerisinArena(Player checkplayer) {
        for (Player player : playersinarena) {
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
        playersinarena.get(0).teleport(firstspawn);
        playersinarena.get(1).teleport(secoundspawn);
        for (Player player : playersinarena) {
            setInventory(player);
        }
    }

    private void setInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().addItem(nockbackstick);
    }
}
