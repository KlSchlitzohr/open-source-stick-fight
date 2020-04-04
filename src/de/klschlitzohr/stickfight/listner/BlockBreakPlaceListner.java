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
            getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
                    Bukkit.getServer().getWorld("world").getBlockAt(
                            event.getBlock().getLocation()).setType(Material.REDSTONE_BLOCK), 60L);

            getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
                    Bukkit.getServer().getWorld("world").getBlockAt(
                            event.getBlock().getLocation()).setType(Material.AIR), 70L);
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
