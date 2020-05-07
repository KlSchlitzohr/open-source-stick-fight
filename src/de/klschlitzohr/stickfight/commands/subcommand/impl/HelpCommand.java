package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import org.bukkit.entity.Player;

public class HelpCommand implements SubCommand {

    @Override
    public void run(Player player, String[] args) {
        new PlayerMessageBuilder("command.help", player).send();
        new PlayerMessageBuilder("command.setup",player).send();
        new PlayerMessageBuilder("command.duell.synatx",player).send();
        new PlayerMessageBuilder("command.games.syntax",player).send();
        new PlayerMessageBuilder("command.join.syntax",player).send();
        new PlayerMessageBuilder("command.joinqueue.syntax",player).send();
        new PlayerMessageBuilder("command.leave.syntax",player).send();
        new PlayerMessageBuilder("command.setlang.syntax",player).send();
    }

    @Override
    public String getPermission() {
        return "stickfight.command.help";
    }
}
