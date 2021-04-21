package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.entity.Player;

public class LeaveCommand implements SubCommand {

    private GameManager gameManager;

    public LeaveCommand() {
        gameManager = Main.getPlugin().getGameManager();
    }

    @Override
    public void run(Player player, String[] args) {
        gameManager.playerLeave(player,false);
    }

    @Override
    public String getPermission() {
        return "stickfight.command.leave";
    }
}
