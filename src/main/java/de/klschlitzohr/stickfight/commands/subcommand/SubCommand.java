package de.klschlitzohr.stickfight.commands.subcommand;

import org.bukkit.entity.Player;

public interface SubCommand {

    void run(final Player player, final String[] args);

    String getPermission();
}
