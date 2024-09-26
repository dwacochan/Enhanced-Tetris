package controller.AIPlayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardEvaluatorTest {

    private BoardEvaluator evaluator;

    @BeforeEach
    void setUp() {
        evaluator = new BoardEvaluator();
    }

    @Test
    void testEvaluateBoard_EmptyBoard() {
        // Empty board
        int[][] board = new int[20][10];
        int score = evaluator.evaluateBoard(board);

        // All metrics should be zero
        assertEquals(0, score, "Score should be zero for an empty board");
    }

    @Test
    void testEvaluateBoard_FullLines() {
        // Board with two full lines at the bottom
        int[][] board = new int[20][10];
        for (int y = 18; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                board[y][x] = 1;
            }
        }
        int score = evaluator.evaluateBoard(board);

        // Expected metrics
        int aggregateHeight = 2 * 10; // Two rows fully filled
        int holes = 0;
        int linesCleared = 2;
        int bumpiness = 0;
        int expectedScore = (-2 * aggregateHeight) + (2 * linesCleared) - (2 * holes) - (1 * bumpiness);

        assertEquals(expectedScore, score, "Score should match expected value for full lines");
    }

    @Test
    void testEvaluateBoard_WithHoles() {
        // Board with holes in one column
        int[][] board = new int[20][10];
        for (int y = 10; y < 20; y++) {
            if (y != 15) { // Create a hole at y=15
                board[y][0] = 1;
            }
        }
        int score = evaluator.evaluateBoard(board);

        // Expected metrics
        int aggregateHeight = evaluator.getAggregateHeight(board);
        int holes = evaluator.getHoles(board);
        int linesCleared = evaluator.getClearedLines(board);
        int bumpiness = evaluator.getBumpiness(board);
        int expectedScore = (-2 * aggregateHeight) + (2 * linesCleared) - (2 * holes) - (1 * bumpiness);

        assertEquals(expectedScore, score, "Score should match expected value for board with holes");
        assertEquals(1, holes, "There should be 1 hole");
    }

    @Test
    void testEvaluateBoard_VariedBumpiness() {
        // Board with varying column heights
        int[][] board = new int[20][10];
        int[] heights = {5, 3, 2, 7, 1, 4, 0, 0, 0, 0};
        for (int x = 0; x < heights.length; x++) {
            int height = heights[x];
            for (int y = 20 - height; y < 20; y++) {
                board[y][x] = 1;
            }
        }
        int score = evaluator.evaluateBoard(board);

        // Expected metrics
        int aggregateHeight = evaluator.getAggregateHeight(board);
        int holes = evaluator.getHoles(board);
        int linesCleared = evaluator.getClearedLines(board);
        int bumpiness = evaluator.getBumpiness(board);
        int expectedScore = (-2 * aggregateHeight) + (2 * linesCleared) - (2 * holes) - (1 * bumpiness);

        assertEquals(expectedScore, score, "Score should match expected value for varied bumpiness");
    }

    @Test
    void testEvaluateBoard_MaxHeightColumns() {
        // Board with all columns filled to the top
        int[][] board = new int[20][10];
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                board[y][x] = 1;
            }
        }
        int score = evaluator.evaluateBoard(board);

        // Expected metrics
        int aggregateHeight = 20 * 10;
        int holes = 0;
        int linesCleared = 20;
        int bumpiness = 0;
        int expectedScore = (-2 * aggregateHeight) + (2 * linesCleared) - (2 * holes) - (1 * bumpiness);

        assertEquals(expectedScore, score, "Score should match expected value for max height columns");
    }

    @Test
    void testEvaluateBoard_OneColumnFilled() {
        // Board with one column filled to the top
        int[][] board = new int[20][10];
        for (int y = 0; y < 20; y++) {
            board[y][0] = 1;
        }
        int score = evaluator.evaluateBoard(board);

        // Expected metrics
        int aggregateHeight = 20;
        int holes = 0;
        int linesCleared = 0; // No full lines
        int bumpiness = evaluator.getBumpiness(board);
        int expectedScore = (-2 * aggregateHeight) + (2 * linesCleared) - (2 * holes) - (1 * bumpiness);

        assertEquals(expectedScore, score, "Score should match expected value for one column filled");
    }

    @Test
    void testEvaluateBoard_NoHolesButHighBumpiness() {
        // Board with no holes but high bumpiness
        int[][] board = new int[20][10];
        int[] heights = {1, 3, 2, 5, 4, 6, 2, 1, 3, 0};
        for (int x = 0; x < heights.length; x++) {
            int height = heights[x];
            for (int y = 20 - height; y < 20; y++) {
                board[y][x] = 1;
            }
        }
        int score = evaluator.evaluateBoard(board);

        // Expected metrics
        int aggregateHeight = evaluator.getAggregateHeight(board);
        int holes = 0;
        int linesCleared = 0;
        int bumpiness = evaluator.getBumpiness(board);
        int expectedScore = (-2 * aggregateHeight) + (2 * linesCleared) - (2 * holes) - (1 * bumpiness);

        assertEquals(expectedScore, score, "Score should match expected value for no holes but high bumpiness");
    }

    @Test
    void testEvaluateBoard_WithMultipleHoles() {
        // Board with multiple holes across columns
        int[][] board = new int[20][10];
        for (int x = 0; x < 5; x++) {
            for (int y = 15; y < 20; y++) {
                if (y != 17) { // Create holes at y=17
                    board[y][x] = 1;
                }
            }
        }
        int score = evaluator.evaluateBoard(board);

        // Expected metrics
        int holes = evaluator.getHoles(board);
        int expectedHoles = 5;
        assertEquals(expectedHoles, holes, "There should be 5 holes");

        int aggregateHeight = evaluator.getAggregateHeight(board);
        int linesCleared = evaluator.getClearedLines(board);
        int bumpiness = evaluator.getBumpiness(board);
        int expectedScore = (-2 * aggregateHeight) + (2 * linesCleared) - (2 * holes) - (1 * bumpiness);

        assertEquals(expectedScore, score, "Score should match expected value for board with multiple holes");
    }

    // Additional tests to cover helper methods indirectly

    @Test
    void testEvaluateBoard_AllMetrics() {
        // Complex board to cover all metrics
        int[][] board = new int[20][10];
        // Create varying heights, holes, and full lines
        for (int x = 0; x < 10; x++) {
            for (int y = 15; y < 20; y++) {
                board[y][x] = 1;
            }
            // Introduce holes
            if (x % 2 == 0) {
                board[16][x] = 0;
            }
        }
        // Clear a line
        for (int x = 0; x < 10; x++) {
            board[19][x] = 1;
        }
        int score = evaluator.evaluateBoard(board);

        // Expected metrics
        int aggregateHeight = evaluator.getAggregateHeight(board);
        int holes = evaluator.getHoles(board);
        int linesCleared = evaluator.getClearedLines(board);
        int bumpiness = evaluator.getBumpiness(board);
        int expectedScore = (-2 * aggregateHeight) + (2 * linesCleared) - (2 * holes) - (1 * bumpiness);

        assertEquals(expectedScore, score, "Score should match expected value for complex board");
    }


    @Test
    void testGetAggregateHeight_AllZero() {
        int[][] board = new int[20][10];
        int aggregateHeight = evaluator.getAggregateHeight(board);
        assertEquals(0, aggregateHeight, "Aggregate height should be zero for an empty board");
    }

    @Test
    void testGetAggregateHeight_VariedHeights() {
        int[][] board = new int[20][10];
        int[] heights = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
        for (int x = 0; x < heights.length; x++) {
            int height = heights[x];
            for (int y = 20 - height; y < 20; y++) {
                board[y][x] = 1;
            }
        }
        int aggregateHeight = evaluator.getAggregateHeight(board);
        int expectedHeight = 0;
        for (int h : heights) {
            expectedHeight += h;
        }
        assertEquals(expectedHeight, aggregateHeight, "Aggregate height should match sum of column heights");
    }

    @Test
    void testGetColumnHeight_NoBlocks() {
        int[][] board = new int[20][10];
        int height = evaluator.getColumnHeight(board, 0);
        assertEquals(0, height, "Column height should be zero when no blocks are present");
    }

    @Test
    void testGetColumnHeight_WithBlocks() {
        int[][] board = new int[20][10];
        for (int y = 10; y < 20; y++) {
            board[y][0] = 1;
        }
        int height = evaluator.getColumnHeight(board, 0);
        assertEquals(10, height, "Column height should be calculated correctly");
    }

    @Test
    void testGetHoles_NoBlocks() {
        int[][] board = new int[20][10];
        int holes = evaluator.getHoles(board);
        assertEquals(0, holes, "Holes should be zero when no blocks are present");
    }

    @Test
    void testGetClearedLines_NoFullLines() {
        int[][] board = new int[20][10];
        int linesCleared = evaluator.getClearedLines(board);
        assertEquals(0, linesCleared, "No lines should be cleared on an empty board");
    }

    @Test
    void testGetClearedLines_FullBoard() {
        int[][] board = new int[20][10];
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                board[y][x] = 1;
            }
        }
        int linesCleared = evaluator.getClearedLines(board);
        assertEquals(20, linesCleared, "All lines should be cleared on a full board");
    }

    @Test
    void testGetBumpiness_FlatSurface() {
        int[][] board = new int[20][10];
        for (int y = 15; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                board[y][x] = 1;
            }
        }
        int bumpiness = evaluator.getBumpiness(board);
        assertEquals(0, bumpiness, "Bumpiness should be zero for a flat surface");
    }

    @Test
    void testGetBumpiness_MaxBumpiness() {
        int[][] board = new int[20][10];
        int[] heights = {20, 0, 20, 0, 20, 0, 20, 0, 20, 0};
        for (int x = 0; x < heights.length; x++) {
            int height = heights[x];
            for (int y = 20 - height; y < 20; y++) {
                board[y][x] = 1;
            }
        }
        int bumpiness = evaluator.getBumpiness(board);
        int expectedBumpiness = 180; // Sum of differences between 20 and 0 across adjacent columns
        assertEquals(expectedBumpiness, bumpiness, "Bumpiness should match expected maximum value");
    }
}
