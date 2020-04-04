package de.klschlitzohr.stickfight.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args[0].equalsIgnoreCase("setup") && args.length == 2) {
            return Arrays.asList("creategame", "setspawn", "finish");
        }
        if (args.length == 3 && args[1].equalsIgnoreCase("setspawn")) {
            return Arrays.asList("1","2");
        }
        if (!(args.length >= 2)) {
            return Arrays.asList("games", "join", "setup", "help", "setlang","leave");
        }
            return new ArrayList<>();
    }
}
