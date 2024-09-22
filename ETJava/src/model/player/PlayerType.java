package model.player;

public enum PlayerType {
    HUMAN("Human"),
    AI("AI"),
    EXTERNAL("External");

    private final String type;

    PlayerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
