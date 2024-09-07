package game;

import java.awt.*;

public class Tetromino {
    public Block[] blocks = new Block[4];
    public Block[] tempBlocks = new Block[4];
    public int rotation = 0;
    public boolean leftCollide = false, rightCollide = false, bottomCollide = false;
    public boolean settled = false;
    public boolean settling = false;
    private int settlingTimer = 0;

    public void create(Color color) {
        for (int i = 0; i < 4; i++) {
            blocks[i] = new Block(color);
            tempBlocks[i] = new Block(color);
        }
    }

    public void setPosition(int x, int y) {
        for (Block block : blocks) {
            block.x = x;
            block.y = y;
        }
    }

    protected void updateRotation(int rotation) {
        checkRotationCollision();

        if (!leftCollide && !rightCollide && !bottomCollide) {
            this.rotation = rotation;
            for (int i = 0; i < 4; i++) {
                blocks[i].x = tempBlocks[i].x;
                blocks[i].y = tempBlocks[i].y;
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
        for (Block settledBlock : Gameplay.settledTetrominos) {
            for (Block block : blocksToCheck) {
                if (isCollision(block, settledBlock)) {
                    updateCollisionFlags(block, settledBlock);
                }
            }
        }
    }

    private boolean isCollision(Block movingBlock, Block settledBlock) {
        // Checks for block overlap with settledBlock
        return movingBlock.x < settledBlock.x + Block.SIZE &&
                movingBlock.x + Block.SIZE > settledBlock.x &&
                movingBlock.y < settledBlock.y + Block.SIZE &&
                movingBlock.y + Block.SIZE > settledBlock.y;
    }

    private void updateCollisionFlags(Block block, Block settledBlock) {
        if (block.y + Block.SIZE > settledBlock.y && block.y < settledBlock.y + Block.SIZE) {
            if (block.x + Block.SIZE > settledBlock.x && block.x < settledBlock.x + Block.SIZE) {
                rightCollide = true;
            }
            if (block.x < settledBlock.x + Block.SIZE && block.x + Block.SIZE > settledBlock.x) {
                leftCollide = true;
            }
            if (block.x + Block.SIZE > settledBlock.x && block.x < settledBlock.x + Block.SIZE) {
                bottomCollide = true;
            }
        }
    }

    private void checkWallCollision(Block block) {
        if (block.x < Gameplay.left_x) {
            leftCollide = true;
        }
        if (block.x + Block.SIZE > Gameplay.right_x) {
            rightCollide = true;
        }
        if (block.y + Block.SIZE >= Gameplay.bottom_y) {
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
        if (Controls.left) {
            moveLeft();
            checkMovementCollision();
            if (leftCollide) {
                moveRight();
            }
            Controls.left = false;
        }

        if (Controls.right) {
            moveRight();
            checkMovementCollision();
            if (rightCollide) {
                moveLeft();
            }
            Controls.right = false;
        }

        if (Controls.down) {
            moveDown();
            checkMovementCollision();
            if (bottomCollide) {
                moveUp();
            }
            Controls.down = false;
        }
    }

    private void handleRotation() {
        if (settling) return;
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

    private void moveUp() {
        for (Block block : blocks) {
            block.y -= Block.SIZE;
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
            snapBackRotation();
        }
    }

    private void handleFalling() {
        for (Block block : blocks) {
            block.y += 1;
        }
        checkMovementCollision();
        if (bottomCollide) {
            settling = true;
            for (Block block : blocks) {
                block.y -= 1;
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
                    if (block.y < Gameplay.top_y + Block.SIZE + 1) {
                        Gameplay.gameOver = true;
                        break;
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(blocks[0].color);
        for (Block block : blocks) {
            g2d.fillRect(block.x, block.y, Block.SIZE, Block.SIZE);
        }
    }
}