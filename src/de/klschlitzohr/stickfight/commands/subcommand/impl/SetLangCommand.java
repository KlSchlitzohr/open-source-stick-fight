package de.klschlitzohr.stickfight.commands.subcommand.impl;

import de.klschlitzohr.stickfight.commands.subcommand.SubCommand;
import de.klschlitzohr.stickfight.main.Main;
import de.klschlitzohr.stickfight.message.player.PlayerMessageBuilder;
import org.bukkit.entity.Player;

public class SetLangCommand implements SubCommand {
    @Override
    public void run(Player player, String[] args) {
        if (args.length == 2) {
            new PlayerMessageBuilder("command.setlang.start",player).send();
            if (Main.getPlugin().getLanguageManager().changeLanguage(args[1]))
                new PlayerMessageBuilder("command.setlang.success",player).send();
             else
                new PlayerMessageBuilder("command.setlang.error",player).addVariable("%lang", args[1]).send();
        } else {
            new PlayerMessageBuilder("command.setlang.syntax",player).send();
        }
    }

    @Override
    public String getPermission() {
        return "stickfight.command.setlang";
    }
}
