package controller.facade;

import controller.Gameplay;

import java.awt.*;

public class GameFacade {
    private Gameplay gameplay;

    public GameFacade(int width, int height) {
        gameplay = new Gameplay(width, height);
    }

    public void startNewGame() {
        gameplay.reset();
    }

    public void updateGame() {
        gameplay.update();
    }

    public void renderGame(Graphics2D g2d) {
        gameplay.draw(g2d);
    }

    public void setGameDimensions(int width, int height) {
        gameplay.setWidth(width);
        gameplay.setHeight(height);
        gameplay.resetDimensions();
    }

    public boolean isGameOver() {
        return gameplay.isGameOver();
    }

}
