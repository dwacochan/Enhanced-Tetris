package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BottomPanel extends JPanel {

    private JButton backButton;
    private ActionListener backButtonListener;

    // Constructor with an optional ActionListener
    public BottomPanel(GameController gameController, String authorName, ActionListener backButtonListener) {
        this.backButtonListener = backButtonListener; // Store the optional listener

        setLayout(new BorderLayout());

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(e -> handleBackButtonClick(gameController)); // Default behavior
        if (backButtonListener != null) {
            backButton.addActionListener(backButtonListener); // Additional custom behavior
        }
        add(backButton, BorderLayout.SOUTH);

        JLabel authorLabel = new JLabel("Author: " + authorName, JLabel.CENTER);
        add(authorLabel, BorderLayout.NORTH);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // Overloaded constructor without ActionListener
    public BottomPanel(GameController gameController, String authorName) {
        this(gameController, authorName, null); // No additional listener
    }

    private void handleBackButtonClick(GameController gameController) {
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
            int choice = JOptionPane.showConfirmDialog(this,
                    "Do you want to stop the game and return to the main menu?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                gameController.stopGame();
                gameController.showMainMenu();
            } else {
                gameController.resumeGame();
            }
        } else {
            gameController.showMainMenu();
        }
    }
}
