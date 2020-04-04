package de.klschlitzohr.stickfight.listner;

import de.klschlitzohr.stickfight.game.Arena;
import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListner implements Listener {

    private GameManager gameManager;

    public PlayerMoveListner() {
        this.gameManager = Main.getPlugin().getGameManager();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (this.gameManager.getLastLocation().containsKey(player)) {
            for (Arena arena : this.gameManager.getActiveArenas()) {
                if (arena.getPlayersinarena().containsKey(player)) {
                    if (arena.getDeathHigh() > player.getLocation().getY()) {
                        arena.killPlayer(player);
                    }
                }
            }
        }
    }

}
