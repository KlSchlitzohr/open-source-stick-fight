package de.klschlitzohr.stickfight.listner;

import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class DamageListner implements Listener {

    private GameManager gameManager;

    public DamageListner() {
        this.gameManager = Main.getPlugin().getGameManager();
    }

    @EventHandler
    public void getDamageByEntity(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (this.gameManager.getLastLocation().containsKey(player)) {
                event.setDamage(0);
            }
        }
    }

    @EventHandler
    public void getDamageBadsyEntity(InventoryClickEvent event) {
        System.out.println("clickevent");
    }

    @EventHandler
    public void getDamageddddByEntity(InventoryMoveItemEvent event) {
        System.out.println("Move item");
    }

    @EventHandler
    public void getDamagaaeddddByEntity(InventoryInteractEvent event) {
        System.out.println("Interakt");
    }

    @EventHandler
    public void getdtDamageddddByEntity(InventoryDragEvent event) {
        System.out.println("drag event");
    }

}
