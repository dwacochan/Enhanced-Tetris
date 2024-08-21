import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {

    public BottomPanel(GameController gameController, String authorName) {
        setLayout(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(e -> handleBackButtonClick(gameController));
        add(backButton, BorderLayout.SOUTH);

        JLabel authorLabel = new JLabel("Author: " + authorName, JLabel.CENTER);
        add(authorLabel, BorderLayout.NORTH);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void handleBackButtonClick(GameController gameController) {
        boolean wasRunning = gameController.isGameRunning();

        // Pause the game
        gameController.pauseGame();

        // Show confirmation dialog
        int choice = JOptionPane.showConfirmDialog(this,
                "Do you want to stop the game and return to the main menu?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            gameController.stopGame();
            gameController.showMainMenu();
        } else {
            // Resume the game only if it was running before the pause
            if (wasRunning) {
                gameController.resumeGame();
            }
        }
    }
}
