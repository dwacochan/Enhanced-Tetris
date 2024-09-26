package model.tetrominos;

import model.Block;
import model.Tetromino;

import java.awt.*;

public class Z extends Tetromino {

    public Z(){
        create(Color.RED);
    }

    public void setPosition(int x, int y){
        blocks[0].setX(x);
        blocks[0].setY(y);
        blocks[1].setX(x + Block.SIZE);
        blocks[1].setY(y);
        blocks[2].setX(x);
        blocks[2].setY(y - Block.SIZE);
        blocks[3].setX(x - Block.SIZE);
        blocks[3].setY(y - Block.SIZE);
    }

    public void getRotation1(){

        tempBlocks[0].setX(blocks[0].getX());
        tempBlocks[0].setY(blocks[0].getY());
        tempBlocks[1].setX(blocks[0].getX());
        tempBlocks[1].setY(blocks[0].getY() - Block.SIZE);
        tempBlocks[2].setX(blocks[0].getX() - Block.SIZE);
        tempBlocks[2].setY(blocks[0].getY());
        tempBlocks[3].setX(blocks[0].getX() - Block.SIZE);
        tempBlocks[3].setY(blocks[0].getY() + Block.SIZE);

        updateRotation(1);

    }

    public void getRotation0(){

        tempBlocks[0].setX(blocks[0].getX());
        tempBlocks[0].setY(blocks[0].getY());
        tempBlocks[1].setX(blocks[0].getX() + Block.SIZE);
        tempBlocks[1].setY(blocks[0].getY());
        tempBlocks[2].setX(blocks[0].getX());
        tempBlocks[2].setY(blocks[0].getY() - Block.SIZE);
        tempBlocks[3].setX(blocks[0].getX() - Block.SIZE);
        tempBlocks[3].setY(blocks[0].getY() - Block.SIZE);

        updateRotation(0);

    }

    public void getRotation2(){

        getRotation0();

    }

    public void getRotation3(){

        getRotation1();

    }

}
