package controller;

import model.Block;
import model.Tetromino;
import model.GameLoop;
import model.factory.TetrominoFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Gameplay {


    private int width;
    private int height;
    private int left_x;
    private int right_x;
    private int top_y;
    private int bottom_y;
    private Tetromino currentTetromino;
    private int TETROMINOSTART_X;
    private int TETROMINOSTART_Y;
    private ArrayList<Block> settledTetrominos = new ArrayList<>();
    private boolean gameOver = false;



    private int gameNumber;

    // Scoring and level tracking
    private int score = 0;
    private int rowsErased = 0;
    private int level = 1;

    public Gameplay(int width, int height, int gameNumber) {
        this.width = width;
        this.height = height;
        this.gameNumber = gameNumber;
        initializeDimensions();
        currentTetromino = selectShape();
        currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
        currentTetromino.setGameplay(this);  // Pass instance of Gameplay to Tetromino
    }

    public Gameplay(int width, int height) {
        this(width,height,1);
    }

    private void initializeDimensions() {
        setLeft_x((GameLoop.WIDTH / 2) - (width / 2));
        setRight_x(getLeft_x() + width);
        setTop_y(50);
        setBottom_y(getTop_y() + height);
        TETROMINOSTART_X = getLeft_x() + (width / 2) - Block.SIZE;
        TETROMINOSTART_Y = getTop_y() + Block.SIZE;
    }

    public void resetDimensions() {
        initializeDimensions();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private Tetromino selectShape() {
        return TetrominoFactory.getNextTetromino(this);
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
        if (!fullRows.isEmpty()){
            updateScore(fullRows.size());
            rowsErased += fullRows.size();
            checkLevelUp();
        }
        removeFullRow(fullRows);
        shiftDownRemainingRows(fullRows);
    }

    private void checkFullRow(ArrayList<Integer> fullRows) {
        for (int y = getTop_y(); y < getBottom_y(); y += Block.SIZE) {
            int blockNum = 0;
            for (Block settledBlock : settledTetrominos) {
                if (settledBlock.getY() == y) {
                    blockNum++;
                }
            }
            if (blockNum == (getRight_x() - getLeft_x()) / Block.SIZE) {
                fullRows.add(y);
            }
        }
    }



    private void updateScore(int rowsErased) {
        switch (rowsErased) {
            case 1 -> score += 100;
            case 2 -> score += 300;
            case 3 -> score += 600;
            default -> score += 1000; // 4 and over
        }
    }

    private void checkLevelUp() {
        if (rowsErased >= level * 10) {
            level++;
            System.out.println("Level up! Now at level: " + level);
            // TODO: Increase Tetromino falling speed
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

        // Draw the score and level
        g2d.setColor(Color.BLACK);
        g2d.setFont(g2d.getFont().deriveFont(30f));
        g2d.drawString("Score: " + score, left_x - 200, top_y + 100);
        g2d.drawString("Level: " + level, left_x - 200, top_y + 150);

        // Existing paused drawing logic
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

        // Reset score and level
        score = 0;
        rowsErased = 0;
        level = 1;
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

    public int getLeft_x() {
        return left_x;
    }

    public void setLeft_x(int left_x) {
        this.left_x = left_x;
    }

    public int getRight_x() {
        return right_x;
    }

    public void setRight_x(int right_x) {
        this.right_x = right_x;
    }

    public int getTop_y() {
        return top_y;
    }

    public void setTop_y(int top_y) {
        this.top_y = top_y;
    }

    public int getBottom_y() {
        return bottom_y;
    }

    public void setBottom_y(int bottom_y) {
        this.bottom_y = bottom_y;
    }
}
