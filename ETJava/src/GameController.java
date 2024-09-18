import javax.swing.*;
import java.awt.*;
import game.*;

public class GameController {
    private final JFrame mainFrame;
    private boolean isRunning;

    // Data models
    private HighScores highScores;
    private Configurations configurations;

    // Screens
    private MainMenu mainMenu;
    private SplashScreen splashScreen;
    private GameLoop gameLoop;
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
        gameLoop = new GameLoop(this);
        configurationScreen = new ConfigurationScreen(this, configurations);  // Pass Configurations to Settings
        highScoreScreen = new HighScoreScreen(this, highScores);

        mainFrame.getContentPane().add(mainMenu.getPanel(), "MainMenu");
        mainFrame.getContentPane().add(splashScreen.getPanel(), "SplashScreen");
        mainFrame.getContentPane().add(gameLoop.getPanel(), "GameLoop");
        mainFrame.getContentPane().add(configurationScreen.getPanel(), "Settings");
        mainFrame.getContentPane().add(highScoreScreen.getPanel(), "HighScoreScreen");

        mainFrame.setVisible(true);
    }

    public void showMainMenu(){
        hideAllScreens();
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "MainMenu");
    }

    public void showSplashScreen(){
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "SplashScreen");
    }

    public void showGameLoop(){
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "GameLoop");
        gameLoop.gameArea.startGame();
        isRunning = true;
    }

    public void showSettings(){
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "Settings");
        configurations = Configurations.loadFromFile();
    }

    public void showHighScores(){
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "HighScoreScreen");
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
            gameLoop.gameArea.pauseGame();
            isRunning = false;
        }
    }

    public void resumeGame() {
        if (!isRunning) {
            gameLoop.gameArea.resumeGame();
            isRunning = true;
        }
    }

    public void stopGame() {
        gameLoop.gameArea.stopGame();
        isRunning = false;
        gameLoop.gameArea.resetGame();
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
