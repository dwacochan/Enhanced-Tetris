import controller.GameController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Main{
    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.showSplashScreen();
        Timer timer = new Timer(1420, (ActionEvent e) -> gameController.showMainMenu());
        timer.setRepeats(false);
        timer.start();




    }
}
