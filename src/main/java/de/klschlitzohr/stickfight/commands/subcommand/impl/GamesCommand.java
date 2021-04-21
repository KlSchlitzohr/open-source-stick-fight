package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.game.Arena;
import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import de.klschlitzohr.stickfight.message.player.PlayerMessageType;
import org.bukkit.entity.Player;

public class GamesCommand implements SubCommand {

    private GameManager gameManager;

    public GamesCommand() {
        gameManager = Main.getPlugin().getGameManager();
    }

    @Override
    public void run(Player player, String[] args) {
            new PlayerMessageBuilder("command.games", player)
                    .setType(PlayerMessageType.PLAYER_INFO).send();

            for (Arena arena : gameManager.getAllArena()) {
                new PlayerMessageBuilder("- " + arena.getName() + " " + arena.getPlayerCount() + "/2",
                        player, true).setType(PlayerMessageType.PLAYER_INFO).send();
            }
    }

    @Override
    public String getPermission() {
        return "de.klschlitzohr.stickfight.command.games";
    }
}
