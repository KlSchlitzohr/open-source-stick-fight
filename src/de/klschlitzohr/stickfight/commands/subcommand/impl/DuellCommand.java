package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import org.bukkit.entity.Player;

public class DuellCommand implements SubCommand {
    @Override
    public void run(Player player, String[] args) {

    }

    @Override
    public String getPermission() {
        return "stickfight.command.duell";
    }
}
