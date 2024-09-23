package model.factory;

import controller.Gameplay;
import model.Tetromino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TetrominoFactoryTest {

    private Gameplay gameplay1;
    private Gameplay gameplay2;

    @BeforeEach
    public void setUp() {
        TetrominoFactory.reset();
        gameplay1 = new Gameplay(400,600,1);
        gameplay2 = new Gameplay(400,600,1);
    }

    @Test
    public void testGenerateTetrominoSequence() {
        Tetromino t1 = TetrominoFactory.getNextTetromino(gameplay1);
        assertNotNull(t1, "Generated Tetromino should not be null");

        Tetromino t2 = TetrominoFactory.getNextTetromino(gameplay1);
        assertNotNull(t2, "Generated Tetromino should not be null");

        assertNotEquals(t1, t2, "Consecutive Tetrominoes should not be the same");
    }

    @Test
    public void testDifferentGameplayInstancesHaveSameSequence() {
        Tetromino t1Game1 = TetrominoFactory.getNextTetromino(gameplay1);
        Tetromino t1Game2 = TetrominoFactory.getNextTetromino(gameplay2);

        assertEquals(t1Game1.getClass(), t1Game2.getClass(),
                "Both gameplay instances should get the same Tetromino type");
    }

    @Test
    public void testQueueRegeneration() {
        for (int i = 0; i < 1000; i++) {
            TetrominoFactory.getNextTetromino(gameplay1);
        }
        TetrominoFactory.getNextTetromino(gameplay1); // This should trigger queue regeneration

        assertDoesNotThrow(() -> TetrominoFactory.getNextTetromino(gameplay1),
                "TetrominoFactory should regenerate the queue and not throw an exception");
    }

    @Test
    public void testPeekNextTetromino() {
        Tetromino peekedTetromino = TetrominoFactory.peekNextTetromino(gameplay1);
        Tetromino nextTetromino = TetrominoFactory.getNextTetromino(gameplay1);

        assertEquals(peekedTetromino.getClass(), nextTetromino.getClass(),
                "Peeked Tetromino should match the next Tetromino retrieved");
    }

    @Test
    public void testResetFunctionality() {
        Tetromino t1 = TetrominoFactory.getNextTetromino(gameplay1);
        TetrominoFactory.reset();

        Tetromino t2 = TetrominoFactory.getNextTetromino(gameplay1);
        assertNotEquals(t1, t2, "Tetromino sequence should reset and differ after calling reset");
    }
}
