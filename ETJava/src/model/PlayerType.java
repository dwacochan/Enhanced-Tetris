package model;

public enum PlayerType {
    HUMAN("Human"),
    AI("AI"),
    SERVER("Server");

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
