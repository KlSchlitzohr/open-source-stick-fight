package de.klschlitzohr.stickfight.listner;

import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropListner implements Listener {

    private GameManager gameManager;

    public DropListner() {
        this.gameManager = Main.getPlugin().getGameManager();
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent event) {
        Player player = (Player) event.getPlayer();
        if (this.gameManager.getLastLocation().containsKey(player)) {
            event.setCancelled(true);
        }

    }

}
