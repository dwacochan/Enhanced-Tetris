package controller;

import model.Configurations;
import model.HighScores;
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

    private ScreenController(JFrame mainFrame, GameController gameController, Configurations configurations, HighScores highScores) {
        this.mainFrame = mainFrame;

        mainFrame.getContentPane().setLayout(new CardLayout());

        mainMenu = new MainMenu(gameController);
        splashScreen = new SplashScreen(gameController);
        gameScreen = new GameScreen(gameController);
        configurationScreen = new ConfigurationScreen(gameController, configurations);  // Pass model.Configurations to Settings
        highScoreScreen = new HighScoreScreen(gameController, highScores);

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
        mainFrame.revalidate();
        mainFrame.repaint();
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.MainMenu");
    }

    public void showSplashScreen() {
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.SplashScreen");
    }

    public void showGameScreen() {
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.GameScreen");
    }

    public void showSettings() {
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "Settings");
    }

    public void showHighScores() {
        mainFrame.getContentPane().remove(highScoreScreen);
        highScoreScreen = new HighScoreScreen(GameController.getInstance(),GameController.getInstance().getHighScores());
        mainFrame.getContentPane().add(highScoreScreen.getPanel(), "view.HighScoreScreen");
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "view.HighScoreScreen");
    }

    public void hideAllScreens() {
        for (Component comp : mainFrame.getContentPane().getComponents()) {
            comp.setVisible(false);
        }
    }
}
