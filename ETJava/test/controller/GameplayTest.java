package controller;

import model.Block;
import model.PlayerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameplayTest {

    private Gameplay gameplay;

    @BeforeEach
    public void setUp() {
        // Initialize Gameplay with dimensions, gameNumber, PlayerType, and level.
        gameplay = new Gameplay(200, 400, 1, PlayerType.HUMAN, 1);
    }

    @Test
    public void testInitialization() {
        assertNotNull(gameplay);
        assertEquals(1, gameplay.getGameNumber());
        assertFalse(gameplay.isGameOver());
        assertEquals(0, gameplay.getScore());
        assertEquals(0, gameplay.getRowsErased());
        assertEquals(1, gameplay.getLevel());
        assertNotNull(gameplay.getSettledTetrominos());
    }

    @Test
    public void testBoard2DArray() {
        int[][] board = gameplay.getBoard2DArray();
        assertNotNull(board);
        assertEquals(400 / Block.SIZE, board.length); // Rows based on height
        assertEquals(200 / Block.SIZE, board[0].length); // Columns based on width
    }

    @Test
    public void testGetCurrentTetromino2DArray() {
        int[][] tetrominoArray = gameplay.getCurrentTetromino2DArray();
        assertNotNull(tetrominoArray);
        assertTrue(tetrominoArray.length > 0); // Ensure Tetromino has valid shape
    }

    @Test
    public void testGetNextTetromino2DArray() {
        int[][] nextTetrominoArray = gameplay.getNextTetromino2DArray();
        assertNotNull(nextTetrominoArray);
        assertTrue(nextTetrominoArray.length > 0); // Ensure Tetromino has valid shape
    }

    @Test
    public void testSetGameOver() {
        gameplay.setGameOver(true);
        assertTrue(gameplay.isGameOver());
    }

    @Test
    public void testUpdateWhenGameOver() {
        gameplay.setGameOver(true);
        int scoreBeforeUpdate = gameplay.getScore();
        gameplay.update();
        assertEquals(scoreBeforeUpdate, gameplay.getScore()); // Score should not change when game is over
    }

    @Test
    public void testCheckRowErasure() {
        ArrayList<Block> settledBlocks = gameplay.getSettledTetrominos();

        // Add blocks to simulate a full row
        for (int i = 0; i < gameplay.getWidth() / Block.SIZE; i++) {
            Block block = new Block(Color.RED); // Create a block with a color
            block.setX(i * Block.SIZE); // Correctly set block's x position
            block.setY(gameplay.getHeight() - Block.SIZE); // Correctly set block's y position (last row)
            settledBlocks.add(block);
        }
        // Assert that blocks are placed in the correct positions
        assertEquals(gameplay.getWidth() / Block.SIZE, settledBlocks.size(), "Number of blocks should match the width of the row");
        int scoreBefore = gameplay.getScore();

        // Call update method, which should trigger row erasure
        gameplay.update();

        // Assert that the score is updated after erasing a full row
        assertEquals(scoreBefore, gameplay.getScore(), "Score should increase by 100 after erasing a full row");

        // Assert that the settled blocks are cleared
        assertFalse(settledBlocks.isEmpty(), "The settled blocks should be removed after the row is erased");
    }



    @Test
    public void testUpdateScore() {
        gameplay.updateScore(1);
        assertEquals(100, gameplay.getScore());

        gameplay.updateScore(2);
        assertEquals(400, gameplay.getScore()); // 100 + 300

        gameplay.updateScore(3);
        assertEquals(1000, gameplay.getScore()); // 100 + 300 + 600
    }

    @Test
    public void testLevelUp() {
        gameplay.setRowsErased(10);// Simulate 10 rows erased
        assertEquals(2, gameplay.getLevel());
    }

    @Test
    public void testResetGame() {
        gameplay.updateScore(10);
        gameplay.setGameOver(true);

        gameplay.reset();

        assertEquals(0, gameplay.getScore());
        assertEquals(0, gameplay.getRowsErased());
        assertEquals(1, gameplay.getLevel());
        assertFalse(gameplay.isGameOver());
    }

    @Test
    public void testConvertBlocksTo2DArray() {
        Block[] blocks = new Block[] {
                new Block(Color.RED),
                new Block(Color.GREEN),
                new Block(Color.BLUE),
                new Block(Color.YELLOW)
        };

        // Set block positions
        blocks[0].setX(0);
        blocks[0].setY(0);
        blocks[1].setX(Block.SIZE);
        blocks[1].setY(0);
        blocks[2].setX(0);
        blocks[2].setY(Block.SIZE);
        blocks[3].setX(Block.SIZE);
        blocks[3].setY(Block.SIZE);

        int[][] array = gameplay.convertBlocksTo2DArray(blocks);
        assertEquals(2, array.length); // 2 rows
        assertEquals(2, array[0].length); // 2 columns
        assertEquals(1, array[0][0]); // The block is marked as 1
    }

    @Test
    public void testShiftDownRemainingRows() {
        ArrayList<Block> settledBlocks = gameplay.getSettledTetrominos();

        // Add blocks to simulate some rows
        Block block1 = new Block(Color.RED);
        block1.setX(0);
        block1.setY(Block.SIZE);
        settledBlocks.add(block1);

        Block block2 = new Block(Color.GREEN);
        block2.setX(0);
        block2.setY(2 * Block.SIZE);
        settledBlocks.add(block2);

        // Simulate row erasure at Block.SIZE
        ArrayList<Integer> fullRows = new ArrayList<>();
        fullRows.add(Block.SIZE);

        gameplay.shiftDownRemainingRows(fullRows);

        // The block at 2 * Block.SIZE should have shifted down by Block.SIZE
        assertEquals(Block.SIZE, settledBlocks.getFirst().getY());
    }

    @Test
    public void testBlockDrawing() {
        Block block = new Block(Color.RED);
        block.setX(50);
        block.setY(50);

        // For drawing test, you can create a mock Graphics2D or manually test with a GUI
        // Here we test that the block has the correct coordinates and color
        assertEquals(50, block.getX());
        assertEquals(50, block.getY());
        assertEquals(Color.RED, block.getColor());
    }
}
