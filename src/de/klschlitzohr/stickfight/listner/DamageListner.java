package de.klschlitzohr.stickfight.listner;

import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

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
}
