package controller;

import model.PureGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExternalPlayerTest {

    private ExternalPlayer externalPlayer1;
    private ExternalPlayer externalPlayer2;

    @BeforeEach
    public void setUp() {
        // Initialize a new PureGame instance since it's used in the abstract method
        PureGame pureGame = new PureGame(10, 20, new int[10][20], new int[4][4], new int[4][4]);

        // Create two concrete instances of ExternalPlayer to test both players
        externalPlayer1 = new ConcreteExternalPlayer(1);
        externalPlayer2 = new ConcreteExternalPlayer(2);
    }

    @Test
    public void testMoveLeft() {
        // Test Player 1 (External)
        externalPlayer1.moveLeft();
        assertTrue(Controls.ext_1_left, "Player 1 (External) should have left control set");

        // Test Player 2 (External)
        externalPlayer2.moveLeft();
        assertTrue(Controls.ext_2_left, "Player 2 (External) should have left control set");
    }

    @Test
    public void testMoveRight() {
        // Test Player 1 (External)
        externalPlayer1.moveRight();
        assertTrue(Controls.ext_1_right, "Player 1 (External) should have right control set");

        // Test Player 2 (External)
        externalPlayer2.moveRight();
        assertTrue(Controls.ext_2_right, "Player 2 (External) should have right control set");
    }

    @Test
    public void testMoveUp() {
        // Test Player 1 (External)
        externalPlayer1.moveUp();
        assertTrue(Controls.ext_1_up, "Player 1 (External) should have up control set");

        // Test Player 2 (External)
        externalPlayer2.moveUp();
        assertTrue(Controls.ext_2_up, "Player 2 (External) should have up control set");
    }

    @Test
    public void testMoveDown() {
        // Test Player 1 (External)
        externalPlayer1.moveDown();
        assertTrue(Controls.ext_1_down, "Player 1 (External) should have down control set");

        // Test Player 2 (External)
        externalPlayer2.moveDown();
        assertTrue(Controls.ext_2_down, "Player 2 (External) should have down control set");
    }

    @Test
    public void testStartDownwardMovement() throws InterruptedException {
        // Start downward movement and verify the control is triggered
        externalPlayer1.startDownwardMovement();

        // Wait a short time to allow the timer to trigger
        Thread.sleep(550); // Wait slightly longer than the scheduled interval

        // Check that the down control is being triggered repeatedly
        assertTrue(Controls.ext_1_down, "Player 1 should have down control set after starting downward movement");

        externalPlayer1.stop(); // Stop the movement after the test
    }

    @Test
    public void testStop() throws InterruptedException {
        // Start downward movement to simulate active controls
        externalPlayer1.startDownwardMovement();

        // Stop the external player (this triggers the resetControls with a 50ms delay)
        externalPlayer1.stop();

        // Wait for the controls to reset (slightly longer than 50ms to account for the timer delay)
        Thread.sleep(100);

        // Ensure the timers are stopped and controls reset
        assertFalse(Controls.ext_1_left, "Player 1 (External) left control should be reset");
        assertFalse(Controls.ext_1_right, "Player 1 (External) right control should be reset");
        assertFalse(Controls.ext_1_up, "Player 1 (External) up control should be reset");
        assertFalse(Controls.ext_1_down, "Player 1 (External) down control should be reset");
    }

    @Test
    public void testResetControls() throws InterruptedException {
        // Trigger movements
        externalPlayer1.moveLeft();
        externalPlayer1.moveRight();
        externalPlayer1.moveUp();
        externalPlayer1.moveDown();

        // Simulate calling resetControls() to release all keys
        externalPlayer1.resetControls();

        // Allow time for the reset to take effect (since it's scheduled with a delay)
        Thread.sleep(100); // Wait for the scheduled timer task to reset controls

        // Check that the control states are reset for Player 1
        assertFalse(Controls.ext_1_left, "Player 1 left control should be reset");
        assertFalse(Controls.ext_1_right, "Player 1 right control should be reset");
        assertFalse(Controls.ext_1_up, "Player 1 up control should be reset");
        assertFalse(Controls.ext_1_down, "Player 1 down control should be reset");
    }

    // A concrete class to test the abstract ExternalPlayer
    static class ConcreteExternalPlayer extends ExternalPlayer {
        public ConcreteExternalPlayer(int gameNumber) {
            super(gameNumber);
        }

        @Override
        public void decideAndMakeBestMove(PureGame pureGame) {
            // Logic for testing purposes (can be mocked if needed)
        }
    }
}
