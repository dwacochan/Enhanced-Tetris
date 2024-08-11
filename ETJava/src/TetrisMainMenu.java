import java.awt.*;
import java.awt.event.*;

public class TetrisMainMenu extends Frame implements ActionListener {

    Button playButton, configButton, highScoresButton, exitButton;

    public TetrisMainMenu() {
        setLayout(new GridLayout(1, 3));

        add(new Panel());


        Panel centerPanel = new Panel(new GridLayout(6, 12, 10, 10));
        // Title
        Label titleLabel = new Label("Main Menu", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        centerPanel.add(titleLabel);

        // Play Button
        playButton = new Button("Play");
        playButton.setFont(new Font("Arial", Font.PLAIN, 18));
        playButton.addActionListener(this);
        centerPanel.add(playButton);

        // Settings Button
        configButton = new Button("Settings");
        configButton.setFont(new Font("Arial", Font.PLAIN, 18));
        configButton.addActionListener(this);
        centerPanel.add(configButton);

        // High Scores Button
        highScoresButton = new Button("High Scores");
        highScoresButton.setFont(new Font("Arial", Font.PLAIN, 18));
        highScoresButton.addActionListener(this);
        centerPanel.add(highScoresButton);

        // Exit Button
        exitButton = new Button("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        exitButton.addActionListener(this);
        centerPanel.add(exitButton);

        // Author label
        Label authorLabel = new Label("Author: Daniel De Calmer", Label.CENTER);
        centerPanel.add(authorLabel);


        add(centerPanel);

        add(new Panel());

        setTitle("Tetris");
        setSize(400, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            System.out.println("Play button clicked");
            // Add your play logic here
        } else if (e.getSource() == configButton) {
            System.out.println("Settings button clicked");
            // Add your settings logic here
        } else if (e.getSource() == highScoresButton) {
            System.out.println("High Scores button clicked");
            // Add your high scores logic here
        } else if (e.getSource() == exitButton) {
            System.out.println("Exit button clicked");
            // Add your exit logic here
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new TetrisMainMenu();
    }
}
