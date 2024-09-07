package game;

import tetrominos.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Gameplay {
    final int WIDTH = 200;
    final int HEIGHT = 400;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;
    Tetromino currentTetromino;
    final int TETROMINOSTART_X;
    final int TETROMINOSTART_Y;
    public static ArrayList<Block> settledTetrominos = new ArrayList<>();
    public static boolean gameOver = false;


    public Gameplay(){
        left_x = (GameArea.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        TETROMINOSTART_X = left_x + (WIDTH/2) - Block.SIZE;
        TETROMINOSTART_Y = top_y + Block.SIZE;

        currentTetromino = selectShape();
        currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
    }

    private Tetromino selectShape(){

        Tetromino shape = null;
        int n = new Random().nextInt(7);

        shape = switch (n) {
            case 0 -> new I();
            case 1 -> new O();
            case 2 -> new T();
            case 3 -> new S();
            case 4 -> new Z();
            case 5 -> new J();
            case 6 -> new L();
            default -> shape;
        };

        return shape;

    }

    public void update() {
        if (gameOver){
            return;
        }
        if (currentTetromino.settled) {
            settledTetrominos.addAll(Arrays.asList(currentTetromino.blocks));

            currentTetromino.settling = false;
            currentTetromino = selectShape();
            currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
            checkRowErasure();
        } else {
            currentTetromino.update();
        }
    }

    private void checkRowErasure(){
        ArrayList<Integer> fullRows = new ArrayList<>();
        checkFullRow(fullRows);
        removeFullRow(fullRows);
        shiftDownRemainingRows(fullRows);
    }
    private void checkFullRow(ArrayList<Integer> fullRows) {
        for (int y = top_y; y < bottom_y; y += Block.SIZE) {
            int blockNum = 0;
            for (Block settledBlock : settledTetrominos) {
                if (settledBlock.y == y) {
                    blockNum++;
                }
            }
            if (blockNum == (right_x - left_x) / Block.SIZE) {
                fullRows.add(y);
            }
        }

    }
    private void removeFullRow(ArrayList<Integer> fullRows){
        for (int row : fullRows) {
            settledTetrominos.removeIf(block -> block.y == row);
        }
    }

    private void shiftDownRemainingRows(ArrayList<Integer> fullRows){
        for (int row : fullRows) {
            for (Block settledBlock : settledTetrominos) {
                if (settledBlock.y < row) {
                    settledBlock.y += Block.SIZE;
                }
            }
        }
    }


    public void draw(Graphics2D g2d){
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(left_x-2, top_y-2, WIDTH+4, HEIGHT+4);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(left_x-2, top_y-2, WIDTH+4, HEIGHT+4);

        if(currentTetromino != null){
            currentTetromino.draw(g2d);
        }

        for (Block block : settledTetrominos) {
            block.draw(g2d);
        }

        g2d.setColor(Color.BLACK);
        g2d.setFont(g2d.getFont().deriveFont(30f));
        if(Controls.pause){
            int x = left_x - 215;
            int y = top_y + 200;
            g2d.drawString("PAUSED", x, y);
            g2d.drawString("Press P to unpause", x - 65, y + 50);
        }

    }


    public void reset() {
        settledTetrominos.clear();
        Controls.pause = false;
        gameOver = false;
        currentTetromino = selectShape();
        currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
    }

}
