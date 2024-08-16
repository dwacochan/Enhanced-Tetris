package tetrominos;

import game.Block;
import game.Tetromino;

import java.awt.*;

public class L extends Tetromino {

    public L(){
        create(Color.ORANGE);
    }

    public void setPosition(int x, int y){
        blocks[0].x = x;
        blocks[0].y = y;
        blocks[1].x = x;
        blocks[1].y = y - Block.SIZE;
        blocks[2].x = x;
        blocks[2].y = y + Block.SIZE;
        blocks[3].x = x + Block.SIZE;
        blocks[3].y = y + Block.SIZE;
    }
}