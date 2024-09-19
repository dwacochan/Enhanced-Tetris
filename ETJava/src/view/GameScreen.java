package view;

import controller.GameController;
import controller.Controls;
import model.AbstractScreen;

import java.awt.*;

public class GameScreen extends AbstractScreen {
    public GameArea gameArea;

    public GameScreen(GameController gameController) {
        super(gameController);

        mainPanel.setLayout(new BorderLayout());

        this.gameArea = new GameArea();
        Controls.bindKeys(this.gameArea);

        mainPanel.add(gameArea, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Anton Koulakov");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}