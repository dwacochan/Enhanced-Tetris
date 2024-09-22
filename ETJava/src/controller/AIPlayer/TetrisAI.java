//package controller.AIPlayer;
//
//import controller.BoardEvaluator;
//import model.Block;
//import model.Move;
//import model.Tetromino;
//
//public class TetrisAI {
//    private BoardEvaluator evaluator;
//
//    public TetrisAI(BoardEvaluator evaluator) {
//        this.evaluator = evaluator;
//    }
//
//    public Move findBestMove(int[][] board, Tetromino piece) {
//        Move bestMove = null;
//        int bestScore = Integer.MIN_VALUE;
//
//        for (int rotation = 0; rotation < 4; rotation++) {
//            Tetromino rotatedPiece = piece.clone();
//            rotatedPiece.rotate(rotation);
//
//            int minX = -rotatedPiece.getLeftBound();
//            int maxX = board[0].length - rotatedPiece.getRightBound() - 1;
//
//            for (int col = minX; col <= maxX; col++) {
//                int[][] simulatedBoard = simulateDrop(board, rotatedPiece, col);
//                int score = evaluator.evaluateBoard(simulatedBoard);
//
//                if (score > bestScore) {
//                    bestScore = score;
//                    bestMove = new Move(col, rotation);
//                }
//            }
//        }
//
//        return bestMove;
//    }
//
//    private int[][] simulateDrop(int[][] board, Tetromino piece, int col) {
//        int[][] simulatedBoard = copyBoard(board);
//        int dropRow = getDropRow(simulatedBoard, piece, col);
//        placePiece(simulatedBoard, piece, col, dropRow);
//        return simulatedBoard;
//    }
//
//    private int getDropRow(int[][] board, Tetromino piece, int col) {
//        int row = 0;
//        while (canPlacePiece(board, piece, col, row)) {
//            row++;
//        }
//        return row - 1; // Return the last valid row
//    }
//
//    private boolean canPlacePiece(int[][] board, Tetromino piece, int col, int row) {
//        for (Block p : piece.getBlocks()) {
//            int x = col + p.getX();
//            int y = row + p.getY();
//            if (x < 0 || x >= board[0].length || y < 0 || y >= board.length || board[y][x] != 0) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void placePiece(int[][] board, Tetromino piece, int col, int row) {
//        for (Block p : piece.getBlocks()) {
//            int x = col + p.getX();
//            int y = row + p.getY();
//            if (y >= 0 && y < board.length && x >= 0 && x < board[0].length) {
//                board[y][x] = 1; // 1 represents the piece
//            }
//        }
//    }
//
//    private int[][] copyBoard(int[][] board) {
//        int[][] newBoard = new int[board.length][];
//        for (int y = 0; y < board.length; y++) {
//            newBoard[y] = board[y].clone();
//        }
//        return newBoard;
//    }
//}
