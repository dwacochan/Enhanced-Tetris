import javax.swing.*;
import java.awt.*;

public class Settings extends AbstractScreen {

    public Settings(GameController gameController) {
        super(gameController);

        mainPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Settings", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(label, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "I think Draco be doin' this screen");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}