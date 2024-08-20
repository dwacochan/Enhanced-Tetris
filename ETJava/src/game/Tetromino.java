package game;

import java.awt.*;

public class Tetromino {
    public Block[] blocks = new Block[4];
    public Block[] tempBlocks = new Block[4];
    public int rotation = 0;
    public boolean leftCollide;
    public boolean rightCollide;
    public boolean bottomCollide;
    public boolean settled = false;
    public boolean settling;
    int settlingTimer = 0;

    public void create(Color color){
        for (int i = 0; i < 4; i++) {
            blocks[i] = new Block(color);
            tempBlocks[i] = new Block(color);
        }
    }

    public void setPosition(int x, int y){

    }

    public void updateRotation(int rotation){

        checkRotationCollision();

        if (!leftCollide && !rightCollide && !bottomCollide) {
            this.rotation = rotation;
            for (int i = 0; i < 4; i++) {
                blocks[i].x = tempBlocks[i].x;
                blocks[i].y = tempBlocks[i].y;
            }
        }
    }

    protected void getRotation0(){}

    protected void getRotation1(){}

    protected void getRotation2(){}

    protected void getRotation3(){}

    public void checkMovementCollision(){

        leftCollide = false;
        rightCollide = false;
        bottomCollide = false;

        checkSettledTetrominoCollision();

        for (Block block : blocks) {
            if (block.x == Gameplay.left_x) {
                leftCollide = true;
                break;
            }
        }

        for (Block block : blocks) {
            if (block.x + Block.SIZE == Gameplay.right_x) {
                rightCollide = true;
                break;
            }
        }

        for (Block block : blocks) {
            if (block.y + Block.SIZE >= Gameplay.bottom_y) {
                bottomCollide = true;
                break;
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
                break;
            }
        }

        for (Block block : tempBlocks) {
            if (block.x + Block.SIZE > Gameplay.right_x) {
                rightCollide = true;
                break;
            }
        }

        for (Block block : tempBlocks) {
            if (block.y + Block.SIZE >= Gameplay.bottom_y) {
                bottomCollide = true;
                break;
            }
        }

    }

    private void checkSettledTetrominoCollision() {
        for (Block settledBlock : Gameplay.settledTetrominos) {
            int settledX = settledBlock.x;
            int settledY = settledBlock.y;

            for (Block block : blocks) {
                if (isBottomCollision(block, settledX, settledY)) {
                    bottomCollide = true;
                }
                if (isLeftCollision(block, settledX, settledY)) {
                    leftCollide = true;
                }
                if (isRightCollision(block, settledX, settledY)) {
                    rightCollide = true;
                }
            }
        }
    }

    private boolean isBottomCollision(Block block, int settledX, int settledY) {
        return block.x == settledX && block.y + Block.SIZE >= settledY;
    }

    private boolean isLeftCollision(Block block, int settledX, int settledY) {
        return block.x - Block.SIZE == settledX && block.y >= settledY && block.y < settledY + Block.SIZE;
    }

    private boolean isRightCollision(Block block, int settledX, int settledY) {
        return block.x + Block.SIZE == settledX && block.y >= settledY && block.y < settledY + Block.SIZE;
    }


    public void update() {
        if (settling) {
            settle();
        }

        checkMovementCollision();

        handleMovement();
        handleRotation();

        if (bottomCollide) {
            settling = true;
        } else {
            handleFalling();
        }
    }

    private void handleMovement() {
        if (Controls.left && !leftCollide) {
            moveLeft();
            Controls.left = false;
        }

        if (Controls.right && !rightCollide) {
            moveRight();
            Controls.right = false;
        }

        if (Controls.down && !bottomCollide) {
            moveDown();
            Controls.down = false;
        }
    }

    private void handleRotation() {
        if (Controls.up) {
            rotate();
            Controls.up = false;
        }
    }

    private void moveLeft() {
        for (Block block : blocks) {
            block.x -= Block.SIZE;
        }
    }

    private void moveRight() {
        for (Block block : blocks) {
            block.x += Block.SIZE;
        }
    }

    private void moveDown() {
        for (Block block : blocks) {
            block.y += Block.SIZE;
        }
    }

    private void rotate() {
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
    }

    private void handleFalling() {
        for (Block block : blocks) {
            block.y += 1;
        }
    }


    private void settle(){

        settlingTimer += 1;
        if (settlingTimer == 45){

            settlingTimer = 0;
            checkMovementCollision();
            if (bottomCollide){
                settled = true;
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