package view;

import controller.GameController;

import javax.swing.*;

public abstract class AbstractScreen extends JPanel {
    protected BackgroundPanel mainPanel;
    protected GameController gameController;

    public AbstractScreen(GameController gameController) {
        mainPanel = new BackgroundPanel("/resources/MainMenuBackground.jpg");
        this.gameController = gameController;
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}