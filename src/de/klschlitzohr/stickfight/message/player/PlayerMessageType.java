package de.klschlitzohr.stickfight.message.player;

public enum PlayerMessageType {

    PLAYER_DEBUG("\u00a7cDebug \u00a78\u00bb\u00a7b"), PLAYER_ERROR("\u00a7cDebug \u00a78\u00bb\u00a7c"), PLAYER_INFO("\u00a78\u00bb\u00a7c"), PLAYER_SETUP("\u00a7bSetup \u00a78\u00bb\u00a7c");

    private String type;

    PlayerMessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
