package de.klschlitzohr.stickfight.listner;

import de.klschlitzohr.stickfight.game.Arena;
import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.game.ItemStackBuilder;
import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;

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
            if (event.getBlockPlaced().getType() == Material.BARRIER) {
                event.setCancelled(true);
                return;
            }

            Material material = null;
            for (Arena arena : gameManager.getActiveArenas()) {
                if (arena.getPlayersinarena().containsKey(event.getPlayer())) {
                    material = arena.getBlocks().getData().getItemType();
                    if (arena.getFirstspawn().getBlockX() == event.getBlock().getX() &&
                            arena.getFirstspawn().getBlockZ() == event.getBlock().getZ() ||
                    arena.getSecoundspawn().getBlockX() == event.getBlock().getX() &&
                    arena.getSecoundspawn().getBlockZ() == event.getBlock().getZ()) {
                        event.setCancelled(true);
                        return;
                    }

                }
            }
            if (material == null)
                return;
            if (event.getHand() == EquipmentSlot.OFF_HAND) {
                if (player.getInventory().getItemInOffHand().getAmount() == 1) {
                    player.getInventory().setItemInOffHand(new ItemStackBuilder(Material.BARRIER, 1)
                            .setDisplayName("§cXXX").build());
                }
            } else if (player.getInventory().getItemInMainHand().getAmount() == 1)
                player.getInventory().setItemInMainHand(new ItemStackBuilder(Material.BARRIER,1).setDisplayName("§cXXX").build());

            getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
                    event.getBlock().setType(Material.REDSTONE_BLOCK), 60L);
            getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
                    event.getBlock().setType(Material.AIR), 70L);
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
