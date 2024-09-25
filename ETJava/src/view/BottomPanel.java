package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BottomPanel extends JPanel {

    private JButton backButton;
    private ActionListener backButtonListener;

    // Constructor with an optional ActionListener
    public BottomPanel(GameController gameController, String authorName, ActionListener backButtonListener) {
        this.backButtonListener = backButtonListener;
        this.setOpaque(false);
        setLayout(new BorderLayout());

        backButton = new JButton("Back");
        backButton.setFont(new Font("Courier New", Font.BOLD, 18));
        backButton.setOpaque(true);
        //backButton.setContentAreaFilled(true);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.CYAN);
        backButton.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        backButton.setPreferredSize(new Dimension(150, 50));
        // Default behavior
        backButton.addActionListener(Objects.requireNonNullElseGet(backButtonListener, () -> e -> gameController.showMainMenu())); // Additional custom behavior
        add(backButton, BorderLayout.SOUTH);

        OutlinedLabel authorLabel = new OutlinedLabel("Author: " + authorName, JLabel.CENTER,Color.WHITE);
        authorLabel.setFont((new Font("Courier New", Font.BOLD, 18)));
        add(authorLabel, BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // Overloaded constructor without ActionListener
    public BottomPanel(GameController gameController, String authorName) {
        this(gameController, authorName, null); // No additional listener
    }


}
