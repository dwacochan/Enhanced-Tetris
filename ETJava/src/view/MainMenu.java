package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.GameController;
import util.AudioManager;

public class MainMenu extends AbstractScreen implements ActionListener {
    JButton playButton, configButton, highScoresButton, exitButton;
    private final String musicFilePath = "/resources/mainmenu.wav";

    public MainMenu(GameController gameController) {
        super(gameController);

        // Use the singleton AudioManager to play or resume the menu music
        AudioManager.getInstance().playMusic(musicFilePath);

        // Load your background image with a 16:9 aspect ratio
        BackgroundPanel backgroundPanel = new BackgroundPanel("/resources/MainMenuBackground.jpg");
        backgroundPanel.setLayout(new GridBagLayout()); // Use GridBagLayout to center the buttons

        // Create a GridBagConstraints object to control the layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around each component
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE; // Stack components vertically
        gbc.fill = GridBagConstraints.NONE; // No stretching
        gbc.anchor = GridBagConstraints.CENTER; // Center the components

        // Title with white outline
        OutlinedLabel titleLabel = new OutlinedLabel("Main Menu", JLabel.CENTER, Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK); // Set the text color to black

        gbc.insets = new Insets(20, 10, 20, 10); // Larger padding for the title
        backgroundPanel.add(titleLabel, gbc);

        // Play Button
        playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.PLAIN, 18));
        playButton.setPreferredSize(new Dimension(200, 50)); // Set a fixed size for the buttons
        playButton.addActionListener(this);
        gbc.insets = new Insets(10, 10, 10, 10); // Regular padding for buttons
        backgroundPanel.add(playButton, gbc);

        // Settings Button
        configButton = new JButton("Settings");
        configButton.setFont(new Font("Arial", Font.PLAIN, 18));
        configButton.setPreferredSize(new Dimension(200, 50));
        configButton.addActionListener(this);
        backgroundPanel.add(configButton, gbc);

        // High Scores Button
        highScoresButton = new JButton("High Scores");
        highScoresButton.setFont(new Font("Arial", Font.PLAIN, 18));
        highScoresButton.setPreferredSize(new Dimension(200, 50));
        highScoresButton.addActionListener(this);
        backgroundPanel.add(highScoresButton, gbc);

        // Exit Button
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        exitButton.setPreferredSize(new Dimension(200, 50));
        exitButton.addActionListener(this);
        backgroundPanel.add(exitButton, gbc);

        // Author label with white outline
        OutlinedLabel authorLabel = new OutlinedLabel("Author: Daniel De Calmer", JLabel.CENTER, Color.WHITE);
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        authorLabel.setForeground(Color.BLACK); // Set the text color to black

        // Add empty border to ensure the text is not cut off
        authorLabel.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12)); // 5px top/bottom padding, 10px left/right padding

        gbc.insets = new Insets(20, 10, 10, 10); // Larger padding for the label
        backgroundPanel.add(authorLabel, gbc);

        // Add the background panel to the main panel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(backgroundPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Handle button actions
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            System.out.println("Play button clicked");
            AudioManager.getInstance().pauseMusic(); // Pause the music when switching to game screen
            gameController.showGameScreen();
        } else if (e.getSource() == configButton) {
            System.out.println("Settings button clicked");
            AudioManager.getInstance().pauseMusic(); // Pause the music when switching to settings
            gameController.showSettings();
        } else if (e.getSource() == highScoresButton) {
            System.out.println("High Scores button clicked");
            AudioManager.getInstance().pauseMusic(); // Pause the music when switching to high scores
            gameController.showHighScores();
        } else if (e.getSource() == exitButton) {
            int response = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                AudioManager.getInstance().pauseMusic(); // Pause the music when exiting
                System.exit(0);
            }
        }
    }
}
