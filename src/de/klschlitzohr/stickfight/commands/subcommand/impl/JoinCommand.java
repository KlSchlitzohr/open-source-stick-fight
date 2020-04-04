package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.main.Main;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class JoinCommand implements SubCommand {

    @Override
    public void run(Player player, String[] args) {
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//Games.yml"));
        if (args.length == 2) {
            if (Main.getPlugin().getGameManager().joinArenaName(args[1],player)) {
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
