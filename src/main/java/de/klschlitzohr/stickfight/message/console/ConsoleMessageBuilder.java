package de.klschlitzohr.stickfight.message.console;

import de.klschlitzohr.stickfight.main.Main;

public class ConsoleMessageBuilder {

    private String prefix = "[StickFight]";
    private ConsoleMessageType type = ConsoleMessageType.CONSOLE_INFO;

    private String message;

    public ConsoleMessageBuilder(String name) {
        this(name, false);
    }

    public ConsoleMessageBuilder(String name, boolean raw) {
        this.message = Main.getPlugin().getLanguageManager().getMessages().getOrDefault(name, name);
    }

    public ConsoleMessageBuilder setType(ConsoleMessageType consoleMessageType) {
        type = consoleMessageType;
        return this;
    }

    public ConsoleMessageBuilder addVariable(final String key, final Object replacement) {
        this.message.replaceAll(key, String.valueOf(replacement));
        return this;
    }

    public void send() {
        System.out.println(prefix + type.getType() + " " + this.message);
    }
}
