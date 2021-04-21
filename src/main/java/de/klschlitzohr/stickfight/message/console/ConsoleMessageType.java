package de.klschlitzohr.stickfight.message.console;

public enum ConsoleMessageType {

    CONSOLE_DEBUG("[DEBUG]"), CONSOLE_ERROR("[ERROR]"), CONSOLE_INFO("[INFO]");

    private String type;

    ConsoleMessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
