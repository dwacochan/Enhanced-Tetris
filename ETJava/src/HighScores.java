import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighScores extends AbstractScreen {

    public HighScores(GameController gameController) {
        super(gameController);

        mainPanel.setLayout(new BorderLayout());

        // Add a label in the center of the screen
        JLabel label = new JLabel("HighScores", JLabel.CENTER);
        mainPanel.add(label, BorderLayout.CENTER);

        // Add a button at the bottom of the screen
        JButton backButton = new JButton("Back to Main Screen");
        backButton.setPreferredSize(new Dimension(100, 80)); // Set a custom size for the button (width, height)

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.showMainMenu();
            }
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);
    }
}