import controller.GameController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Main {
    public static void main(String[] args) {
        // Retrieve the singleton instance of GameController
        GameController gameController = GameController.getInstance();

        // Set a timer to transition from the splash screen to the main menu after 1420 milliseconds
        Timer timer = new Timer(1420, (ActionEvent e) -> gameController.showMainMenu());
        timer.setRepeats(false);
        timer.start();
    }
}
