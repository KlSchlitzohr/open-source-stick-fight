package de.klschlitzohr.stickfight.listner;

import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodListner implements Listener {

    private GameManager gameManager;

    public FoodListner() {
        this.gameManager = Main.getPlugin().getGameManager();
    }

    @EventHandler
    public void getHungry(FoodLevelChangeEvent event) {
           Player player = (Player) event.getEntity();
           if (this.gameManager.getLastLocation().containsKey(player)) {
               event.setCancelled(true);
        }
    }

}
