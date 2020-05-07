package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import org.bukkit.entity.Player;

public class LeaveCommand implements SubCommand {

    private GameManager gameManager;

    public LeaveCommand() {
        gameManager = Main.getPlugin().getGameManager();
    }

    @Override
    public void run(Player player, String[] args) {
        gameManager.playerLeave(player,false);
        new PlayerMessageBuilder("command.leave.success",player).send();
    }

    @Override
    public String getPermission() {
        return "stickfight.command.leave";
    }
}
