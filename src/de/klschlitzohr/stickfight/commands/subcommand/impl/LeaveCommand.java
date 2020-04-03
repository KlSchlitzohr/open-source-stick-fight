package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.entity.Player;

public class LeaveCommand implements SubCommand {
    @Override
    public void run(Player player, String[] args) {
        Main.getPlugin().getGameManager().playerLeave(player);
    }

    @Override
    public String getPermission() {
        return "stickfight.command.leave";
    }
}
