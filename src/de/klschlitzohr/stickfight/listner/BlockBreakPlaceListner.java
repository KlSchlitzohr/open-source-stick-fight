package de.klschlitzohr.stickfight.listner;

import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import static org.bukkit.Bukkit.getServer;

public class BlockBreakPlaceListner implements Listener {

    private GameManager gameManager;

    public BlockBreakPlaceListner() {
        this.gameManager = Main.getPlugin().getGameManager();
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Player player = (Player) event.getPlayer();
        if (this.gameManager.getLastLocation().containsKey(player)) {
            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    Bukkit.getServer().getWorld("world").getBlockAt(event.getBlock().getLocation()).setType(Material.REDSTONE_BLOCK);
                }
            }, 60L);
            BukkitScheduler scheduler2 = getServer().getScheduler();
            scheduler2.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    Bukkit.getServer().getWorld("world").getBlockAt(event.getBlock().getLocation()).setType(Material.AIR);
                }
            }, 70L);
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = (Player) event.getPlayer();
        if (this.gameManager.getLastLocation().containsKey(player)) {
            event.setCancelled(true);
        }
    }
}
