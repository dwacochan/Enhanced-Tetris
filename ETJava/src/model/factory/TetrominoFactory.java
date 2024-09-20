package model.factory;

import model.Tetromino;
import model.tetrominos.*;
import java.util.Random;

public class TetrominoFactory {

    public static Tetromino createTetromino() {
        int n = new Random().nextInt(7);

        return switch (n) {
            case 0 -> new I();
            case 1 -> new O();
            case 2 -> new T();
            case 3 -> new S();
            case 4 -> new Z();
            case 5 -> new J();
            case 6 -> new L();
            default -> null;
        };
    }
}
