package controller;

import model.Configurations;
import model.HighScores;
import model.PlayerType;
import util.AudioManager;

import javax.swing.*;

public class GameController {

    private final ScreenController screenController;
    private final HighScores highScores;
    private final Configurations configurations;
    private boolean isRunning;

    // Game model
    private GameLoop gameLoop;

    // Music file path for the main menu
    private final String menuMusicFilePath = "/resources/mainmenu.wav";

    // Private constructor to prevent direct instantiation
    private GameController() {
        // Load configurations and high scores from file
        configurations = Configurations.loadFromFile();
        highScores = new HighScores();
        highScores.loadFromFile();

        // Set up the main frame
        JFrame mainFrame = new JFrame("Tetris");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameLoop = new GameLoop(configurations.isExtendModeOn(), configurations.getPlayer1Type(), configurations.getPlayer2Type(), this);

        // Initialize screen controller using the singleton pattern
        screenController = ScreenController.getInstance(mainFrame, this, configurations, highScores);

        // Show splash screen initially
        screenController.showSplashScreen();
        Controls.bindKeys(this.gameLoop);
    }

    public Configurations getConfigurations() {
        return configurations;
    }

    private static final class GameControllerHolder {
        // Singleton instance
        private static final GameController instance = new GameController();
    }

    // Public static method to provide the singleton instance
    public static GameController getInstance() {
        return GameControllerHolder.instance;
    }

    public GameLoop getGameLoop() {
        return this.gameLoop;
    }

    // Screen control methods
    public void showMainMenu() {
        // Resume or play the main menu music
        AudioManager.getInstance().playMusic(menuMusicFilePath);

        // Show the main menu screen
        screenController.showMainMenu();
    }

    public void showGameScreen() {
        // Pause the main menu music when switching to the game screen
        AudioManager.getInstance().pauseMusic();


        // Show the game screen and start the game
        screenController.showGameScreen();
        gameLoop.resetGame();
        isRunning = true;
        gameLoop.startGame();
        if(configurations.isMusicOn()){
            AudioManager.getInstance().playMusic("/resources/GameMusic.wav");
        }

    }

    public void showSettings() {
        // Pause the main menu music when switching to the settings screen
        AudioManager.getInstance().pauseMusic();

        // Show the settings screen and load configurations
        screenController.showSettings();
        Configurations.loadFromFile();
    }

    public void showHighScores() {
        // Pause the main menu music when switching to the high scores screen
        AudioManager.getInstance().pauseMusic();

        // Show the high scores screen and load high scores
        screenController.showHighScores();
        highScores.loadFromFile();
    }

    public void hideAllScreens() {
        screenController.hideAllScreens();
    }

    // Game control methods
    public void pauseGame() {
        if (isRunning) {
            gameLoop.pauseGame();
            isRunning = false;
        }
    }

    public void resumeGame() {
        if (!isRunning) {
            gameLoop.resumeGame();
            isRunning = true;
        }
        if(configurations.isMusicOn()){
            AudioManager.getInstance().playMusic("/resources/GameMusic.wav");
        }

    }

    public void stopGame() {
        gameLoop.stopGame();
        AudioManager.getInstance().stopMusic();
        isRunning = false;
        configurations.saveToFile();
        highScores.saveToFile();
    }

    public boolean isGameRunning() {
        return isRunning;
    }

    public void updateConfigurations() {
        configurations.saveToFile();
    }

    public boolean checkGameOver() {
        return gameLoop.isGameOver();
    }

    public void setNewScore(int score, int playerNumber, String config) {
        System.out.println("Player " + playerNumber + " scored: " + score);
        if (highScores.isTopTen(score)) {
            switch (playerNumber) {
                case 1 -> {
                    if (configurations.getPlayer1Type() != PlayerType.HUMAN) {
                        highScores.addScore(score, configurations.getPlayer1Type().toString(), config);
                        return;
                    }
                }
                case 2 -> {
                    if (configurations.getPlayer2Type() != PlayerType.HUMAN) {
                        highScores.addScore(score, configurations.getPlayer2Type().toString(), config);
                        return;
                    }
                }
            }

            String playerName = JOptionPane.showInputDialog("Player " + playerNumber + " score is in top 10! Enter your name: ");
            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Anonymous";  // Default name
            }
            highScores.addScore(score, playerName, config);
            highScores.saveToFile();
        }
    }

    public HighScores getHighScores() {
        return highScores;
    }
}
