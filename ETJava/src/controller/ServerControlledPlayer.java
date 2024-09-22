package controller;

import model.OpMove;
import model.PureGame;
import model.ServerConnection;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class ServerControlledPlayer extends ExternalPlayer {
    private ServerConnection serverConnection;

    public ServerControlledPlayer(int gameNumber) {
        super(gameNumber);
        this.serverConnection = new ServerConnection();
    }

    @Override
    public void decideAndMakeBestMove(PureGame pureGame) {

        // Stop any previous downward movement
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = new Timer();  // Reinitialize the downTimer
        }

        // Convert PureGame to JSON string
        Gson gson = new Gson();
        String jsonGameState = gson.toJson(pureGame);
        System.out.println("JSON game state: " + jsonGameState);

        // Get optimal move from the server
        OpMove move = serverConnection.getOptimalMove(pureGame);
        System.out.println(move);

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
                    moveUp();  // Assuming moveUp() rotates the block
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
}
