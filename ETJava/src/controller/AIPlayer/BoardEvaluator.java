package controller;

public class BoardEvaluator {
    public int evaluateBoard(int[][] board) {
        int heightScore = getAggregateHeight(board);
        int holesScore = getHoles(board);
        int linesCleared = getClearedLines(board);
        int bumpinessScore = getBumpiness(board);

        // Weights can be adjusted for better performance
        return (-4 * heightScore) + (3 * linesCleared) - (5 * holesScore) - (2 * bumpinessScore);
    }

    private int getAggregateHeight(int[][] board) {
        int aggregateHeight = 0;
        for (int x = 0; x < board[0].length; x++) {
            aggregateHeight += getColumnHeight(board, x);
        }
        return aggregateHeight;
    }

    private int getColumnHeight(int[][] board, int col) {
        for (int y = 0; y < board.length; y++) {
            if (board[y][col] != 0) {
                return board.length - y;
            }
        }
        return 0;
    }

    private int getHoles(int[][] board) {
        int holes = 0;
        for (int x = 0; x < board[0].length; x++) {
            boolean blockFound = false;
            for (int y = 0; y < board.length; y++) {
                if (board[y][x] != 0) {
                    blockFound = true;
                } else if (blockFound) {
                    holes++;
                }
            }
        }
        return holes;
    }

    private int getClearedLines(int[][] board) {
        int clearedLines = 0;
        for (int y = 0; y < board.length; y++) {
            boolean isFull = true;
            for (int x = 0; x < board[0].length; x++) {
                if (board[y][x] == 0) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                clearedLines++;
            }
        }
        return clearedLines;
    }

    private int getBumpiness(int[][] board) {
        int bumpiness = 0;
        int[] heights = new int[board[0].length];
        for (int x = 0; x < board[0].length; x++) {
            heights[x] = getColumnHeight(board, x);
        }
        for (int x = 0; x < heights.length - 1; x++) {
            bumpiness += Math.abs(heights[x] - heights[x + 1]);
        }
        return bumpiness;
    }
}
