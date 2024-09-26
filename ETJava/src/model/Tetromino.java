package model;

import controller.Controls;
import controller.Gameplay;

import java.awt.*;

public abstract class Tetromino {
    public Block[] blocks = new Block[4];
    public Block[] tempBlocks = new Block[4];
    public int rotation = 0;
    public boolean leftCollide = false, rightCollide = false, bottomCollide = false;
    public boolean settled = false;
    public boolean settling = false;
    private int settlingTimer = 0;
    private Gameplay gameplay;

    public void setGameplay(Gameplay gameplay) {
        this.gameplay = gameplay;
    }

    public void create(Color color) {
        for (int i = 0; i < 4; i++) {
            blocks[i] = new Block(color);
            tempBlocks[i] = new Block(color);
        }
    }

    public void setPosition(int x, int y) {
        for (Block block : blocks) {
            block.setX(x);
            block.setY(y);
        }
    }

    protected void updateRotation(int rotation) {
        checkRotationCollision();

        if (!leftCollide && !rightCollide && !bottomCollide) {
            this.rotation = rotation;
            for (int i = 0; i < 4; i++) {
                blocks[i].setX(tempBlocks[i].getX());
                blocks[i].setY(tempBlocks[i].getY());
            }
        } else {
            snapBackRotation();
        }
    }



    private void snapBackRotation() {
        switch (this.rotation) {
            case 0 -> getRotation0();
            case 1 -> getRotation1();
            case 2 -> getRotation2();
            case 3 -> getRotation3();
        }
    }

    protected void getRotation0() {}
    protected void getRotation1() {}
    protected void getRotation2() {}
    protected void getRotation3() {}

    private void checkMovementCollision() {
        resetCollisions();
        checkSettledTetrominoCollision(blocks);
        for (Block block : blocks) {
            checkWallCollision(block);
        }
    }

    private void checkRotationCollision() {
        resetCollisions();
        checkSettledTetrominoCollision(tempBlocks);
        for (Block block : tempBlocks) {
            checkWallCollision(block);
        }
    }

    private void resetCollisions() {
        leftCollide = false;
        rightCollide = false;
        bottomCollide = false;
    }

    private void checkSettledTetrominoCollision(Block[] blocksToCheck) {
        for (Block settledBlock : gameplay.getSettledTetrominos()) {
            for (Block block : blocksToCheck) {
                if (isCollision(block, settledBlock)) {
                    updateCollisionFlags(block, settledBlock);
                }
            }
        }
    }

    private boolean isCollision(Block movingBlock, Block settledBlock) {
        return movingBlock.getX() < settledBlock.getX() + Block.SIZE &&
                movingBlock.getX() + Block.SIZE > settledBlock.getX() &&
                movingBlock.getY() < settledBlock.getY() + Block.SIZE &&
                movingBlock.getY() + Block.SIZE > settledBlock.getY();
    }

    private void updateCollisionFlags(Block block, Block settledBlock) {
        if (block.getY() + Block.SIZE > settledBlock.getY() && block.getY() < settledBlock.getY() + Block.SIZE) {
            if (block.getX() + Block.SIZE > settledBlock.getX() && block.getX() < settledBlock.getX() + Block.SIZE) {
                rightCollide = true;
            }
            if (block.getX() < settledBlock.getX() + Block.SIZE && block.getX() + Block.SIZE > settledBlock.getX()) {
                leftCollide = true;
            }
            if (block.getX() + Block.SIZE > settledBlock.getX() && block.getX() < settledBlock.getX() + Block.SIZE) {
                bottomCollide = true;
            }
        }
    }

    private void checkWallCollision(Block block) {
        if (block.getX() < gameplay.getLeft_x()) {
            leftCollide = true;
        }
        if (block.getX() + Block.SIZE > gameplay.getRight_x()) {
            rightCollide = true;
        }
        if (block.getY() + Block.SIZE > gameplay.getBottom_y()) {
            bottomCollide = true;
        }
    }

    public void update() {
        if (settling) {
            settle();
            return;
        }
        checkMovementCollision();

        handleFalling();

        handleMovement();
        handleRotation();

    }

    private void handleMovement() {
        if (settling) return;


        ControlsSet currentControls = Controls.getCurrentControls(gameplay.getGameNumber(), gameplay.getPlayerType());


        // Handle left movement
        if (currentControls.isLeftPressed()) {
            moveLeft();
            checkMovementCollision();
            if (leftCollide) {
                moveRight();
            }
            currentControls.resetLeft();
        }

        // Handle right movement
        if (currentControls.isRightPressed()) {
            moveRight();
            checkMovementCollision();
            if (rightCollide) {
                moveLeft();
            }
            currentControls.resetRight();
        }

        // Handle down movement
        if (currentControls.isDownPressed()) {
            moveDown();
            checkMovementCollision();
            if (bottomCollide) {
                moveUp();
            }
            currentControls.resetDown();
        }
    }

    private void handleRotation() {
        if (settling) return;

        // Determine the current control set based on the game number
        ControlsSet currentControls = Controls.getCurrentControls(gameplay.getGameNumber(), gameplay.getPlayerType());

        // Handle rotation
        if (currentControls.isUpPressed()) {
            rotate();
            currentControls.resetUp();
        }
    }


    private void moveLeft() {
        for (Block block : blocks) {
            block.setX(block.getX() - Block.SIZE);
        }
    }

    private void moveRight() {
        for (Block block : blocks) {
            block.setX(block.getX() + Block.SIZE);
        }
    }

    private void moveDown() {
        for (Block block : blocks) {
            block.setY(block.getY() + Block.SIZE);
        }
    }

    private void moveUp() {
        for (Block block : blocks) {
            block.setY(block.getY() - Block.SIZE);
        }
    }

    private void rotate() {
        switch (rotation) {
            case 0 -> getRotation1();
            case 1 -> getRotation2();
            case 2 -> getRotation3();
            case 3 -> getRotation0();

        }
        checkRotationCollision();
        if (leftCollide || rightCollide || bottomCollide) {
            System.out.println("COLLIDED ON ROTATE");
            snapBackRotation();
        }
    }

    private void handleFalling() {
        for (Block block : blocks) {
            block.setY(block.getY() + 1);
        }
        checkMovementCollision();
        if (bottomCollide) {
            settling = true;
            for (Block block : blocks) {
                block.setY(block.getY() - 1);
            }
        }
    }

    private void settle() {
        settlingTimer += 1;
        if (settlingTimer == 45) {
            settlingTimer = 0;

            if (bottomCollide) {
                settled = true;
                for (Block block : blocks) {
                    if (block.getY() < gameplay.getTop_y() + Block.SIZE + 1) {
                        gameplay.gameOver();
                        break;
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2d) {
        for (Block block : blocks) {
            block.draw(g2d);
        }
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettling(boolean settling) {
        this.settling = settling;
    }
}
