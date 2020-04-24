package de.klschlitzohr.stickfight.commands;

import de.klschlitzohr.stickfight.commands.subcommand.*;
import de.klschlitzohr.stickfight.commands.subcommand.impl.*;
import de.klschlitzohr.stickfight.message.console.ConsoleMessageBuilder;
import de.klschlitzohr.stickfight.message.console.ConsoleMessageType;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandStickfightExecutor implements CommandExecutor {

    private HashMap<String, SubCommand> subCommands;

    public CommandStickfightExecutor() {
        this.subCommands = new HashMap<>();
        this.subCommands.put("games", new GamesCommand());
        this.subCommands.put("help", new HelpCommand());
        this.subCommands.put("join", new JoinCommand());
        this.subCommands.put("setup", new SetUpCommand());
        this.subCommands.put("setlang", new SetLangCommand());
        this.subCommands.put("leave", new LeaveCommand());
        this.subCommands.put("joinqueue", new JoinQueueCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            new ConsoleMessageBuilder("console.player.needed")
                    .setType(ConsoleMessageType.CONSOLE_ERROR).send();
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            new PlayerMessageBuilder("command.noargs", player).send();
            return false;
        }

        SubCommand subCommand = this.subCommands.get(args[0].toLowerCase());

        if (subCommand != null) {
            if (!player.hasPermission(subCommand.getPermission())) {
                new PlayerMessageBuilder("permission.denied", player).send();
                return true;
            }

            subCommand.run(player, args);
        } else {

        }
        return false;
    }
}
