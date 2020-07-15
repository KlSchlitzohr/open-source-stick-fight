package de.klschlitzohr.stickfight.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class GamePlayer {

    private Inventory inventory;
    private Location location;

    public GamePlayer(Location location) {
        this.location = location;
        this.inventory = Bukkit.createInventory(null, InventoryType.PLAYER);
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Location getLocation() {
        return location;
    }

    public Inventory getInventory() {
        return inventory;
    }

}
