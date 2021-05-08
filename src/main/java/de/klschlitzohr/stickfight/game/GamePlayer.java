package de.klschlitzohr.stickfight.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;

public class GamePlayer {

    private Inventory inventory;
    private Location location;
    private Player player;

    public GamePlayer(Player player) {
        this.player = player;
        this.location = player.getLocation();
        this.inventory = Bukkit.createInventory(null, InventoryType.PLAYER);
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void managerLeave(boolean leave) {
        if (!leave) {
            sendBack();
            restoreInventory();
        } else
            savePlayerData();
    }

    public void sendBack() {
        player.teleport(location);
    }

    public void restoreInventory() {
        player.getInventory().clear();
        inventory.forEach(itemStack -> {
            if (itemStack != null)
                player.getInventory().addItem(itemStack);
        });
        inventory = null;
    }

    private void savePlayerData() {
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//playerdata.yml"));
        cfg.set(player.getUniqueId() + ".inventory",inventory.getContents());
        cfg.set(player.getUniqueId() + ".location",location);
        try {
            cfg.save(new File("plugins//Stickfight//playerdata.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        inventory = null;
        location = null;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
