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

        BottomPanel bottomPanel = new BottomPanel(gameController, "I think Anton be doin' this screen");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}