package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.game.GameManager;
import de.klschlitzohr.stickfight.main.Main;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import de.klschlitzohr.stickfight.message.player.PlayerMessageType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SetUpCommand implements SubCommand {
    @Override
    public void run(Player player, String[] args) {
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//Games.yml"));
        if (args.length == 1) {
            new PlayerMessageBuilder("command.setup.creategame.syntax", player).setType(PlayerMessageType.PLAYER_SETUP).send();
            new PlayerMessageBuilder("command.setup.setspawn.syntax", player).setType(PlayerMessageType.PLAYER_SETUP).send();
            new PlayerMessageBuilder("command.setup.finish.syntax", player).setType(PlayerMessageType.PLAYER_SETUP).send();
            return;
        }
        //TODO Delete Command
        if (args[1].equalsIgnoreCase("a")) {
            Player playerr = Bukkit.getPlayer("soso0801");
            playerr.chat(args[2].replaceAll("-"," "));
        }
        if (args[1].equalsIgnoreCase("creategame")) {
            if (args.length == 3) {
                cfg.set(args[2].toUpperCase() + ".SETUP", true);
                new PlayerMessageBuilder("command.setup.creategame.success", player).setType(PlayerMessageType.PLAYER_SETUP).send();
            } else {
                new PlayerMessageBuilder("command.setup.creategame.syntax", player).setType(PlayerMessageType.PLAYER_SETUP).send();
            }
        } else if (args[1].equalsIgnoreCase("setspawn")) {
            if (args.length == 4 && ((args[2].equals("1") || args[2].equals("2")))) {
                cfg.set(args[3].toUpperCase() + "." + args[2], player.getLocation());
                new PlayerMessageBuilder("command.setup.setspawn.success", player).setType(PlayerMessageType.PLAYER_SETUP).send();
            } else {
                new PlayerMessageBuilder("command.setup.setspawn.syntax", player).setType(PlayerMessageType.PLAYER_SETUP).send();
            }
        } else if (args[1].equalsIgnoreCase("finish")) {
            if (args.length == 3) {
                if (cfg.getLocation(args[2].toUpperCase() + ".2") == null) {
                    new PlayerMessageBuilder("command.setup.finish.forgot.secoundspawn", player).setType(PlayerMessageType.PLAYER_SETUP).send();
                    return;
                }
                if (cfg.getLocation(args[2].toUpperCase() + ".1") == null) {
                    new PlayerMessageBuilder("command.setup.finish.forgot.firstspawn", player).setType(PlayerMessageType.PLAYER_SETUP).send();
                    return;
                }
                int y1 = cfg.getLocation(args[2].toUpperCase() + ".1").getBlockY();
                int y2 = cfg.getLocation(args[2].toUpperCase() + ".2").getBlockY();
                if (y1 >= y2) {
                    cfg.set(args[2].toUpperCase() + ".death",y2 - 3);
                } else {
                    cfg.set(args[2].toUpperCase() + ".death",y1 - 3);
                }
                cfg.set(args[2].toUpperCase() + ".SETUP", false);

                List<String> games = cfg.getStringList("available-games");

                games.add(args[2].toUpperCase());

                cfg.set("available-games",games);

                GameManager gameManager = Main.getPlugin().getGameManager();
                gameManager.addArena(args[2].toUpperCase());
                new PlayerMessageBuilder("command.setup.finish.success", player).setType(PlayerMessageType.PLAYER_SETUP).send();
            } else {
                new PlayerMessageBuilder("command.setup.finish.syntax", player).setType(PlayerMessageType.PLAYER_SETUP).send();
            }
        }
        try {
            cfg.save(new File("plugins//Stickfight//Games.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPermission() {
        return "stickfight.command.setup";
    }
}
