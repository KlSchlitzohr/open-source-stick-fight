package de.klschlitzohr.stickfight.game;

import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;

public class Arena {

    public String getName() {
        return name;
    }

    public HashMap<Player, Integer> getPlayersinarena() {
        return playersinarena;
    }

    private HashMap<Player, Integer> playersinarena = new HashMap<>();

    private ItemStack nockbackstick;

    private ItemStack blocks;

    private String name;

    private Location firstspawn;

    private Location secoundspawn;

    private int deathHigh;

    public Arena(String name) {
        this.name = name.toUpperCase();
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//Games.yml"));
        firstspawn = cfg.getLocation(this.name + ".1");
        secoundspawn = cfg.getLocation(this.name + ".2");
        deathHigh = cfg.getInt(this.name + ".death");
        blocks = cfg.getItemStack(this.name + ".blocks");
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
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playersinarena.remove(player);
        },"leaveArena").start();
        player.getInventory().clear();
        Main.getPlugin().getGameManager().getGamePlayer().get(player).getInventory().forEach(itemStack -> {
            if (itemStack != null)
                player.getInventory().addItem(itemStack);
        });
        ScoreBoardUtils.updateScoreBoard(this,false);
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
            setInventory(player,true);
        }
        ScoreBoardUtils.updateScoreBoard(this,true);
    }

    private void setInventory(Player player,boolean saveInventory) {
        player.closeInventory();

        if (saveInventory) {
            Inventory inventory = Bukkit.createInventory(null, InventoryType.PLAYER);
        player.getInventory().forEach(itemStack -> {
            if (itemStack != null)
                inventory.addItem(itemStack);
        });
        Main.getPlugin().getGameManager().getGamePlayer().get(player).setInventory(inventory);
        }

        int stick = player.getInventory().first(Material.STICK);
        int blocke = -10;

        if (player.getInventory().first(blocks.getData().getItemType()) != -1)
            blocke = player.getInventory().first(blocks.getData().getItemType());
        else if (player.getInventory().first(Material.BARRIER) != -1)
            blocke = player.getInventory().first(Material.BARRIER);

        if (player.getInventory().getItemInOffHand().getData().getItemType() == Material.LEGACY_BARRIER ||
                player.getInventory().getItemInOffHand().getData().getItemType() == blocks.getData().getItemType())
            blocke = -5;

        if (stick == -1)
            stick = 0;

        if (blocke == -10) {
            blocke = 1;
        }

        boolean ofhand = false;
        if (blocke == -5)
            ofhand = true;
        boolean setstick = player.getInventory().getItemInOffHand().getData().getItemType() == Material.LEGACY_STICK;

        player.getInventory().clear();
        if (!ofhand)
            player.getInventory().setItem(blocke,blocks);
        else
            player.getInventory().setItemInOffHand(blocks);
        if (!setstick)
            player.getInventory().setItem(stick, nockbackstick);
        else
            player.getInventory().setItemInOffHand(nockbackstick);
    }

    public void killPlayer(Player player) {
        Player otherPlayer = getOtherPlayer(player);
        playersinarena.put(otherPlayer,playersinarena.get(otherPlayer) + 1);
        setInventory(player,false);
        ScoreBoardUtils.updateScoreBoard(this,true);
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

    public ItemStack getBlocks() {
        return blocks;
    }

    public Location getSecoundspawn() {
        return secoundspawn;
    }

    public Location getFirstspawn() {
        return firstspawn;
    }
}
