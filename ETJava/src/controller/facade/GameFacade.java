package controller.facade;

import controller.Gameplay;
import model.PlayerType;

import java.awt.*;

public class GameFacade {
    private Gameplay gameplay;


    private int gameNumber;
    public GameFacade(int width, int height) {
        gameplay = new Gameplay(width, height);
    }

    public GameFacade(int width, int height, int gameNumber) {
        this.gameNumber = gameNumber;
        gameplay = new Gameplay(width, height,gameNumber);
    }

    public GameFacade(int width, int height, int gameNumber, PlayerType playerType) {
        gameplay = new Gameplay(width, height, gameNumber, playerType);
    }

    public Gameplay getGameplay() {
        return gameplay;
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

    public int getScore() {return gameplay.getScore();}

    public int getGameNumber() {
        return gameplay.getGameNumber();
    }


}
