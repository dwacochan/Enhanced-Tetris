package controller.AIPlayer;

import controller.ExternalPlayer;
import model.Move;
import model.OpMove;
import model.PureGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AIControlledPlayer extends ExternalPlayer {
    private BoardEvaluator evaluator;
    private TetrisAI tetrisAI;

    private static final boolean FORCE_DROP_LEFT = false; // Disabled forced left drop
    private static final boolean FORCE_ROTATIONS_ENABLED = false; // Disabled forced rotations
    private static final int FORCED_ROTATIONS = 3; // Not used when forcing rotations is disabled

    public AIControlledPlayer(int gameNumber) {
        super(gameNumber);
        this.evaluator = new BoardEvaluator();
        this.tetrisAI = new TetrisAI(evaluator);
    }

    enum TetrominoType {
        I, O, T, S, Z, J, L, UNKNOWN
    }

    // Calibration offsets per shape per rotation
    private static final Map<TetrominoType, Map<Integer, Integer>> SHAPE_CALIBRATION = new HashMap<>();

    static {
        // Initialize calibration offsets for each shape and rotation
        for (TetrominoType type : TetrominoType.values()) {
            Map<Integer, Integer> rotationOffsets = new HashMap<>();
            // Initialize offsets for rotations 0 to 3
            rotationOffsets.put(0, 0);
            rotationOffsets.put(1, 0);
            rotationOffsets.put(2, 0);
            rotationOffsets.put(3, 0);
            SHAPE_CALIBRATION.put(type, rotationOffsets);
        }

        // Example calibration offsets (adjust these based on testing)
        SHAPE_CALIBRATION.get(TetrominoType.O).put(0, 1);
        SHAPE_CALIBRATION.get(TetrominoType.I).put(0, 1);

        SHAPE_CALIBRATION.get(TetrominoType.O).put(1, 1);
        SHAPE_CALIBRATION.get(TetrominoType.T).put(1, 1);
        SHAPE_CALIBRATION.get(TetrominoType.I).put(1, 2);
        SHAPE_CALIBRATION.get(TetrominoType.S).put(1, 1);
        SHAPE_CALIBRATION.get(TetrominoType.J).put(1, 1);
        SHAPE_CALIBRATION.get(TetrominoType.L).put(1, 1);

        SHAPE_CALIBRATION.get(TetrominoType.O).put(2, 1);

        SHAPE_CALIBRATION.get(TetrominoType.O).put(3, 2);
        SHAPE_CALIBRATION.get(TetrominoType.S).put(3, 1);
        SHAPE_CALIBRATION.get(TetrominoType.I).put(3, 2);
        // Add calibration offsets for other shapes and rotations as needed
    }

    @Override
    public void decideAndMakeBestMove(PureGame pureGame) {
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = new Timer();  // Reinitialize the downTimer
        }

        // Use AI to compute the best move
        OpMove move = computeBestMove(pureGame);

        int movesX = move.opX();          // Number of moves to the right (negative if moving left)
        int rotations = move.opRotate();  // Number of rotations

        // Define movement tasks
        TimerTask moveHorizontalTask = new TimerTask() {
            int moveCount = 0;
            int totalMoves = Math.abs(movesX);

            @Override
            public void run() {
                if (moveCount < totalMoves) {
                    if (movesX > 0) {
                        moveRight();
                    } else {
                        moveLeft();
                    }
                    moveCount++;
                } else {
                    startDownwardMovement();
                    cancel();
                }
            }
        };

        TimerTask rotateTask = new TimerTask() {
            int rotateCount = 0;

            @Override
            public void run() {
                if (rotateCount < rotations) {
                    System.out.println("ROTATED");
                    moveUp();  // Use the correct rotation method
                    rotateCount++;
                } else {
                    // After rotations, move horizontally
                    timer.schedule(moveHorizontalTask, 35, 35);
                    cancel();
                }
            }
        };

        // Schedule rotation task
        timer.schedule(rotateTask, 35, 35);
    }

    OpMove computeBestMove(PureGame pureGame) {
        int[][] board = pureGame.getCells();
        int[][] currentShape = pureGame.getCurrentShape();

        // Identify the shape
        TetrominoType shapeType = identifyShape(currentShape);
        System.out.println("Identified shape: " + shapeType);

        int currentRotation = 0; // Assuming the current shape is at rotation 0

        // Use TetrisAI to find the best move
        Move bestMove;

        if (FORCE_DROP_LEFT) {
            int rotation = FORCE_ROTATIONS_ENABLED ? FORCED_ROTATIONS % 4 : 0;
            bestMove = new Move(1, rotation); // Force column 1 and forced rotation
        } else {
            bestMove = tetrisAI.findBestMove(board, currentShape);
        }

        System.out.println("Best move determined by TetrisAI: " + bestMove);

        // Compute the number of moves to the right or left from the starting position
        int startingColumn = (pureGame.getWidth() - currentShape[0].length) / 2;
        int calibrationOffset = getCalibrationOffset(shapeType, bestMove.getRotation());
        startingColumn += calibrationOffset;

        int movesX = bestMove.getColumn() - startingColumn;
        int rotations = bestMove.getRotation();

        System.out.println("Calculated movesX: " + movesX + ", rotations: " + rotations);

        return new OpMove(movesX, rotations);
    }

    int getCalibrationOffset(TetrominoType shapeType, int rotation) {
        Map<Integer, Integer> rotationOffsets = SHAPE_CALIBRATION.getOrDefault(shapeType, new HashMap<>());
        return rotationOffsets.getOrDefault(rotation, 0);
    }

    TetrominoType identifyShape(int[][] shape) {
        // Convert the shape array to a string representation
        String shapeString = shapeArrayToString(shape);

        switch (shapeString) {
            case "1111":
                return TetrominoType.I;
            case "11|11":
                return TetrominoType.O;
            case "010|111":
                return TetrominoType.T;
            case "011|110":
                return TetrominoType.S;
            case "110|011":
                return TetrominoType.Z;
            case "100|111":
                return TetrominoType.J;
            case "001|111":
                return TetrominoType.L;
            default:
                return TetrominoType.UNKNOWN;
        }
    }

    String shapeArrayToString(int[][] shape) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[0].length; x++) {
                sb.append(shape[y][x]);
            }
            if (y < shape.length - 1) {
                sb.append("|"); // Use '|' as a row delimiter
            }
        }
        return sb.toString();
    }

    private void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                // Print 1s and 0s with a space in between
                System.out.print(cell + " ");
            }
            System.out.println();  // New line at the end of each row
        }
    }
}
