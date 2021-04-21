package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import org.bukkit.entity.Player;

public class StatsCommand implements SubCommand {
    @Override
    public void run(Player player, String[] args) {
        //TODO implemt Stats System
    }

    @Override
    public String getPermission() {
        return "de.klschlitzohr.stickfight.command.stats";
    }
}
