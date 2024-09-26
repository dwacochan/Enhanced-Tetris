package controller;

import model.OpMove;
import model.PureGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TestableServerControlledPlayer extends ServerControlledPlayer {

    public List<String> actions = new ArrayList<>();
    private OpMove predefinedMove;
    public Timer testTimer;

    public TestableServerControlledPlayer(int gameNumber, OpMove predefinedMove) {
        super(gameNumber);
        this.predefinedMove = predefinedMove;
        this.testTimer = new Timer();

        // Replace the timer with our test timer
        this.timer = this.testTimer;
        this.downTimer = new Timer();
    }

    @Override
    public void decideAndMakeBestMove(PureGame pureGame) {
        // Stop any previous downward movement
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = new Timer();
        }

        OpMove move = predefinedMove; // Use the predefined move instead of server connection

        int halfWidth = pureGame.getWidth() / 2;
        int movesX = move.opX();
        int movesLeft = halfWidth;

        // Define movement tasks
        TimerTask moveRightTask = new TimerTask() {
            int moveCount = 0;

            @Override
            public void run() {
                if (moveCount < movesX) {
                    moveRight();
                    moveCount++;
                } else {
                    startDownwardMovement();
                    cancel();
                }
            }
        };

        TimerTask moveLeftTask = new TimerTask() {
            int moveCount = 0;

            @Override
            public void run() {
                if (moveCount < movesLeft) {
                    moveLeft();
                    moveCount++;
                } else {
                    timer.schedule(moveRightTask, 35, 35);
                    cancel();
                }
            }
        };

        TimerTask rotateTask = new TimerTask() {
            int rotateCount = 0;

            @Override
            public void run() {
                if (rotateCount < Math.abs(move.opRotate())) {
                    moveUp(); // Assuming moveUp() rotates the block
                    rotateCount++;
                } else {
                    timer.schedule(moveLeftTask, 35, 35);
                    cancel();
                }
            }
        };

        // Schedule rotation task
        timer.schedule(rotateTask, 0, 35);
    }

    @Override
    protected void moveLeft() {
        actions.add("moveLeft");
    }

    @Override
    protected void moveRight() {
        actions.add("moveRight");
    }

    @Override
    protected void moveUp() {
        actions.add("moveUp");
    }

    @Override
    protected void startDownwardMovement() {
        actions.add("startDownwardMovement");
    }
}
