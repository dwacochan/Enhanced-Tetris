package model;

import java.util.Arrays;

public class PureGame {
    private int width;
    private int height;
    private int[][] cells;
    private int[][] currentShape;
    private int[][] nextShape;
    @Override
    public String toString() {
        return "PureGame{" +
                "width=" + width +
                ", height=" + height +
                ", cells=" + Arrays.deepToString(cells) +
                ", currentShape=" + Arrays.deepToString(currentShape) +
                ", nextShape=" + Arrays.deepToString(nextShape) +
                '}';
    }

    public PureGame(int width, int height, int[][] cells, int[][] currentShape, int[][] nextShape) {
        this.width = width;
        this.height = height;
        this.cells = cells;
        this.currentShape = currentShape;
        this.nextShape = nextShape;
        System.out.println("WIDSTH: " + width);
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int[][] getCells() {
        return cells;
    }
    public int[][] getCurrentShape() {
        return currentShape;
    }
    public int[][] getNextShape() {
        return nextShape;
    }
}