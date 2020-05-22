package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import org.bukkit.entity.Player;

public class JoinCommand implements SubCommand {

    private GameManager gameManager;

    public JoinCommand() {
        gameManager = Main.getPlugin().getGameManager();
    }

    @Override
    public void run(Player player, String[] args) {
        if (args.length == 2) {
            if (gameManager.joinArenaName(args[1],player)) {
                new PlayerMessageBuilder("command.join.success", player).send();
            } else {
                new PlayerMessageBuilder("command.join.full", player).send();
            }
        } else {
            new PlayerMessageBuilder("command.join.syntax", player).send();
        }
    }

    @Override
    public String getPermission() {
        return "stickfight.command.join";
    }
}
