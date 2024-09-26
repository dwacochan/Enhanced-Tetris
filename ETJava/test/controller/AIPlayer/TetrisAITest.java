package controller.AIPlayer;

import model.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TetrisAITest {

    private TetrisAI tetrisAI;

    @BeforeEach
    void setUp() {
        BoardEvaluator evaluator = new BoardEvaluator();
        tetrisAI = new TetrisAI(evaluator);
    }

    @Test
    void testFindBestMove_EmptyBoard() {
        int[][] board = new int[20][10]; // Empty board
        int[][] pieceShape = {{1, 1, 1, 1}}; // I-shaped tetromino

        Move bestMove = tetrisAI.findBestMove(board, pieceShape);

        assertNotNull(bestMove, "Best move should not be null");
        // Since the board is empty, any move is acceptable; check that move is within valid range
        assertTrue(bestMove.getColumn() >= -3 && bestMove.getColumn() <= 9, "Best move column should be within valid range");
        assertTrue(bestMove.getRotation() >= 0 && bestMove.getRotation() <= 3, "Best move rotation should be between 0 and 3");
    }

    @Test
    void testRotateShape() {
        int[][] shape = {{1, 0}, {1, 0}, {1, 1}}; // L-shaped tetromino
        int rotations = 1;

        int[][] rotatedShape = tetrisAI.rotateShape(shape, rotations);

        int[][] expectedShape = {{1, 1, 1}, {1, 0, 0}}; // Expected shape after one rotation

        assertArrayEquals(expectedShape, rotatedShape, "Rotated shape should match expected shape after one rotation");
    }


    @Test
    void testGetRightPadding() {
        int[][] shape = {{1, 0, 0}, {1, 1, 0}, {1, 0, 0}}; // Sample shape with right padding
        int rightPadding = tetrisAI.getRightPadding(shape);

        assertEquals(2, rightPadding, "Right padding should be 2");
    }

    @Test
    void testSimulateDrop() {
        int[][] board = new int[20][10];
        int[][] shape = {{1, 1}, {1, 1}}; // O-shaped tetromino
        int col = 4;

        int[][] newBoard = tetrisAI.simulateDrop(board, shape, col);

        // The piece should be at the bottom of the board at column 4
        for (int y = 18; y < 20; y++) {
            for (int x = 4; x < 6; x++) {
                assertEquals(1, newBoard[y][x], "Block should be placed at the bottom at the correct position");
            }
        }
    }

    @Test
    void testCanPlace_ValidPlacement() {
        int[][] board = new int[20][10];
        int[][] shape = {{1, 1}, {1, 1}}; // O-shaped tetromino
        int row = 0;
        int col = 0;

        boolean canPlace = tetrisAI.canPlace(board, shape, row, col);

        assertTrue(canPlace, "Should be able to place shape at the top-left corner on an empty board");
    }

    @Test
    void testCanPlace_InvalidPlacement() {
        int[][] board = new int[20][10];
        // Place a block at the top-left corner
        board[0][0] = 1;
        int[][] shape = {{1, 1}, {1, 1}}; // O-shaped tetromino
        int row = 0;
        int col = 0;

        boolean canPlace = tetrisAI.canPlace(board, shape, row, col);

        assertFalse(canPlace, "Should not be able to place shape where blocks already exist");
    }

    @Test
    void testPlacePiece() {
        int[][] board = new int[20][10];
        int[][] shape = {{1, 1}, {1, 1}}; // O-shaped tetromino
        int row = 18;
        int col = 4;

        tetrisAI.placePiece(board, shape, row, col);

        // Verify that the shape is placed correctly
        for (int y = 18; y < 20; y++) {
            for (int x = 4; x < 6; x++) {
                assertEquals(1, board[y][x], "Block should be placed at the specified position");
            }
        }
    }

    @Test
    void testCopyBoard() {
        int[][] board = new int[20][10];
        board[0][0] = 1;
        int[][] copiedBoard = tetrisAI.copyBoard(board);

        // Modify the original board
        board[0][0] = 0;

        // Ensure the copied board remains unchanged
        assertEquals(1, copiedBoard[0][0], "Copied board should not reflect changes in the original board");
    }

    @Test
    void testGetTopPadding() {
        int[][] shape = {{0, 0, 1}, {1, 1, 1}}; // T-shaped tetromino with padding at the top
        int topPadding = tetrisAI.getTopPadding(shape);

        assertEquals(0, topPadding, "Top padding should be 0 since first row contains a block");
    }

    @Test
    void testFindBestMove_BlockedBoard() {
        int[][] board = new int[20][10];
        // Fill the bottom row
        for (int x = 0; x < 10; x++) {
            board[19][x] = 1;
        }
        int[][] pieceShape = {{1, 1, 1, 1}}; // I-shaped tetromino

        Move bestMove = tetrisAI.findBestMove(board, pieceShape);

        assertNotNull(bestMove, "Best move should not be null even if the board is blocked");
    }

    @Test
    void testFindBestMove_AllRotations() {
        int[][] board = new int[20][10];
        int[][] pieceShape = {{1, 0}, {1, 0}, {1, 1}}; // L-shaped tetromino

        Move bestMove = tetrisAI.findBestMove(board, pieceShape);

        assertNotNull(bestMove, "Best move should not be null");
        assertTrue(bestMove.getRotation() >= 0 && bestMove.getRotation() <= 3, "Rotation should be between 0 and 3");
    }

    @Test
    void testRotateShape_FullRotation() {
        int[][] shape = {{1, 1}, {1, 1}}; // O-shaped tetromino
        int rotations = 4;

        int[][] rotatedShape = tetrisAI.rotateShape(shape, rotations);

        assertArrayEquals(shape, rotatedShape, "After 4 rotations, the shape should return to original");
    }

    @Test
    void testGetLeftPadding_NoPadding() {
        int[][] shape = {{1, 1}, {1, 1}}; // O-shaped tetromino with no padding
        int leftPadding = tetrisAI.getLeftPadding(shape);

        assertEquals(0, leftPadding, "Left padding should be 0 for shapes without left padding");
    }

    @Test
    void testGetRightPadding_NoPadding() {
        int[][] shape = {{1, 1}, {1, 1}}; // O-shaped tetromino with no padding
        int rightPadding = tetrisAI.getRightPadding(shape);

        assertEquals(0, rightPadding, "Right padding should be 0 for shapes without right padding");
    }


    @Test
    void testSimulateDrop_OnFilledBoard() {
        int[][] board = new int[20][10];
        // Fill bottom half of the board
        for (int y = 10; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                board[y][x] = 1;
            }
        }
        int[][] shape = {{1, 1}, {1, 1}}; // O-shaped tetromino
        int col = 4;

        int[][] newBoard = tetrisAI.simulateDrop(board, shape, col);

        // The piece should be placed just above the filled area
        for (int y = 8; y < 10; y++) {
            for (int x = 4; x < 6; x++) {
                assertEquals(1, newBoard[y][x], "Block should be placed just above the filled area");
            }
        }
    }

    @Test
    void testCanPlace_OutOfBounds() {
        int[][] board = new int[20][10];
        int[][] shape = {{1, 1}, {1, 1}}; // O-shaped tetromino
        int row = 0;
        int col = 9; // Placing at the far right, should be invalid

        boolean canPlace = tetrisAI.canPlace(board, shape, row, col);

        assertFalse(canPlace, "Should not be able to place shape out of bounds");
    }

    @Test
    void testPlacePiece_OutOfBounds() {
        int[][] board = new int[20][10];
        int[][] shape = {{1, 1}, {1, 1}}; // O-shaped tetromino
        int row = 0;
        int col = 9;

        // Attempting to place out of bounds should not throw an exception
        assertDoesNotThrow(() -> tetrisAI.placePiece(board, shape, row, col), "Placing piece out of bounds should not throw an exception");
    }

    @Test
    void testCopyBoard_DifferentReference() {
        int[][] board = new int[20][10];
        int[][] copiedBoard = tetrisAI.copyBoard(board);

        assertNotSame(board, copiedBoard, "Copied board should be a different reference from the original");
    }


}
