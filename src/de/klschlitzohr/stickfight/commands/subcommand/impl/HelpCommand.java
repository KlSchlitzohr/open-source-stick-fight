package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import org.bukkit.entity.Player;

public class HelpCommand implements SubCommand {

    @Override
    public void run(Player player, String[] args) {
        new PlayerMessageBuilder("command.help", player).send();
    }

    @Override
    public String getPermission() {
        return "stickfight.command.help";
    }
}
