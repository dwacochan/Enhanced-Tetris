package controller;
import model.PureGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.ExternalPlayer;
import controller.Controls;

import static org.junit.jupiter.api.Assertions.*;

class TestExternalPlayer extends ExternalPlayer {

    public TestExternalPlayer(int gameNumber) {
        super(gameNumber);
    }

    @Override
    public void decideAndMakeBestMove(PureGame pureGame) {
        // Implementation of abstract method for testing
    }
}

public class ExternalPlayerTest {

    private TestExternalPlayer externalPlayer1;
    private TestExternalPlayer externalPlayer2;

    @BeforeEach
    public void setUp() {
        // Create instances of ExternalPlayer for gameNumber 1 and 2
        externalPlayer1 = new TestExternalPlayer(1);
        externalPlayer2 = new TestExternalPlayer(2);

        // Ensure controls are reset before each test
        Controls.ext_1_up = false;
        Controls.ext_1_right = false;
        Controls.ext_1_down = false;
        Controls.ext_1_left = false;

        Controls.ext_2_up = false;
        Controls.ext_2_right = false;
        Controls.ext_2_down = false;
        Controls.ext_2_left = false;
    }

    @Test
    public void testConstructor() {
        // Test if the game number is set properly
        assertNotNull(externalPlayer1);
        assertNotNull(externalPlayer2);
        assertEquals(1, externalPlayer1.getGameNumber());
        assertEquals(2, externalPlayer2.getGameNumber());
    }

    @Test
    public void testMoveLeft() {
        // Test moveLeft for player 1
        externalPlayer1.moveLeft();
        assertTrue(Controls.ext_1_left);

        // Test moveLeft for player 2
        externalPlayer2.moveLeft();
        assertTrue(Controls.ext_2_left);
    }

    @Test
    public void testMoveRight() {
        // Test moveRight for player 1
        externalPlayer1.moveRight();
        assertTrue(Controls.ext_1_right);

        // Test moveRight for player 2
        externalPlayer2.moveRight();
        assertTrue(Controls.ext_2_right);
    }

    @Test
    public void testMoveUp() {
        // Test moveUp for player 1
        externalPlayer1.moveUp();
        assertTrue(Controls.ext_1_up);

        // Test moveUp for player 2
        externalPlayer2.moveUp();
        assertTrue(Controls.ext_2_up);
    }

    @Test
    public void testMoveDown() {
        // Test moveDown for player 1
        externalPlayer1.moveDown();
        assertTrue(Controls.ext_1_down);

        // Test moveDown for player 2
        externalPlayer2.moveDown();
        assertTrue(Controls.ext_2_down);
    }

    @Test
    public void testStartDownwardMovement() throws InterruptedException {
        // Start downward movement for player 1
        externalPlayer1.startDownwardMovement();

        // Wait for the timer to trigger (simulate the delay for testing)
        Thread.sleep(600); // Adjust sleep time according to downward movement interval

        // Verify that moveDown has been triggered for player 1
        assertTrue(Controls.ext_1_down);
    }

    @Test
    public void testStop() throws InterruptedException {
        // Start the downward movement and then stop it
        externalPlayer1.startDownwardMovement();
        externalPlayer1.stop();

        Thread.sleep(100);
        // Test if the stop method works correctly
        assertFalse(Controls.ext_1_left);
        assertFalse(Controls.ext_1_right);
        assertFalse(Controls.ext_1_up);
        assertFalse(Controls.ext_1_down);
    }

    @Test
    public void testResetControls() throws InterruptedException {
        // Simulate some movements
        externalPlayer1.moveLeft();
        externalPlayer1.moveRight();
        externalPlayer1.moveUp();
        externalPlayer1.moveDown();

        // Test that the controls are active
        assertTrue(Controls.ext_1_left);
        assertTrue(Controls.ext_1_right);
        assertTrue(Controls.ext_1_up);
        assertTrue(Controls.ext_1_down);

        // Call resetControls and check after delay
        externalPlayer1.resetControls();

        // Wait for the 50ms delay to pass
        Thread.sleep(100);

        // Test that all controls have been reset
        assertFalse(Controls.ext_1_left);
        assertFalse(Controls.ext_1_right);
        assertFalse(Controls.ext_1_up);
        assertFalse(Controls.ext_1_down);
    }
}
