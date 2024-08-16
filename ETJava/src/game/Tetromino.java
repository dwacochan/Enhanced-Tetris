package game;

import java.awt.*;

public class Tetromino {
    public Block blocks[] = new Block[4];
    public Block tempBlocks[] = new Block[4];
    private boolean falling = false;

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

    public void updatePosition(int rotation){

    }

    public void update(){
        if (Controls.left){
            blocks[0].x -= Block.SIZE;
            blocks[1].x -= Block.SIZE;
            blocks[2].x -= Block.SIZE;
            blocks[3].x -= Block.SIZE;

            Controls.left = false;
        }

        if (Controls.right){
            blocks[0].x += Block.SIZE;
            blocks[1].x += Block.SIZE;
            blocks[2].x += Block.SIZE;
            blocks[3].x += Block.SIZE;

            Controls.right = false;
        }

        if (Controls.down){
            blocks[0].y += Block.SIZE;
            blocks[1].y += Block.SIZE;
            blocks[2].y += Block.SIZE;
            blocks[3].y += Block.SIZE;

            Controls.down = false;
        }

        falling = true;
        if (falling) {
            blocks[0].y += 1;
            blocks[1].y += 1;
            blocks[2].y += 1;
            blocks[3].y += 1;
            falling = false;
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