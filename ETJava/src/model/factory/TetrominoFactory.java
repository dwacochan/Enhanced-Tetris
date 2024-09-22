package model.factory;

import model.Tetromino;
import model.tetrominos.*;
import controller.Gameplay;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class TetrominoFactory {
    private static Tetromino nextTetromino = generateRandomTetromino();

    private static final Map<Gameplay, Queue<Integer>> gameTetrominoQueues = new HashMap<>();
    private static final List<Integer> initialTetrominoSequence = new LinkedList<>();
    private static final Random random = new Random();
    private static final int SEQUENCE_SIZE = 1000;

    static {
        generateTetrominoSequence();
    }

    private static void generateTetrominoSequence() {
        initialTetrominoSequence.clear();
        for (int i = 0; i < SEQUENCE_SIZE; i++) {
            initialTetrominoSequence.add(generateTetrominoType());
        }
    }

    private static int generateTetrominoType() {
        return random.nextInt(7);
    }

    private static Tetromino createTetrominoFromType(int type) {
        return switch (type) {
            case 0 -> new I();
            case 1 -> new O();
            case 2 -> new T();
            case 3 -> new S();
            case 4 -> new Z();
            case 5 -> new J();
            case 6 -> new L();
            default -> null;  // This should never be hit, as the random is constrained
        };
    }

    private static void initializeTetrominoQueueForGame(Gameplay gameplay) {
        Queue<Integer> tetrominoQueue = new LinkedList<>(initialTetrominoSequence);
        gameTetrominoQueues.put(gameplay, tetrominoQueue);
    }

    public static synchronized Tetromino getNextTetromino(Gameplay gameplay) {
        if (!gameTetrominoQueues.containsKey(gameplay)) {
            initializeTetrominoQueueForGame(gameplay);
        }
        Queue<Integer> queue = gameTetrominoQueues.get(gameplay);
        if (queue.isEmpty()) {
            regenerateTetrominoSequenceForAllGames();
        }
        int tetrominoType = queue.poll();
        return createTetrominoFromType(tetrominoType);
    }

    private static void regenerateTetrominoSequenceForAllGames() {
        generateTetrominoSequence();
        gameTetrominoQueues.forEach((gameplay, queue) -> queue.addAll(initialTetrominoSequence));
    }


    public static synchronized void reset() {
        gameTetrominoQueues.clear();
        generateTetrominoSequence();
    }
}
