package controller;

import model.OpMove;
import model.PureGame;
import model.ServerConnection;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;




public class ExternalPlayer {
    private GameController gameController;
    private int gameNumber;
    private Timer timer;
    private Timer downTimer;
    private ServerConnection serverConnection;

    public ExternalPlayer(int gameNumber) {
        gameController = GameController.getInstance();
        this.gameNumber = gameNumber;
        this.downTimer = new Timer();  // Separate timer for downward movement
        this.timer = new Timer();  // Create the timer here
        System.out.println("ExternalPlayer constructor");
        this.serverConnection = new ServerConnection();

    }

    public void makeCallToServer(PureGame pureGame) {
        // Stop any previous downward movement
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = new Timer();  // Reinitialize the downTimer
        }

        // Convert PureGame to JSON string manually
        Gson gson = new Gson();
        String jsonGameState = gson.toJson(pureGame);
        System.out.println("JSON game state: " + jsonGameState);

        // Call ServerConnection with the PureGame object or directly with the JSON if needed
        OpMove move = serverConnection.getOptimalMove(pureGame);  // This will internally also JSON-serialize it
        System.out.println(move);

        int halfWidth = pureGame.getWidth() / 2;
        int movesX = move.opX();  // The number of moves right based on OpMove
        int movesLeft = halfWidth;  // Move left half of the board width

        // Task to move the block to the right according to OpMove's opX
        TimerTask moveRightTask = new TimerTask() {
            int moveCount = 0;

            @Override
            public void run() {
                if (moveCount < movesX) {
                    moveRight();
                    moveCount++;
                } else {
                    // Once done with right moves, start moving down continuously
                    startDownwardMovement();
                    cancel();  // Stop the task after completing all right moves
                }
            }
        };

        // Task to move the block to the left first (halfWidth moves)
        TimerTask moveLeftTask = new TimerTask() {
            int moveCount = 0;

            @Override
            public void run() {
                if (moveCount < movesLeft) {
                    moveLeft();
                    moveCount++;
                } else {
                    // After finishing moving left, start moving right
                    timer.schedule(moveRightTask, 35, 35);
                    cancel();  // Stop the left movement task
                }
            }
        };

        // Task to rotate the block first before any movement
        TimerTask rotateTask = new TimerTask() {
            int rotateCount = 0;

            @Override
            public void run() {
                if (rotateCount < Math.abs(move.opRotate())) {
                    moveUp();  // Assuming moveUp() rotates the block
                    rotateCount++;
                } else {
                    // After completing rotations, schedule the left movement
                    timer.schedule(moveLeftTask, 35, 35);  // Start moving left with delay
                    cancel();  // Stop the rotation task
                }
            }
        };

        // Schedule the rotateTask to run immediately, with a 35ms delay between rotations
        timer.schedule(rotateTask, 0, 35);
    }

    // Call this method to stop the downward movement when needed
    private void startDownwardMovement() {
        TimerTask moveDownTask = new TimerTask() {
            @Override
            public void run() {
                moveDown();  // Continuously move down
            }
        };
        downTimer.schedule(moveDownTask, 0, 100);  // Moves down every 100ms (adjust if needed)
    }

    public void stop() {
        System.out.println("Stopping " + this + " number : " + gameNumber);
        if (timer != null) {
            timer.cancel();  // Cancel the timer to stop task execution
        }
        resetControls();  // Optionally reset controls when stopping
    }

    // Movement control methods for External Player 1 or 2 based on gameNumber
    private void moveLeft() {
        if (gameNumber == 1) {
            Controls.setExt_1_left(true);
        } else if (gameNumber == 2) {
            Controls.setExt_2_left(true);
        }
    }

    private void moveRight() {
        if (gameNumber == 1) {
            Controls.setExt_1_right(true);
        } else if (gameNumber == 2) {
            Controls.setExt_2_right(true);
        }
    }

    private void moveUp() {
        if (gameNumber == 1) {
            Controls.setExt_1_up(true);
        } else if (gameNumber == 2) {
            Controls.setExt_2_up(true);
        }
    }

    private void moveDown() {
        if (gameNumber == 1) {
            Controls.setExt_1_down(true);
        } else if (gameNumber == 2) {
            Controls.setExt_2_down(true);
        }
    }

    // Reset external player controls
    private void resetControls() {
        Timer resetTimer = new Timer();
        resetTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (gameNumber == 1) {
                    Controls.setExt_1_left(false);
                    Controls.setExt_1_right(false);
                    Controls.setExt_1_up(false);
                    Controls.setExt_1_down(false);
                } else if (gameNumber == 2) {
                    Controls.setExt_2_left(false);
                    Controls.setExt_2_right(false);
                    Controls.setExt_2_up(false);
                    Controls.setExt_2_down(false);
                }
            }
        }, 50);  // Reset after 50ms to simulate key release
    }
}
