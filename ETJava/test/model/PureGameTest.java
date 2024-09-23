package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PureGameTest {

    private PureGame pureGame;
    private int width;
    private int height;
    private int[][] cells;
    private int[][] currentShape;
    private int[][] nextShape;

    @BeforeEach
    public void setUp() {
        width = 10;
        height = 20;
        cells = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        currentShape = new int[][]{
                {1, 1, 1},
                {0, 1, 0}
        };
        nextShape = new int[][]{
                {0, 2, 0},
                {2, 2, 2}
        };
        pureGame = new PureGame(width, height, cells, currentShape, nextShape);
    }

    @Test
    public void testConstructorAndGetters() {
        // Test width and height
        assertEquals(width, pureGame.getWidth(), "Width should match the constructor value");
        assertEquals(height, pureGame.getHeight(), "Height should match the constructor value");

        // Test cells
        assertArrayEquals(cells, pureGame.getCells(), "Cells should match the constructor value");

        // Test current shape
        assertArrayEquals(currentShape, pureGame.getCurrentShape(), "Current shape should match the constructor value");

        // Test next shape
        assertArrayEquals(nextShape, pureGame.getNextShape(), "Next shape should match the constructor value");
    }

    @Test
    public void testToString() {
        String expectedString = "PureGame{" +
                "width=" + width +
                ", height=" + height +
                ", cells=" + Arrays.deepToString(cells) +
                ", currentShape=" + Arrays.deepToString(currentShape) +
                ", nextShape=" + Arrays.deepToString(nextShape) +
                '}';

        assertEquals(expectedString, pureGame.toString(), "toString() output should match the expected format");
    }
}
