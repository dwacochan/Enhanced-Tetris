package view;

import controller.GameController;
import model.AbstractScreen;
import model.GameLoop;

import java.awt.*;

public class GameScreen extends AbstractScreen {
    private GameLoop gameLoop;

    public GameScreen(GameController gameController) {
        super(gameController);

        mainPanel.setLayout(new BorderLayout());

        this.gameLoop = gameController.getGameLoop();

        mainPanel.add(gameLoop, BorderLayout.CENTER);

        BottomPanel bottomPanel = new BottomPanel(gameController, "Anton Koulakov");
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}