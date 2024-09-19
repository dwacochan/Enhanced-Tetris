package controller;

import javax.swing.*;
import java.awt.*;

import model.*;
import view.SplashScreen;
import view.*;

public class GameController {
    private final JFrame mainFrame;
    private boolean isRunning;

    // Data models
    private HighScores highScores;
    private Configurations configurations;

    // Screens
    private MainMenu mainMenu;
    private SplashScreen splashScreen;
    private GameScreen gameScreen;
    private ConfigurationScreen configurationScreen;
    private HighScoreScreen highScoreScreen;

    public GameController(){
        highScores = new HighScores();
        configurations = Configurations.loadFromFile();
        highScores = new HighScores();
        highScores.loadFromFile();

        mainFrame = new JFrame("Tetris");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.getContentPane().setLayout(new CardLayout());

        mainMenu = new MainMenu(this);
        splashScreen = new SplashScreen(this);
        gameScreen = new GameScreen(this);
        configurationScreen = new ConfigurationScreen(this, configurations);  // Pass model.Configurations to Settings
        highScoreScreen = new HighScoreScreen(this, highScores);

        mainFrame.getContentPane().add(mainMenu.getPanel(), "view.MainMenu");
        mainFrame.getContentPane().add(splashScreen.getPanel(), "view.SplashScreen");
        mainFrame.getContentPane().add(gameScreen.getPanel(), "view.GameScreen");
        mainFrame.getContentPane().add(configurationScreen.getPanel(), "Settings");
        mainFrame.getContentPane().add(highScoreScreen.getPanel(), "view.HighScoreScreen");

        mainFrame.setVisible(true);
    }

    public void showMainMenu(){
        hideAllScreens();
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.MainMenu");
    }

    public void showSplashScreen(){
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.SplashScreen");
    }

    public void showGameLoop(){
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.GameScreen");
        gameScreen.gameArea.startGame();
        isRunning = true;
    }

    public void showSettings(){
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "Settings");
        configurations = Configurations.loadFromFile();
    }

    public void showHighScores(){
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.HighScoreScreen");
    }

    public void hideAllScreens() {
        for (Component comp : mainFrame.getContentPane().getComponents()) {
            comp.setVisible(false);
        }
    }

    public boolean isGameRunning() {
        return isRunning;
    }

    public void pauseGame() {
        if (isRunning) {
            gameScreen.gameArea.pauseGame();
            isRunning = false;
        }
    }

    public void resumeGame() {
        if (!isRunning) {
            gameScreen.gameArea.resumeGame();
            isRunning = true;
        }
    }

    public void stopGame() {
        gameScreen.gameArea.stopGame();
        isRunning = false;
        gameScreen.gameArea.resetGame();
        configurations.saveToFile();
        highScores.saveToFile();
    }

    public void updateHighScores(int score, String name) {
        highScores.addScore(score, name);
        highScores.saveToFile();
    }

    public void updateConfigurations() {
        configurations.saveToFile();
    }

    public boolean checkGameOver(){
        return Gameplay.gameOver;
    }
}
