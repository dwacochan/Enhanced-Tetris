package game;

import java.awt.*;

public class Tetromino {
    public Block blocks[] = new Block[4];
    public Block tempBlocks[] = new Block[4];
    private boolean falling = false;
    public int rotation = 0;
    public boolean leftCollide;
    public boolean rightCollide;
    public boolean bottomCollide;
    public boolean settled = false;

    public void create(Color color){
        blocks[0] = new Block(color);
        blocks[1] = new Block(color);
        blocks[2] = new Block(color);
        blocks[3] = new Block(color);
        tempBlocks[0] = new Block(color);
        tempBlocks[1] = new Block(color);
        tempBlocks[2] = new Block(color);
        tempBlocks[3] = new Block(color);
    }

    public void setPosition(int x, int y){

    }

    public void updateRotation(int rotation){

        checkRotationCollision();

        if (!leftCollide && !rightCollide && !bottomCollide) {

            this.rotation = rotation;

            blocks[0].x = tempBlocks[0].x;
            blocks[0].y = tempBlocks[0].y;
            blocks[1].x = tempBlocks[1].x;
            blocks[1].y = tempBlocks[1].y;
            blocks[2].x = tempBlocks[2].x;
            blocks[2].y = tempBlocks[2].y;
            blocks[3].x = tempBlocks[3].x;
            blocks[3].y = tempBlocks[3].y;

        }
    }

    public void getRotation0(){

    }

    public void getRotation1(){

    }

    public void getRotation2(){

    }

    public void getRotation3(){

    }

    public void checkMovementCollision(){

        leftCollide = false;
        rightCollide = false;
        bottomCollide = false;

        checkSettledTetrominoCollision();

        for (Block block : blocks) {
            if (block.x == Gameplay.left_x) {
                leftCollide = true;
            }
        }

        for (Block block : blocks) {
            if (block.x + Block.SIZE == Gameplay.right_x) {
                rightCollide = true;
            }
        }

        for (Block block : blocks) {
            if (block.y + Block.SIZE >= Gameplay.bottom_y) {
                bottomCollide = true;
            }
        }

    }

    public void checkRotationCollision(){

        leftCollide = false;
        rightCollide = false;
        bottomCollide = false;

        checkSettledTetrominoCollision();

        for (Block block : tempBlocks) {
            if (block.x < Gameplay.left_x) {
                leftCollide = true;
            }
        }

        for (Block block : tempBlocks) {
            if (block.x + Block.SIZE > Gameplay.right_x) {
                rightCollide = true;
            }
        }

        for (Block block : tempBlocks) {
            if (block.y + Block.SIZE >= Gameplay.bottom_y) {
                bottomCollide = true;
            }
        }

    }

    private void checkSettledTetrominoCollision(){

        for (Block settledBlock : Gameplay.settledTetrominos){

            int settledX = settledBlock.x;
            int settledY = settledBlock.y;

            for (Block block : blocks){
                if (block.x == settledX && block.y + Block.SIZE >= settledY){
                    bottomCollide = true;
                }
            }

            for (Block block : blocks){
                if (block.x - Block.SIZE == settledX && block.y >= settledY && block.y < settledY + Block.SIZE){
                    leftCollide = true;
                }
            }

            for (Block block : blocks){
                if (block.x + Block.SIZE == settledX && block.y >= settledY && block.y < settledY + Block.SIZE){
                    rightCollide = true;
                }
            }

        }

    }

    public void update(){

        checkMovementCollision();

        if (Controls.left) {
            if (!leftCollide) {
                blocks[0].x -= Block.SIZE;
                blocks[1].x -= Block.SIZE;
                blocks[2].x -= Block.SIZE;
                blocks[3].x -= Block.SIZE;
            }

            Controls.left = false;
        }

        if (Controls.right) {
            if (!rightCollide) {
                blocks[0].x += Block.SIZE;
                blocks[1].x += Block.SIZE;
                blocks[2].x += Block.SIZE;
                blocks[3].x += Block.SIZE;
            }

            Controls.right = false;
        }

        if (Controls.down) {
            if (!bottomCollide) {
                blocks[0].y += Block.SIZE;
                blocks[1].y += Block.SIZE;
                blocks[2].y += Block.SIZE;
                blocks[3].y += Block.SIZE;
            }

            Controls.down = false;
        }

        if (Controls.up) {
            switch (rotation) {
                case 0:
                    getRotation1();
                    break;
                case 1:
                    getRotation2();
                    break;
                case 2:
                    getRotation3();
                    break;
                case 3:
                    getRotation0();
                    break;
            }

            Controls.up = false;
        }

        if (bottomCollide){

            settled = true;

        } else {

            falling = true;
            if (falling) {
                blocks[0].y += 1;
                blocks[1].y += 1;
                blocks[2].y += 1;
                blocks[3].y += 1;
                falling = false;
            }

        }




    }

    public void draw(Graphics2D g2d){
        g2d.setColor(blocks[0].color);
        g2d.fillRect(blocks[0].x, blocks[0].y, Block.SIZE, Block.SIZE);
        g2d.fillRect(blocks[1].x, blocks[1].y, Block.SIZE, Block.SIZE);
        g2d.fillRect(blocks[2].x, blocks[2].y, Block.SIZE, Block.SIZE);
        g2d.fillRect(blocks[3].x, blocks[3].y, Block.SIZE, Block.SIZE);
    }
}