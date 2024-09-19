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
        this.backButtonListener = backButtonListener; // Store the optional listener

        setLayout(new BorderLayout());

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        // Default behavior
        backButton.addActionListener(Objects.requireNonNullElseGet(backButtonListener, () -> e -> gameController.showMainMenu())); // Additional custom behavior
        add(backButton, BorderLayout.SOUTH);

        JLabel authorLabel = new JLabel("Author: " + authorName, JLabel.CENTER);
        add(authorLabel, BorderLayout.NORTH);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // Overloaded constructor without ActionListener
    public BottomPanel(GameController gameController, String authorName) {
        this(gameController, authorName, null); // No additional listener
    }


}
