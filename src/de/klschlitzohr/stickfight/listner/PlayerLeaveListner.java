package de.klschlitzohr.stickfight.listner;

import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListner implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        GameManager gameManager = Main.getPlugin().getGameManager();
        gameManager.playerLeave(event.getPlayer());
    }

}
