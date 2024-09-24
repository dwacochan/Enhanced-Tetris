package controller;

import model.Block;
import model.PlayerType;
import model.PureGame;
import model.Tetromino;
import model.factory.TetrominoFactory;
import model.tetrominos.O;
import view.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;



public class Gameplay {

    private GameController gameController;
    private int width;
    private int height;
    private int left_x;
    private int right_x;
    private int top_y;
    private int bottom_y;
    private Tetromino currentTetromino;
    private int TETROMINOSTART_X;
    private int TETROMINOSTART_Y;
    private ArrayList<Block> settledTetrominos = new ArrayList<>();
    private boolean gameOver = false;
    private PlayerType playerType = PlayerType.HUMAN;


    private int gameNumber;



    // Scoring and level tracking
    private int score = 0;
    private int rowsErased = 0;
    private int level = 1;

    public Gameplay(int width, int height, int gameNumber, PlayerType playerType) {
        this.gameController = GameController.getInstance();
        this.width = width;
        this.height = height;
        this.gameNumber = gameNumber;
        this.playerType = playerType;

        initializeDimensions();
        currentTetromino = selectShape();
        currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
        currentTetromino.setGameplay(this);

    }

    public Gameplay(int width, int height, int gameNumber) {
        this.gameController = GameController.getInstance();
        this.width = width;
        this.height = height;
        this.gameNumber = gameNumber;
        initializeDimensions();
        currentTetromino = selectShape();
        currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
        currentTetromino.setGameplay(this);  // Pass instance of Gameplay to Tetromino
    }

    public Gameplay(int width, int height) {
        this(width,height,1);
        this.gameController = GameController.getInstance();
    }


    public int[][] getBoard2DArray() {
        int rows = height / Block.SIZE;  // number of rows
        int cols = width / Block.SIZE;   // number of columns
        int[][] board = new int[rows][cols];

        for (Block block : settledTetrominos) {
            int xIndex = (block.getX() - left_x) / Block.SIZE;
            int yIndex = (block.getY() - top_y) / Block.SIZE;

            if (xIndex >= 0 && xIndex < cols && yIndex >= 0 && yIndex < rows) {
                board[yIndex][xIndex] = 1;  // Mark as filled
            }
        }

        return board;
    }

    public int[][] convertBlocksTo2DArray(Block[] blocks) {
        // Find the minimum and maximum X and Y coordinates to determine the bounds
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Block block : blocks) {
            if (block.getX() < minX) minX = block.getX();
            if (block.getX() > maxX) maxX = block.getX();
            if (block.getY() < minY) minY = block.getY();
            if (block.getY() > maxY) maxY = block.getY();
        }

        // Determine the width and height of the Tetromino shape in block coordinates
        int width = (maxX - minX) / Block.SIZE + 1;
        int height = (maxY - minY) / Block.SIZE + 1;

        // Create a 2D array with the size of the Tetromino's bounding box
        int[][] tetrominoShape = new int[height][width];

        // Map the Tetromino's blocks to the 2D array
        for (Block block : blocks) {
            int xIndex = (block.getX() - minX) / Block.SIZE;
            int yIndex = (block.getY() - minY) / Block.SIZE;
            tetrominoShape[yIndex][xIndex] = 1;  // Mark block position with 1
        }
        return tetrominoShape;
    }

    public int[][] getCurrentTetromino2DArray() {
        // Get the blocks of the current Tetromino
        Block[] blocks = currentTetromino.getBlocks();
        return convertBlocksTo2DArray(blocks);

    }

    public int[][] getNextTetromino2DArray() {

        Tetromino nextTetromino =  TetrominoFactory.peekNextTetromino(this);
        nextTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
        Block[] blocks = nextTetromino.getBlocks();
        return convertBlocksTo2DArray(blocks);
    }


    private void initializeDimensions() {
        setLeft_x(0);
        setRight_x(getLeft_x() + width);
        setTop_y(0);
        setBottom_y(getTop_y() + height);

        TETROMINOSTART_X = getLeft_x() + ((getRight_x() - getLeft_x()) / Block.SIZE / 2) * Block.SIZE;
        TETROMINOSTART_Y = getTop_y() + Block.SIZE;
    }


    public void resetDimensions() {
        initializeDimensions();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public PlayerType getPlayerType() { return playerType; }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private Tetromino selectShape() {
        return TetrominoFactory.getNextTetromino(this);
    }

    public void update() {
        if (isGameOver()) {
            return;
        }
        if (currentTetromino.isSettled()) {
            settledTetrominos.addAll(Arrays.asList(currentTetromino.getBlocks()));

            currentTetromino.setSettling(false);
            currentTetromino = selectShape();
            currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
            currentTetromino.setGameplay(this);  // Set reference of Gameplay

            checkRowErasure();

            // Alert external player of state of game
            if (gameController.getGameLoop().getServerControlledPlayer(gameNumber) != null){
                gameController.getGameLoop().getServerControlledPlayer(gameNumber).decideAndMakeBestMove(new PureGame(
                        width / Block.SIZE,
                        height / Block.SIZE,
                        getBoard2DArray(),
                        getCurrentTetromino2DArray(),
                        getNextTetromino2DArray()
                ));
            }

        } else {
            currentTetromino.update();
        }
    }

    private void checkRowErasure() {
        ArrayList<Integer> fullRows = new ArrayList<>();
        checkFullRow(fullRows);
        if (!fullRows.isEmpty()){
            updateScore(fullRows.size());
            rowsErased += fullRows.size();
            checkLevelUp();
        }
        removeFullRow(fullRows);
        shiftDownRemainingRows(fullRows);
    }

    private void checkFullRow(ArrayList<Integer> fullRows) {
        for (int y = getTop_y(); y < getBottom_y(); y += Block.SIZE) {
            int blockNum = 0;
            for (Block settledBlock : settledTetrominos) {
                if (settledBlock.getY() == y) {
                    blockNum++;
                }
            }
            if (blockNum == (getRight_x() - getLeft_x()) / Block.SIZE) {
                fullRows.add(y);
            }
        }
    }



    private void updateScore(int rowsErased) {
        switch (rowsErased) {
            case 1 -> score += 100;
            case 2 -> score += 300;
            case 3 -> score += 600;
            default -> score += 1000; // 4 and over
        }
        // TODO: play sound if > 1
    }

    private void checkLevelUp() {
        if (rowsErased >= level * 10) {
            level++;
            System.out.println("Level up! Now at level: " + level);
            // TODO: Increase Tetromino falling speed
            // TODO: Play level up sound
        }
    }


    private void removeFullRow(ArrayList<Integer> fullRows) {
        for (int row : fullRows) {
            settledTetrominos.removeIf(block -> block.getY() == row);
        }
    }

    private void shiftDownRemainingRows(ArrayList<Integer> fullRows) {
        for (int row : fullRows) {
            for (Block settledBlock : settledTetrominos) {
                if (settledBlock.getY() < row) {
                    settledBlock.setY(settledBlock.getY() + Block.SIZE);
                }
            }
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(238, 238, 238));
        g2d.fillRect(left_x - 2, top_y - 2, width + 4, height + 4);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(left_x - 2, top_y - 2, width + 4, height + 4);

        if (currentTetromino != null) {
            currentTetromino.draw(g2d);
        }

        for (Block block : settledTetrominos) {
            block.draw(g2d);
        }

        g2d.setColor(Color.BLACK);
        g2d.setFont(g2d.getFont().deriveFont(10f));

        if (Controls.pause) {
            int x = (int) (left_x + 10.75f * Block.SIZE);
            int y = top_y + 5 * Block.SIZE;
            g2d.drawString("PAUSED", x, y);
            g2d.drawString("Press P to unpause", (int) (x - 2.5f * Block.SIZE), y + 1f * Block.SIZE);
        }

        int halfSectionHeight = this.height / 16;

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));

        // Get FontMetrics once
        FontMetrics metrics = g2d.getFontMetrics();

        g2d.drawString("Game Info: (Player " + this.gameNumber + ")", getCenteredX("Game Info: (Player " + this.gameNumber + ")", metrics), top_y + halfSectionHeight);

        PlayerType playerType;
        if (gameNumber == 1) {
            playerType = GameController.getInstance().getConfigurations().getPlayer1Type();
        } else {
            playerType = GameController.getInstance().getConfigurations().getPlayer2Type();
        }
        g2d.drawString("Player Type: " + playerType.toString(), getCenteredX("Player Type: " + playerType.toString(), metrics), top_y + halfSectionHeight * 3);
        g2d.drawString("Initial Level: " + GameController.getInstance().getConfigurations().getGameLevel(), getCenteredX("Initial Level: " + GameController.getInstance().getConfigurations().getGameLevel(), metrics), top_y + halfSectionHeight * 5);
        g2d.drawString("Current Level: " + level, getCenteredX("Current Level: " + level, metrics), top_y + halfSectionHeight * 7);
        g2d.drawString("Line Erased: " + rowsErased, getCenteredX("Line Erased: " + rowsErased, metrics), top_y + halfSectionHeight * 9);
        g2d.drawString("Score: " + score, getCenteredX("Score: " + score, metrics), top_y + halfSectionHeight * 11);
        g2d.drawString("Next Tetromino: ", getCenteredX("Next Tetromino: ", metrics), top_y + halfSectionHeight * 13);
        Tetromino nextTetromino = TetrominoFactory.peekNextTetromino(this);

        int remainingSpace = bottom_y - (top_y + halfSectionHeight * 13);

        if(nextTetromino instanceof O){
            nextTetromino.setPosition(left_x-(GamePanel.LEFT_MARGIN / 2)-Block.SIZE,top_y + halfSectionHeight * 13 + remainingSpace/3);
        }else{
            nextTetromino.setPosition(left_x-(GamePanel.LEFT_MARGIN / 2)-Block.SIZE,top_y + halfSectionHeight * 13 + remainingSpace/2);
        }
        nextTetromino.draw(g2d);
    }

    // Helper method to center text in the left margin
    private int getCenteredX(String text, FontMetrics metrics) {
        int textWidth = metrics.stringWidth(text);
        int marginWidth = GamePanel.LEFT_MARGIN;  // Left margin width
        return left_x - marginWidth + (marginWidth - textWidth) / 2;
    }




    public void reset() {
        settledTetrominos.clear();
        Controls.pause = false;
        gameOver = false;
        currentTetromino = selectShape();
        currentTetromino.setPosition(TETROMINOSTART_X, TETROMINOSTART_Y);
        currentTetromino.setGameplay(this);

        // Reset score and level
        score = 0;
        rowsErased = 0;
        level = 1;
    }

    public void gameOver(){
        gameOver = true;
        // TODO: PLAY GAME OVER SOUND
    }


    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Block> getSettledTetrominos() {
        return settledTetrominos;
    }

    public int getLeft_x() {
        return left_x;
    }

    public void setLeft_x(int left_x) {
        this.left_x = left_x;
    }

    public int getRight_x() {
        return right_x;
    }

    public void setRight_x(int right_x) {
        this.right_x = right_x;
    }

    public int getTop_y() {
        return top_y;
    }

    public void setTop_y(int top_y) {
        this.top_y = top_y;
    }

    public int getBottom_y() {
        return bottom_y;
    }

    public void setBottom_y(int bottom_y) {
        this.bottom_y = bottom_y;
    }

    public int getScore() {
        return score;
    }

    public int getRowsErased() {
        return rowsErased;
    }

    public int getLevel() {
        return level;
    }
}
