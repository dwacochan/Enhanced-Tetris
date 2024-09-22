//package controller.AIPlayer;
//
//import controller.BoardEvaluator;
//import controller.ExternalPlayer;
//import model.OpMove;
//import model.PureGame;
//import model.Tetromino;
//import model.Move;
//
//import java.util.TimerTask;
//
//public class AIControlledPlayer extends ExternalPlayer {
//    private BoardEvaluator evaluator;
//    private TetrisAI tetrisAI;
//
//    public AIControlledPlayer(int gameNumber) {
//        super(gameNumber);
//        this.evaluator = new BoardEvaluator();
//        this.tetrisAI = new TetrisAI(evaluator);
//    }
//
//    @Override
//    public void makeCallToServer(PureGame pureGame) {
//        // Stop any previous downward movement
//        if (downTimer != null) {
//            downTimer.cancel();
//            downTimer = new Timer();  // Reinitialize the downTimer
//        }
//
//        // Use AI to compute the best move
//        OpMove move = decideAndMakeBestMove(pureGame);
//
//        int movesX = move.opX();          // Number of moves to the right (negative if moving left)
//        int rotations = move.opRotate();  // Number of rotations
//
//        // Define movement tasks
//        TimerTask moveHorizontalTask = new TimerTask() {
//            int moveCount = 0;
//            int totalMoves = Math.abs(movesX);
//
//            @Override
//            public void run() {
//                if (moveCount < totalMoves) {
//                    if (movesX > 0) {
//                        moveRight();
//                    } else {
//                        moveLeft();
//                    }
//                    moveCount++;
//                } else {
//                    startDownwardMovement();
//                    cancel();
//                }
//            }
//        };
//
//        TimerTask moveLeftTask = new TimerTask() {
//            int moveCount = 0;
//
//            @Override
//            public void run() {
//                moveLeft();
//                moveCount++;
//                // Move all the way left until reaching the wall or starting position
//                if (moveCount >= pureGame.getWidth()) {
//                    timer.schedule(moveHorizontalTask, 35, 35);
//                    cancel();
//                }
//            }
//        };
//
//        TimerTask rotateTask = new TimerTask() {
//            int rotateCount = 0;
//
//            @Override
//            public void run() {
//                if (rotateCount < rotations) {
//                    moveUp();  // Assuming moveUp() rotates the block
//                    rotateCount++;
//                } else {
//                    // After rotations, move all the way left first
//                    timer.schedule(moveLeftTask, 35, 35);
//                    cancel();
//                }
//            }
//        };
//
//        // Schedule rotation task
//        timer.schedule(rotateTask, 0, 35);
//    }
//
//    private OpMove decideAndMakeBestMove(PureGame pureGame) {
//        // Get the current game board and the current tetromino shape
//        int[][] board = pureGame.getCells();
//        int[][] currentShape = pureGame.getCurrentShape();
//
//        Tetromino currentTetromino = new Tetromino(currentShape);
//
//        // Use TetrisAI to find the best move
//        Move bestMove = tetrisAI.findBestMove(board, currentTetromino);
//
//        // Compute the number of moves to the right or left from the starting position
//        int startingColumn = pureGame.getCurrentX();
//        int movesX = bestMove.getColumn() - startingColumn;
//        int rotations = bestMove.getRotation();
//
//        return new OpMove(movesX, rotations);
//    }
//}
