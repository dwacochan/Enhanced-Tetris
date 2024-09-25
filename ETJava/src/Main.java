import controller.GameController;

import view.SplashScreen;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Retrieve the singleton instance of GameController
        GameController gameController = GameController.getInstance();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SplashScreen(gameController);
            }
        });

    }
}
