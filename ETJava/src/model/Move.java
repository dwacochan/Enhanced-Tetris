package model;

public class Move {
    private int column;
    private int rotation;

    public Move(int column, int rotation) {
        this.column = column;
        this.rotation = rotation;
    }

    public int getColumn() {
        return column;
    }

    public int getRotation() {
        return rotation;
    }
}
