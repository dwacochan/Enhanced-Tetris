package game;

import tetrominos.*;

import java.awt.*;
import java.util.ArrayList;

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

    public Gameplay(){
        left_x = (GameArea.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        TETROMINOSTART_X = left_x + (WIDTH/2) - Block.SIZE;
        TETROMINOSTART_Y = top_y + Block.SIZE;

        currentTetromino = new L();
        currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
    }

    public void update(){

        if (currentTetromino.settled){

            settledTetrominos.add(currentTetromino.blocks[0]);
            settledTetrominos.add(currentTetromino.blocks[1]);
            settledTetrominos.add(currentTetromino.blocks[2]);
            settledTetrominos.add(currentTetromino.blocks[3]);


            currentTetromino = new L();
            currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);

        } else {

            currentTetromino.update();

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
}
