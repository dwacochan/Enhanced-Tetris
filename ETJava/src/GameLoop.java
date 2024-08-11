import javax.swing.*;
import java.awt.*;

public class GameLoop extends AbstractScreen {

    public GameLoop(GameController gameController) {
        super(gameController);

        mainPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("GameLoop", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(label, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "I think Anton be doin' this screen");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}