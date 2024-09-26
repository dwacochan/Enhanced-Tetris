package controller.AIPlayer;

import model.Move;

public class TetrisAI {
    private BoardEvaluator evaluator;

    public TetrisAI(BoardEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public Move findBestMove(int[][] board, int[][] pieceShape) {
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        // Try all possible rotations (0 to 3)
        for (int rotation = 0; rotation < 4; rotation++) {
            int[][] rotatedShape = rotateShape(pieceShape, rotation);

            int shapeWidth = rotatedShape[0].length;

            // Determine possible columns to place the piece
            int minColumn = -getLeftPadding(rotatedShape);
            int maxColumn = board[0].length - shapeWidth + getRightPadding(rotatedShape);

            for (int col = minColumn; col <= maxColumn; col++) {
                int[][] simulatedBoard = simulateDrop(board, rotatedShape, col);
                int score = evaluator.evaluateBoard(simulatedBoard);

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = new Move(col, rotation);
                }
            }
        }

        return bestMove;
    }

    int[][] rotateShape(int[][] shape, int rotations) {
        int[][] rotatedShape = shape;
        for (int i = 0; i < rotations; i++) {
            rotatedShape = rotate90(rotatedShape);
        }
        return rotatedShape;
    }

    int[][] rotate90(int[][] shape) {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                rotated[x][rows - y - 1] = shape[y][x];
            }
        }
        return rotated;
    }

    int getLeftPadding(int[][] shape) {
        int padding = shape[0].length;
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[0].length; x++) {
                if (shape[y][x] != 0) {
                    padding = Math.min(padding, x);
                    break;
                }
            }
        }
        return padding;
    }

    int getRightPadding(int[][] shape) {
        int padding = 0;
        for (int y = 0; y < shape.length; y++) {
            for (int x = shape[0].length - 1; x >= 0; x--) {
                if (shape[y][x] != 0) {
                    padding = Math.max(padding, shape[0].length - x - 1);
                    break;
                }
            }
        }
        return padding;
    }

    int[][] simulateDrop(int[][] board, int[][] shape, int col) {
        int[][] newBoard = copyBoard(board);
        int row = -getTopPadding(shape);

        // Drop the piece until it collides
        while (canPlace(newBoard, shape, row + 1, col)) {
            row++;
        }

        // Place the piece on the board
        placePiece(newBoard, shape, row, col);

        return newBoard;
    }

    boolean canPlace(int[][] board, int[][] shape, int row, int col) {
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[0].length; x++) {
                if (shape[y][x] != 0) {
                    int boardY = row + y;
                    int boardX = col + x;
                    if (boardY >= board.length || boardX < 0 || boardX >= board[0].length || board[boardY][boardX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    void placePiece(int[][] board, int[][] shape, int row, int col) {
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[0].length; x++) {
                if (shape[y][x] != 0) {
                    int boardY = row + y;
                    int boardX = col + x;
                    if (boardY >= 0 && boardY < board.length && boardX >= 0 && boardX < board[0].length) {
                        board[boardY][boardX] = shape[y][x];
                    }
                }
            }
        }
    }

    int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[board.length][board[0].length];
        for (int y = 0; y < board.length; y++) {
            System.arraycopy(board[y], 0, newBoard[y], 0, board[0].length);
        }
        return newBoard;
    }

    int getTopPadding(int[][] shape) {
        int padding = shape.length;
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[0].length; x++) {
                if (shape[y][x] != 0) {
                    padding = Math.min(padding, y);
                    break;
                }
            }
        }
        return padding;
    }
}
