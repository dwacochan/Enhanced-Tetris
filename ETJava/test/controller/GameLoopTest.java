package controller;

import controller.facade.GameFacade;
import model.Configurations;
import model.PlayerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class GameLoopTest {

    private GameLoop singlePlayerLoop;
    private GameLoop twoPlayerLoop;
    private GameController gameController;

    @BeforeEach
    public void setUp() {
        // Mocking GameController and Configurations
        gameController = GameController.getInstance();
        Configurations configurations = new Configurations();

        // Set up configurations for testing
        configurations.setGameLevel(1);
        configurations.setPlayer1Type(PlayerType.HUMAN);
        configurations.setPlayer2Type(PlayerType.HUMAN);
        configurations.setExtendModeOn(false);

        // Single-player game loop
        singlePlayerLoop = new GameLoop(false, PlayerType.HUMAN, PlayerType.HUMAN, gameController);

        // Two-player game loop
        twoPlayerLoop = new GameLoop(true, PlayerType.HUMAN, PlayerType.HUMAN, gameController);
    }

    @AfterEach
    public void tearDown(){

    }

    @Test
    public void testConstructorSinglePlayer() {
        assertNotNull(singlePlayerLoop.getPlayerFacade(1));
        assertNull(singlePlayerLoop.getPlayerFacade(2));
        assertFalse(singlePlayerLoop.isGameOver());
    }

    @Test
    public void testConstructorTwoPlayer() {
        assertNotNull(twoPlayerLoop.getPlayerFacade(1));
        assertNotNull(twoPlayerLoop.getPlayerFacade(2));
        assertFalse(twoPlayerLoop.isGameOver());
    }

    @Test
    public void testStartGameSinglePlayer() {
        singlePlayerLoop.startGame();

        // Verify player 1 facade is initialized
        assertNotNull(singlePlayerLoop.getPlayerFacade(1));
    }

    @Test
    public void testStartGameTwoPlayer() {
        twoPlayerLoop.startGame();

        // Verify both facades are initialized in two-player mode
        assertNotNull(twoPlayerLoop.getPlayerFacade(1));
        assertNotNull(twoPlayerLoop.getPlayerFacade(2));
    }

    @Test
    public void testPauseAndResumeGame() {
        singlePlayerLoop.startGame();
        assertFalse(singlePlayerLoop.isPaused());

        // Pause the game
        singlePlayerLoop.pauseGame();
        assertTrue(singlePlayerLoop.isPaused());

        // Resume the game
        singlePlayerLoop.resumeGame();
        assertFalse(singlePlayerLoop.isPaused());
    }

    @Test
    public void testStopGameSinglePlayer() {
        singlePlayerLoop.startGame();
        assertTrue(singlePlayerLoop.isRunning());

        // Stop the game
        singlePlayerLoop.stopGame();
        assertFalse(singlePlayerLoop.isRunning());
    }

    @Test
    public void testStopGameTwoPlayer() {
        twoPlayerLoop.startGame();
        assertTrue(twoPlayerLoop.isRunning());

        // Stop the game
        twoPlayerLoop.stopGame();
        assertFalse(twoPlayerLoop.isRunning());
    }

    @Test
    public void testGameOverSinglePlayer() throws InterruptedException {
        singlePlayerLoop.startGame();

        GameFacade player1Facade = singlePlayerLoop.getPlayerFacade(1);
        assertNotNull(player1Facade);

        // Simulate game over
        player1Facade.setGameOver();
        assertTrue(player1Facade.isGameOver());
    }

    @Test
    public void testGameOverTwoPlayer() {
        twoPlayerLoop.startGame();

        GameFacade player1Facade = twoPlayerLoop.getPlayerFacade(1);
        GameFacade player2Facade = twoPlayerLoop.getPlayerFacade(2);

        assertNotNull(player1Facade);
        assertNotNull(player2Facade);

        // Simulate game over for both players
        player1Facade.setGameOver();
        player2Facade.setGameOver();

        assertTrue(twoPlayerLoop.isGameOver());
    }

    @Test
    public void testResetGame() {
        twoPlayerLoop.startGame();

        GameFacade player1Facade = twoPlayerLoop.getPlayerFacade(1);
        GameFacade player2Facade = twoPlayerLoop.getPlayerFacade(2);

        assertNotNull(player1Facade);
        assertNotNull(player2Facade);

        // Simulate game over
        player1Facade.setGameOver();
        player2Facade.setGameOver();

        assertTrue(twoPlayerLoop.isGameOver());

        // Reset the game and verify it's not game over anymore
        twoPlayerLoop.resetGame();
        assertFalse(twoPlayerLoop.isGameOver());
    }

    @Test
    public void testGetServerControlledPlayer() {
        twoPlayerLoop.startGame();
        assertNull(twoPlayerLoop.getServerControlledPlayer(1)); // Not using server player

        // Simulate server player
        gameController.getConfigurations().setPlayer1Type(PlayerType.SERVER);
        twoPlayerLoop.startGame();
        ServerControlledPlayer serverPlayer1 = twoPlayerLoop.getServerControlledPlayer(1);
        assertNotNull(serverPlayer1);
    }

    @Test
    public void testAdjustGameDimensions() {
        singlePlayerLoop.startGame();

        int newWidth = 400;
        int newHeight = 800;

        singlePlayerLoop.adjustGameDimensions(newWidth, newHeight);
        GameFacade player1Facade = singlePlayerLoop.getPlayerFacade(1);

        assertEquals(newWidth, player1Facade.getGameplay().getWidth());
        assertEquals(newHeight, player1Facade.getGameplay().getHeight());
    }
}
