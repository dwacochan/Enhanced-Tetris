package controller;

import model.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExternalPlayer {
    private GameController gameController;
    private int gameNumber;
    private Timer timer;

    public ExternalPlayer(int gameNumber) {
        gameController = GameController.getInstance();
        this.gameNumber = gameNumber;
        this.timer = new Timer();  // Create the timer here
        System.out.println("ExternalPlayer constructor");
    }

    public void makeCallToServer(ArrayList<Block> settledTetrominos) {
        System.out.println("MAKE CALL TO SERVER FOR PLAYER: " + gameNumber);
        for (Block block : settledTetrominos) {
            System.out.println(block);
        }

    }

    // Simulates sending the board state and receiving instructions
    private List<String> sendBoardStateAndReceiveInstructions() {
        // For simulation, returning a fixed list of instructions
        return List.of("left", "right", "right", "up", "down");
    }

    // Process each instruction with a 0.1s delay between them in an infinite loop
    public void followInstructions() {
        List<String> instructions = sendBoardStateAndReceiveInstructions();

        TimerTask task = new TimerTask() {
            int index = 0;  // Track the current instruction index

            @Override
            public void run() {
                // Get the current instruction
                String instruction = instructions.get(index);

                // Perform the action based on the instruction
                switch (instruction) {
                    case "left":
                        moveLeft();
                        break;
                    case "right":
                        moveRight();
                        break;
                    case "up":
                        moveUp();
                        break;
                    case "down":
                        moveDown();
                        break;
                    default:
                        break;
                }

                // Move to the next instruction and loop back if at the end
                index++;
                if (index >= instructions.size()) {
                    index = 0;  // Reset to the beginning for an infinite loop
                }
            }
        };

        // Schedule the task with a 0.1s (100ms) delay between executions
        timer.schedule(task, 0, 100); // Starts immediately and repeats every 100ms
    }

    // Call this method to stop the timer and clean up resources
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
