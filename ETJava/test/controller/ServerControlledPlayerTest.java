package controller;

import model.OpMove;
import model.PureGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerControlledPlayerTest {

    @AfterEach
    public void tearDown() {
        // Cancel any running timers to avoid interference between tests
        // This assumes you have access to the timers; adjust as needed
    }

    @Test
    public void testDecideAndMakeBestMove() throws InterruptedException {
        // Given
        int gameNumber = 1;
        OpMove predefinedMove = new OpMove(3, 2); // Example move: move 3 units right, rotate 2 times
        TestableServerControlledPlayer player = new TestableServerControlledPlayer(gameNumber, predefinedMove);
        // Create a PureGame instance with required parameters
        PureGame pureGame = getPureGame();

        // Use a CountDownLatch to wait for the asynchronous tasks to complete
        int expectedActions = (Math.abs(predefinedMove.opRotate())) + (pureGame.getWidth() / 2) + predefinedMove.opX() + 1; // +1 for startDownwardMovement
        CountDownLatch latch = new CountDownLatch(expectedActions);

        // Modify movement methods to count down the latch
        player.actions = new ArrayList<String>() {
            @Override
            public boolean add(String s) {
                latch.countDown();
                return super.add(s);
            }
        };

        // When
        player.decideAndMakeBestMove(pureGame);

        // Wait for the actions to complete (timeout after a reasonable time)
        boolean completed = latch.await(5, TimeUnit.SECONDS);

        // Then
        assertTrue(completed, "The player did not complete actions in time");

        // Verify the sequence of actions
        int rotateCount = Math.abs(predefinedMove.opRotate());
        int moveLeftCount = pureGame.getWidth() / 2;
        int moveRightCount = predefinedMove.opX();

        // Build expected actions list
        List<String> expectedAction = new ArrayList<>();
        for (int i = 0; i < rotateCount; i++) {
            expectedAction.add("moveUp");
        }
        for (int i = 0; i < moveLeftCount; i++) {
            expectedAction.add("moveLeft");
        }
        for (int i = 0; i < moveRightCount; i++) {
            expectedAction.add("moveRight");
        }
        expectedAction.add("startDownwardMovement");

        // Assert that the actions match
        assertEquals(expectedAction, player.actions, "The sequence of actions should match the expected sequence");
    }

    private static PureGame getPureGame() {
        int width = 10;
        int height = 20;
        int[][] cells = new int[height][width]; // Assuming an empty game board
        int[][] currentShape = new int[][] {
                {1, 1},
                {1, 1}
        }; // For example, a square block
        int[][] nextShape = new int[][] {
                {1, 0},
                {1, 0},
                {1, 1}
        }; // For example, an L-shaped block

        // Initialize the game board cells if necessary
        // For example, you might set certain cells to represent existing blocks

        PureGame pureGame = new PureGame(width, height, cells, currentShape, nextShape);
        return pureGame;
    }
}
