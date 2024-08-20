
import javax.swing.*;
import java.awt.*;

public class GameController {
    private final JFrame mainFrame;

    // Screens
    private MainMenu mainMenu;
    private SplashScreen splashScreen;
    private GameLoop gameLoop;
    private Settings settings;
    private HighScores highScores;

    public GameController(){
        mainFrame = new JFrame("Tetris");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.getContentPane().setLayout(new CardLayout());

        mainMenu = new MainMenu(this);
        splashScreen = new SplashScreen(this);
        gameLoop = new GameLoop(this);
        settings = new Settings(this);
        highScores = new HighScores(this);

        mainFrame.getContentPane().add(mainMenu.getPanel(), "MainMenu");
        mainFrame.getContentPane().add(splashScreen.getPanel(), "SplashScreen");
        mainFrame.getContentPane().add(gameLoop.getPanel(), "GameLoop");
        mainFrame.getContentPane().add(settings.getPanel(), "Settings");
        mainFrame.getContentPane().add(highScores.getPanel(), "HighScores");

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

        //starts the game when the screen is shown
        gameLoop.gameArea.startGame();
    }

    public void showSettings(){
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "Settings");
    }

    public void showHighScores(){
        ((CardLayout) mainFrame.getContentPane().getLayout()).show(mainFrame.getContentPane(), "HighScores");
    }

    public void hideAllScreens() {
        for (Component comp : mainFrame.getContentPane().getComponents()) {
            comp.setVisible(false);
        }
    }
}