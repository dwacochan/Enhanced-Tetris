package controller;

import model.Block;
import model.Tetromino;
import model.tetrominos.*;
import model.GameLoop;
import model.factory.TetrominoFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Gameplay {


    private int width;
    private int height;
    public int left_x;
    public int right_x;
    public int top_y;
    public int bottom_y;
    private Tetromino currentTetromino;
    private int TETROMINOSTART_X;
    private int TETROMINOSTART_Y;
    private ArrayList<Block> settledTetrominos = new ArrayList<>();
    private boolean gameOver = false;

    public Gameplay(int width, int height) {
        this.width = width;
        this.height = height;
        initializeDimensions();
        currentTetromino = selectShape();
        currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
        currentTetromino.setGameplay(this);  // Pass instance of Gameplay to Tetromino
    }

    private void initializeDimensions() {
        left_x = (GameLoop.WIDTH / 2) - (width / 2);
        right_x = left_x + width;
        top_y = 50;
        bottom_y = top_y + height;
        TETROMINOSTART_X = left_x + (width / 2) - Block.SIZE;
        TETROMINOSTART_Y = top_y + Block.SIZE;
    }

    public void resetDimensions() {
        initializeDimensions();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private Tetromino selectShape() {
        return TetrominoFactory.createTetromino();
    }

    public void update() {
        if (isGameOver()) {
            return;
        }
        if (currentTetromino.isSettled()) {
            settledTetrominos.addAll(Arrays.asList(currentTetromino.getBlocks()));

            currentTetromino.setSettling(false);
            currentTetromino = selectShape();
            currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
            currentTetromino.setGameplay(this);  // Set reference of Gameplay

            checkRowErasure();
        } else {
            currentTetromino.update();
        }
    }

    private void checkRowErasure() {
        ArrayList<Integer> fullRows = new ArrayList<>();
        checkFullRow(fullRows);
        removeFullRow(fullRows);
        shiftDownRemainingRows(fullRows);
    }

    private void checkFullRow(ArrayList<Integer> fullRows) {
        for (int y = top_y; y < bottom_y; y += Block.SIZE) {
            int blockNum = 0;
            for (Block settledBlock : settledTetrominos) {
                if (settledBlock.getY() == y) {
                    blockNum++;
                }
            }
            if (blockNum == (right_x - left_x) / Block.SIZE) {
                fullRows.add(y);
            }
        }
    }

    private void removeFullRow(ArrayList<Integer> fullRows) {
        for (int row : fullRows) {
            settledTetrominos.removeIf(block -> block.getY() == row);
        }
    }

    private void shiftDownRemainingRows(ArrayList<Integer> fullRows) {
        for (int row : fullRows) {
            for (Block settledBlock : settledTetrominos) {
                if (settledBlock.getY() < row) {
                    settledBlock.setY(settledBlock.getY() + Block.SIZE);
                }
            }
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(left_x - 2, top_y - 2, width + 4, height + 4);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(left_x - 2, top_y - 2, width + 4, height + 4);

        if (currentTetromino != null) {
            currentTetromino.draw(g2d);
        }

        for (Block block : settledTetrominos) {
            block.draw(g2d);
        }

        g2d.setColor(Color.BLACK);
        g2d.setFont(g2d.getFont().deriveFont(30f));
        if (Controls.pause) {
            int x = left_x - 215;
            int y = top_y + 200;
            g2d.drawString("PAUSED", x, y);
            g2d.drawString("Press P to unpause", x - 65, y + 50);
        }
    }

    public void reset() {
        settledTetrominos.clear();
        Controls.pause = false;
        setGameOver(false);
        currentTetromino = selectShape();
        currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
        currentTetromino.setGameplay(this);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Block> getSettledTetrominos() {
        return settledTetrominos;
    }
}
