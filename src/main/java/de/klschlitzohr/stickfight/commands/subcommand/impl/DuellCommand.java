package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DuellCommand implements SubCommand {

    private GameManager gameManager;

    public DuellCommand() {
        gameManager = Main.getPlugin().getGameManager();
    }

    @Override
    public void run(Player player, String[] args) {
        if (args.length == 2) {
            if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
                new PlayerMessageBuilder("command.duell.offline", player).send();
                return;
            }
            gameManager.sendFightRequest(player,Bukkit.getPlayer(args[1]));
        } else {
            new PlayerMessageBuilder("command.duell.syntax",player).send();
        }
    }

    @Override
    public String getPermission() {
        return "stickfight.command.duell";
    }
}
