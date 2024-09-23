package view;

import controller.GameController;
import model.AbstractScreen;
import model.GameLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameScreen extends AbstractScreen {
    private GameLoop gameLoop;

    public GameScreen(GameController gameController) {
        super(gameController);

        mainPanel.setLayout(new BorderLayout());

        this.gameLoop = gameController.getGameLoop();

        mainPanel.add(gameLoop, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Anton Koulakov", createBackButtonListener(gameController));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }


    private ActionListener createBackButtonListener(GameController gameController) {
        return e -> {
            boolean wasRunning = gameController.isGameRunning();

            // Check for game over
            if (gameController.checkGameOver()) {
                gameController.stopGame();
                gameController.showMainMenu();
                return;
            }

            // Pause the game
            gameController.pauseGame();
            if (wasRunning) {
                // Show confirmation dialog
                int choice = JOptionPane.showConfirmDialog(mainPanel,
                        "Do you want to stop the game and return to the main menu?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    gameController.stopGame();
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    gameController.showMainMenu();

                } else {
                    gameController.resumeGame();
                }
            } else {
                gameController.showMainMenu();
            }
        };
    }


}