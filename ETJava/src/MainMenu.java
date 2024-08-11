import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends AbstractScreen implements ActionListener {
    JButton playButton, configButton, highScoresButton, exitButton;

    public MainMenu(GameController gameController) {
        super(gameController);

        mainPanel.setLayout(new GridLayout(1, 3));

        mainPanel.add(new JPanel());

        JPanel centerPanel = new JPanel(new GridLayout(6, 12, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Main Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        centerPanel.add(titleLabel);

        // Play Button
        playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.PLAIN, 18));
        playButton.addActionListener(this);
        centerPanel.add(playButton);

        // Settings Button
        configButton = new JButton("Settings");
        configButton.setFont(new Font("Arial", Font.PLAIN, 18));
        configButton.addActionListener(this);
        centerPanel.add(configButton);

        // High Scores Button
        highScoresButton = new JButton("High Scores");
        highScoresButton.setFont(new Font("Arial", Font.PLAIN, 18));
        highScoresButton.addActionListener(this);
        centerPanel.add(highScoresButton);

        // Exit Button
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        exitButton.addActionListener(this);
        centerPanel.add(exitButton);

        JLabel authorLabel = new JLabel("Author: Daniel De Calmer", JLabel.CENTER);
        centerPanel.add(authorLabel);

        mainPanel.add(centerPanel);

        mainPanel.add(new JPanel());

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            System.out.println("Play button clicked");
            gameController.showGameLoop();
        } else if (e.getSource() == configButton) {
            System.out.println("Settings button clicked");
            gameController.showSettings();
        } else if (e.getSource() == highScoresButton) {
            System.out.println("High Scores button clicked");
            gameController.showHighScores();
        } else if (e.getSource() == exitButton) {
            System.out.println("Exit button clicked");
            System.exit(0);
        }
    }
}