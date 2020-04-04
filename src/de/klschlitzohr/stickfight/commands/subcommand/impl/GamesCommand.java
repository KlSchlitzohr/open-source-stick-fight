package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.game.Arena;
import de.klschlitzohr.stickfight.main.Main;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import de.klschlitzohr.stickfight.message.player.PlayerMessageType;
import org.bukkit.entity.Player;

public class GamesCommand implements SubCommand {

    @Override
    public void run(Player player, String[] args) {
        if (args.length == 1) {
            new PlayerMessageBuilder("command.games", player)
                    .setType(PlayerMessageType.PLAYER_INFO).send();

            for (Arena arena : Main.getPlugin().getGameManager().getAllArena()) {
                new PlayerMessageBuilder("- " + arena.getName() + " " + arena.getPlayerCount() + "/2",
                        player, true).setType(PlayerMessageType.PLAYER_INFO).send();
            }
        }
    }

    @Override
    public String getPermission() {
        return "stickfight.command.games";
    }
}
