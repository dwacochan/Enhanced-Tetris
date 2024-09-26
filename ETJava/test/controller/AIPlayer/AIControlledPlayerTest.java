package controller.AIPlayer;

import model.OpMove;
import model.PureGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AIControlledPlayerTest {

    private AIControlledPlayer aiPlayer;
    private PureGame pureGame;

    @BeforeEach
    void setUp() {
        aiPlayer = new AIControlledPlayer(1); // Initialize AI player with game number 1

        // Set up an empty board and a sample current shape for the game
        int width = 10;
        int height = 20;
        int[][] board = new int[height][width]; // Empty board
        int[][] currentShape = {{0, 1, 0}, {1, 1, 1}}; // Example T shape
        int[][] nextShape = {{1, 1, 1, 1}}; // Example I shape

        pureGame = new PureGame(width, height, board, currentShape, nextShape); // Initialize PureGame
    }

    @Test
    void testGetCalibrationOffset() {
        // Test calibration offset for known tetromino type and rotation
        int offset = aiPlayer.getCalibrationOffset(AIControlledPlayer.TetrominoType.O, 1);
        assertEquals(1, offset, "Calibration offset for Tetromino O at rotation 1 should be 1");

        offset = aiPlayer.getCalibrationOffset(AIControlledPlayer.TetrominoType.I, 0);
        assertEquals(1, offset, "Calibration offset for Tetromino I at rotation 0 should be 1");

        offset = aiPlayer.getCalibrationOffset(AIControlledPlayer.TetrominoType.T, 1);
        assertEquals(1, offset, "Calibration offset for Tetromino T at rotation 1 should be 1");

    }

    @Test
    void testIdentifyShape() {
        // Test identifying shape I
        int[][] shapeI = {{1, 1, 1, 1}};
        AIControlledPlayer.TetrominoType type = aiPlayer.identifyShape(shapeI);
        assertEquals(AIControlledPlayer.TetrominoType.I, type, "Shape I should be identified as TetrominoType I");

        // Test identifying shape O
        int[][] shapeO = {{1, 1}, {1, 1}};
        type = aiPlayer.identifyShape(shapeO);
        assertEquals(AIControlledPlayer.TetrominoType.O, type, "Shape O should be identified as TetrominoType O");

        // Test identifying shape T
        int[][] shapeT = {{0, 1, 0}, {1, 1, 1}};
        type = aiPlayer.identifyShape(shapeT);
        assertEquals(AIControlledPlayer.TetrominoType.T, type, "Shape T should be identified as TetrominoType T");

        // Test identifying shape Z
        int[][] shapeZ = {{1, 1, 0}, {0, 1, 1}};
        type = aiPlayer.identifyShape(shapeZ);
        assertEquals(AIControlledPlayer.TetrominoType.Z, type, "Shape Z should be identified as TetrominoType Z");
    }

    @Test
    void testComputeBestMove() {
        // Compute the best move based on the current board and shape
        OpMove bestMove = aiPlayer.computeBestMove(pureGame);

        // Verify the move (this is a simplified test; you can expand it based on AI behavior)
        assertNotNull(bestMove, "Best move should not be null");
        assertEquals(0, bestMove.opRotate(), "The best move should have no rotations");
    }

    @Test
    void testShapeArrayToString() {
        // Test converting a shape array to string for Tetromino T
        int[][] shapeT = {{0, 1, 0}, {1, 1, 1}};
        String result = aiPlayer.shapeArrayToString(shapeT);
        assertEquals("010|111", result, "Shape T should be correctly converted to string '010|111'");

        // Test converting a shape array to string for Tetromino O
        int[][] shapeO = {{1, 1}, {1, 1}};
        result = aiPlayer.shapeArrayToString(shapeO);
        assertEquals("11|11", result, "Shape O should be correctly converted to string '11|11'");
    }
}
