package controller.AIPlayer;

public class BoardEvaluator {
    public int evaluateBoard(int[][] board) {
        int aggregateHeight = getAggregateHeight(board);
        int holes = getHoles(board);
        int linesCleared = getClearedLines(board);
        int bumpiness = getBumpiness(board);

        return (-2 * aggregateHeight) + (2 * linesCleared) - (2 * holes) - (1 * bumpiness);
    }

    // 2 1 2 2 400
    // 2 1 2 1 500
    // 2 2 2 1 900

    int getAggregateHeight(int[][] board) {
        int aggregateHeight = 0;
        for (int x = 0; x < board[0].length; x++) {
            aggregateHeight += getColumnHeight(board, x);
        }
        return aggregateHeight;
    }

    int getColumnHeight(int[][] board, int col) {
        for (int y = 0; y < board.length; y++) {
            if (board[y][col] != 0) {
                return board.length - y;
            }
        }
        return 0;
    }

    int getHoles(int[][] board) {
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

    int getClearedLines(int[][] board) {
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

    int getBumpiness(int[][] board) {
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
