package de.klschlitzohr.stickfight.message.player;

import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.entity.Player;

public class PlayerMessageBuilder {

    private String prefix = "\u00a78[\u00a7eStickFight\u00a78] ";
    private PlayerMessageType type = PlayerMessageType.PLAYER_INFO;
    private Player player;

    private String message;

    public PlayerMessageBuilder(String name, Player player) {
        this(name, player, false);
    }

    public PlayerMessageBuilder(String name, Player player, boolean raw) {
        this.message = Main.getPlugin().getLanguageManager().getMessages().getOrDefault(name, name);
        this.player = player;
    }

    public PlayerMessageBuilder setType(PlayerMessageType consoleMessageType) {
        type = consoleMessageType;
        return this;
    }

    public PlayerMessageBuilder addVariable(final String key, final Object replacement) {
        this.message = this.message.replace(key, String.valueOf(replacement));
        return this;
    }

    public void send() {
        this.message = this.message.replace("%nl", "\n" + prefix + type.getType() + " ");
        this.player.sendMessage(prefix + type.getType() + " " + this.message);
    }

}
