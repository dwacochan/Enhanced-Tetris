import java.awt.*;

public class GameController {
    private final Frame mainFrame;

    // Screens
    private MainMenu mainMenu;
    private SplashScreen splashScreen;
    private GameLoop gameLoop;
    private Settings settings;
    private HighScores highScores;

    public GameController(){
        mainFrame = new Frame("Tetris");
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new CardLayout());

        mainMenu = new MainMenu(this);
        splashScreen = new SplashScreen(this);
        gameLoop = new GameLoop(this);
        settings = new Settings(this);
        highScores = new HighScores(this);

        mainFrame.add(mainMenu.getPanel(), "MainMenu");
        mainFrame.add(splashScreen.getPanel(), "SplashScreen");
        mainFrame.add(gameLoop.getPanel(), "GameLoop");
        mainFrame.add(settings.getPanel(), "Settings");
        mainFrame.add(highScores.getPanel(), "HighScores");

        ((CardLayout)mainFrame.getLayout()).show(mainFrame, "SplashScreen");

        mainFrame.setVisible(true);
    }

    public void showMainMenu(){
        hideAllScreens();
        ((CardLayout) mainFrame.getLayout()).show(mainFrame, "MainMenu");
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void showSplashScreen(){
        ((CardLayout) mainFrame.getLayout()).show(mainFrame, "SplashScreen");
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void showGameLoop(){
        ((CardLayout) mainFrame.getLayout()).show(mainFrame, "GameLoop");
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void showSettings(){
        ((CardLayout) mainFrame.getLayout()).show(mainFrame, "Settings");
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void showHighScores(){
        ((CardLayout) mainFrame.getLayout()).show(mainFrame, "HighScores");
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void hideAllScreens() {
        for (Component comp : mainFrame.getComponents()) {
            comp.setVisible(false);
        }
    }

}
