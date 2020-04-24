package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.main.Main;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class JoinQueueCommand implements SubCommand {

    @Override
    public void run(Player player, String[] args) {
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//Games.yml"));
            if (Main.getPlugin().getGameManager().joinQueue(player)) {
                new PlayerMessageBuilder("command.joinqueue.success", player).send();
            } else {
                new PlayerMessageBuilder("command.joinqueue.error", player).send();
            }

    }

    @Override
    public String getPermission() {
        return "stickfight.command.joinqueue";
    }
}
