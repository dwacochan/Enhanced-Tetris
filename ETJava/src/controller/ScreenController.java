package controller;

import model.Configurations;
import model.HighScores;
import util.AudioManager;
import view.SplashScreen;
import view.*;

import javax.swing.*;
import java.awt.*;

public class ScreenController {
    private static ScreenController instance;
    private final JFrame mainFrame;

    // Screens
    private MainMenu mainMenu;
    private SplashScreen splashScreen;
    private GameScreen gameScreen;
    private ConfigurationScreen configurationScreen;
    private HighScoreScreen highScoreScreen;
    private ManholeScene manholeScene;


    public ScreenController(JFrame mainFrame, GameController gameController, Configurations configurations, HighScores highScores) {
        this.mainFrame = mainFrame;

        mainFrame.getContentPane().setLayout(new CardLayout());

        mainMenu = new MainMenu(gameController);
        splashScreen = new SplashScreen(gameController);
        gameScreen = new GameScreen(gameController);
        configurationScreen = new ConfigurationScreen(gameController, configurations);
        highScoreScreen = new HighScoreScreen(gameController, highScores);

        // Set the names of the panels
        mainMenu.getPanel().setName("view.MainMenu");
        splashScreen.getPanel().setName("view.SplashScreen");
        gameScreen.getPanel().setName("view.GameScreen");
        configurationScreen.getPanel().setName("Settings");
        highScoreScreen.getPanel().setName("view.HighScoreScreen");

        // Add panels to the content pane with card names
        mainFrame.getContentPane().add(mainMenu.getPanel(), "view.MainMenu");
        mainFrame.getContentPane().add(splashScreen.getPanel(), "view.SplashScreen");
        mainFrame.getContentPane().add(gameScreen.getPanel(), "view.GameScreen");
        mainFrame.getContentPane().add(configurationScreen.getPanel(), "Settings");
        mainFrame.getContentPane().add(highScoreScreen.getPanel(), "view.HighScoreScreen");

        mainFrame.setVisible(true);
    }


    // Static method to provide global access to the single instance
    public static synchronized ScreenController getInstance(JFrame mainFrame, GameController gameController, Configurations configurations, HighScores highScores) {
        if (instance == null) {
            instance = new ScreenController(mainFrame, gameController, configurations, highScores);
        }
        return instance;
    }

    public void showMainMenu() {
        mainFrame.setMinimumSize(null);
        mainFrame.setSize(new Dimension(800,600));
        mainFrame.setMinimumSize(mainFrame.getSize());
        mainFrame.revalidate();
        mainFrame.repaint();
        AudioManager.getInstance().playMusic("/resources/mainmenu.wav"); // Play main menu music
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.MainMenu");
    }

    public void showSplashScreen() {
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.SplashScreen");
    }

    public void showGameScreen() {
        AudioManager.getInstance().pauseMusic(); // Stop any music before starting the game
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.GameScreen");
    }

    public void showSettings() {
        AudioManager.getInstance().playMusic("/resources/CafeAmbience.wav"); // Play Cafe Ambience music
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "Settings");
    }

    public void showHighScores() {
        mainFrame.getContentPane().remove(highScoreScreen);
        highScoreScreen = new HighScoreScreen(GameController.getInstance(),GameController.getInstance().getHighScores());
        mainFrame.getContentPane().add(highScoreScreen.getPanel(), "view.HighScoreScreen");
        AudioManager.getInstance().playMusic("/resources/CafeAmbience.wav"); // Play Cafe Ambience music
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.HighScoreScreen");
    }

    public void showManholeScene() {
        // Instantiate ManholeScene only when it's first needed (lazy initialization)
        if (manholeScene == null) {
            manholeScene = new ManholeScene(GameController.getInstance());
            mainFrame.getContentPane().add(manholeScene.getPanel(), "ManholeScene");
        }

        // Stop any currently playing music before starting the manhole scene music
        AudioManager.getInstance().pauseMusic();
        AudioManager.getInstance().playMusic("/resources/ManholeScene.wav");

        // Show the ManholeScene using CardLayout
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "ManholeScene");
    }




    public void hideAllScreens() {
        for (Component comp : mainFrame.getContentPane().getComponents()) {
            comp.setVisible(false);
        }
    }
}
