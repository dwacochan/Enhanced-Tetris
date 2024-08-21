import game.Controls;
import game.GameArea;

import java.awt.*;

public class GameLoop extends AbstractScreen {
    GameArea gameArea;

    public GameLoop(GameController gameController) {
        super(gameController);

        mainPanel.setLayout(new BorderLayout());

        this.gameArea = new GameArea();
        Controls.bindKeys(this.gameArea);

        mainPanel.add(gameArea, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Anton Koulakov");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}