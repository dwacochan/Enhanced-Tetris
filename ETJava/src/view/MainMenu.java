package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.GameController;
import util.AudioManager;

public class MainMenu extends AbstractScreen implements ActionListener {
    JButton playButton, configButton, highScoresButton, exitButton;
    private final String musicFilePath = "/resources/mainmenu.wav";

    public MainMenu(GameController gameController) {
        super(gameController);

        // Use the singleton AudioManager to play or resume the menu music
        AudioManager.getInstance().playMusic(musicFilePath);

        BackgroundPanel backgroundPanel = new BackgroundPanel("/resources/MainMenuBackground.jpg");
        backgroundPanel.setLayout(new GridBagLayout()); // Use GridBagLayout to center the buttons

        // Create a GridBagConstraints object to control the layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around each component
        gbc.gridx = GridBagConstraints.CENTER;
        gbc.gridy = GridBagConstraints.RELATIVE; // Stack components vertically
        gbc.fill = GridBagConstraints.NONE; // No stretching
        gbc.anchor = GridBagConstraints.CENTER; // Center the components

        // Title with white outline
        OutlinedLabel titleLabel = new OutlinedLabel("      Main Menu", JLabel.CENTER, Color.BLACK);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        titleLabel.setPreferredSize(new Dimension(340, 50));
        gbc.insets = new Insets(20, 15, 20, 15); // Larger padding for the title
        backgroundPanel.add(titleLabel, gbc);

        // Play Button with cyan neon
        playButton = new JButton("Play");
        playButton.setFont(new Font("Courier New", Font.BOLD, 18));
        playButton.setPreferredSize(new Dimension(200, 50)); // Set a fixed size for the buttons
        playButton.setOpaque(true); // Make the button opaque
        playButton.setContentAreaFilled(true); // Ensure the content area is filled
        playButton.setBackground(Color.BLACK); // Set background to black
        playButton.setForeground(Color.CYAN); // Cyan neon text
        playButton.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2)); // Cyan neon border
        playButton.addActionListener(this);
        gbc.insets = new Insets(10, 10, 10, 10); // Regular padding for buttons
        backgroundPanel.add(playButton, gbc);

        // Settings Button with orange neon
        configButton = new JButton("Settings");
        configButton.setFont(new Font("Courier New", Font.BOLD, 18));
        configButton.setPreferredSize(new Dimension(200, 50));
        configButton.setOpaque(true); // Make the button opaque
        configButton.setContentAreaFilled(true); // Ensure the content area is filled
        configButton.setBackground(Color.BLACK); // Set background to black
        configButton.setForeground(new Color(255, 165, 0)); // Orange neon text
        configButton.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 2)); // Orange neon border
        configButton.addActionListener(this);
        backgroundPanel.add(configButton, gbc);

        // High Scores Button with green neon
        highScoresButton = new JButton("High Scores");
        highScoresButton.setFont(new Font("Courier New", Font.BOLD, 18));
        highScoresButton.setPreferredSize(new Dimension(200, 50));
        highScoresButton.setOpaque(true); // Make the button opaque
        highScoresButton.setContentAreaFilled(true); // Ensure the content area is filled
        highScoresButton.setBackground(Color.BLACK); // Set background to black
        highScoresButton.setForeground(new Color(0, 255, 0)); // Green neon text
        highScoresButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 0), 2)); // Green neon border
        highScoresButton.addActionListener(this);
        backgroundPanel.add(highScoresButton, gbc);

        // Exit Button with red neon
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Courier New", Font.BOLD, 18));
        exitButton.setPreferredSize(new Dimension(200, 50));
        exitButton.setOpaque(true); // Make the button opaque
        exitButton.setContentAreaFilled(true); // Ensure the content area is filled
        exitButton.setBackground(Color.BLACK); // Set background to black
        exitButton.setForeground(new Color(255, 0, 0)); // Red neon text
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 2)); // Red neon border
        exitButton.addActionListener(this);
        backgroundPanel.add(exitButton, gbc);

        // Load the manhole cover image and scale it down to 2% of its original size
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resources/manhole.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(
                (int) (originalIcon.getIconWidth() * 0.02),
                (int) (originalIcon.getIconHeight() * 0.02),
                Image.SCALE_SMOOTH); // Use smooth scaling
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create the JLabel with the scaled image
        JLabel manholeCover = new JLabel(scaledIcon);

        // Add a MouseListener to the manhole cover to handle the click event
        manholeCover.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.showManholeScene();  // Show the manhole scene
            }
        });

        // Configure gbc to place the manhole cover in the bottom-left
        gbc.gridy = GridBagConstraints.SOUTHWEST; // Stack it below other components
        gbc.anchor = GridBagConstraints.LAST_LINE_START; // Bottom-left corner
        gbc.insets = new Insets(85, 10, 10, 10); // Add space around the image
        backgroundPanel.add(manholeCover, gbc);

        // Add the background panel to the main panel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(backgroundPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Handle button actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            System.out.println("Play button clicked");
            gameController.showGameScreen();
        } else if (e.getSource() == configButton) {
            System.out.println("Settings button clicked");
            AudioManager.getInstance().pauseMusic();
            AudioManager.getInstance().playMusic("/resources/CafeAmbience.wav"); // Play Cafe Ambience music
            gameController.showSettings();
        } else if (e.getSource() == highScoresButton) {
            System.out.println("High Scores button clicked");
            AudioManager.getInstance().pauseMusic();
            AudioManager.getInstance().playMusic("/resources/CafeAmbience.wav"); // Play Cafe Ambience music
            gameController.showHighScores();
        } else if (e.getSource() == exitButton) {
            int response = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}
