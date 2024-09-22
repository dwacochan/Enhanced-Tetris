package model.factory;

import model.Tetromino;
import model.tetrominos.*;
import java.util.Random;

public class TetrominoFactory {
    private static Tetromino nextTetromino = generateRandomTetromino();

    // Method to create the current Tetromino and generate the next one
    public static Tetromino createTetromino() {
        Tetromino currentTetromino = nextTetromino;  // Use the current next Tetromino
        nextTetromino = generateRandomTetromino();   // Generate the new next Tetromino
        return currentTetromino;                     // Return the current Tetromino
    }

    public static Tetromino peekNextTetromino() {
        return nextTetromino;
    }

    private static Tetromino generateRandomTetromino() {
        int n = new Random().nextInt(7);
        return switch (n) {
            case 0 -> new I();
            case 1 -> new O();
            case 2 -> new T();
            case 3 -> new S();
            case 4 -> new Z();
            case 5 -> new J();
            case 6 -> new L();
            default -> null;  // This should never be hit, as the random is constrained
        };
    }
}
