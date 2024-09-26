package view;

import controller.GameController;
import util.AudioManager;

import javax.swing.*;
import java.awt.*;

public class ManholeScene extends AbstractScreen {

    public JPanel getPanel() {
        return mainPanel;
    }

    public ManholeScene(GameController gameController) {
        super(gameController);

        // Set layout for the panel
        mainPanel.setLayout(new BorderLayout());

        // Create the BackgroundPanel and add it to the main panel
        BackgroundPanel backgroundPanel = new BackgroundPanel("/resources/ManholeScene.jpg");
        mainPanel.add(backgroundPanel, BorderLayout.CENTER);  // Use BackgroundPanel instead of JLabel

        // Create a back button to return to the main menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Courier New", Font.BOLD, 18));
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.setOpaque(true); // Make the button opaque
        backButton.setContentAreaFilled(true); // Ensure the content area is filled
        backButton.setBackground(Color.BLACK); // Set background to black
        backButton.setForeground(Color.CYAN); // Set text color to cyan
        backButton.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2)); // Cyan neon border

        // Add action listener to back button to return to the main menu
        backButton.addActionListener(e -> {
            // Stop the manhole scene music
            AudioManager.getInstance().pauseMusic();
            // Return to the main menu (which will handle playing the main menu music)
//            gameController.showMainMenu();
        });

        // Add the back button to the bottom of the panel
        mainPanel.add(backButton, BorderLayout.SOUTH);

        // Set the panel visible
        setVisible(true);
    }
}
