package controller;

import model.Configurations;
import model.GameLoop;
import model.HighScores;

import javax.swing.*;

public class GameController {

    private final ScreenController screenController;
    private final HighScores highScores;
    private final Configurations configurations;
    private boolean isRunning;

    // Game model
    private GameLoop gameLoop;

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
        gameLoop = new GameLoop(true);
        // Initialize screen controller using the singleton pattern
        screenController = ScreenController.getInstance(mainFrame, this, configurations, highScores);

        // Show splash screen initially
        screenController.showSplashScreen();
        Controls.bindKeys(this.gameLoop);
    }

    private static final class GameControllerHolder {
        // Singleton instance
        private static final GameController instance = new GameController();
    }

    // Public static method to provide the singleton instance
    public static GameController getInstance() {
        return GameControllerHolder.instance;
    }

    public GameLoop getGameLoop(){
        return this.gameLoop;
    }

    // Screen control methods
    public void showMainMenu() {
        screenController.showMainMenu();
    }

    public void showGameScreen() {
        screenController.showGameScreen();
        gameLoop.resetGame();
        isRunning = true;
        gameLoop.startGame();
    }

    public void showSettings() {
        screenController.showSettings();
        Configurations.loadFromFile();
    }

    public void showHighScores() {
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
    }

    public void stopGame() {
        gameLoop.stopGame();
        isRunning = false;
        configurations.saveToFile();
        highScores.saveToFile();
    }

    public boolean isGameRunning() {
        return isRunning;
    }

    public void updateHighScores(int score, String name, String config) {
        highScores.addScore(score, name, config);
        highScores.saveToFile();
    }

    public void updateConfigurations() {
        configurations.saveToFile();
    }

    public boolean checkGameOver() {
        return gameLoop.isGameOver();
    }


    public void setNewScore(int score,int playerNumber,String config){
        System.out.println("Player " + playerNumber + " scored: " + score);
        if(highScores.isTopTen(score)){
            String playerName = JOptionPane.showInputDialog("Player " + playerNumber + " score is in top 10! Enter your name: ");
            highScores.addScore(score, playerName,config);
            highScores.saveToFile();
        }
    }

}
