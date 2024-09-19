package model;

import javax.swing.*;
import controller.GameController;

public abstract class AbstractScreen extends JPanel {
    protected JPanel mainPanel;
    protected GameController gameController;

    public AbstractScreen(GameController gameController) {
        mainPanel = new JPanel();
        this.gameController = gameController;
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}