package model.tetrominos;

import model.Block;
import model.Tetromino;

import java.awt.*;

public class T extends Tetromino {

    public T(){
        create(Color.MAGENTA);
    }

    public void setPosition(int x, int y){
        blocks[0].setX(x);
        blocks[0].setY(y);
        blocks[1].setX(x);
        blocks[1].setY(y - Block.SIZE);
        blocks[2].setX(x - Block.SIZE);
        blocks[2].setY(y);
        blocks[3].setX(x + Block.SIZE);
        blocks[3].setY(y);
    }

    public void getRotation0(){

        tempBlocks[0].setX(blocks[0].getX());
        tempBlocks[0].setY(blocks[0].getY());
        tempBlocks[1].setX(blocks[0].getX());
        tempBlocks[1].setY(blocks[0].getY() - Block.SIZE);
        tempBlocks[2].setX(blocks[0].getX() - Block.SIZE);
        tempBlocks[2].setY(blocks[0].getY());
        tempBlocks[3].setX(blocks[0].getX() + Block.SIZE);
        tempBlocks[3].setY(blocks[0].getY());

        updateRotation(0);

    }

    public void getRotation1(){

        tempBlocks[0].setX(blocks[0].getX());
        tempBlocks[0].setY(blocks[0].getY());
        tempBlocks[1].setX(blocks[0].getX() + Block.SIZE);
        tempBlocks[1].setY(blocks[0].getY());
        tempBlocks[2].setX(blocks[0].getX());
        tempBlocks[2].setY(blocks[0].getY() - Block.SIZE);
        tempBlocks[3].setX(blocks[0].getX());
        tempBlocks[3].setY(blocks[0].getY() + Block.SIZE);

        updateRotation(1);

    }

    public void getRotation2(){

        tempBlocks[0].setX(blocks[0].getX());
        tempBlocks[0].setY(blocks[0].getY());
        tempBlocks[1].setX(blocks[0].getX());
        tempBlocks[1].setY(blocks[0].getY() + Block.SIZE);
        tempBlocks[2].setX(blocks[0].getX() + Block.SIZE);
        tempBlocks[2].setY(blocks[0].getY());
        tempBlocks[3].setX(blocks[0].getX() - Block.SIZE);
        tempBlocks[3].setY(blocks[0].getY());

        updateRotation(2);

    }

    public void getRotation3(){

        tempBlocks[0].setX(blocks[0].getX());
        tempBlocks[0].setY(blocks[0].getY());
        tempBlocks[1].setX(blocks[0].getX() - Block.SIZE);
        tempBlocks[1].setY(blocks[0].getY());
        tempBlocks[2].setX(blocks[0].getX());
        tempBlocks[2].setY(blocks[0].getY() + Block.SIZE);
        tempBlocks[3].setX(blocks[0].getX());
        tempBlocks[3].setY(blocks[0].getY() - Block.SIZE);

        updateRotation(3);

    }

}
