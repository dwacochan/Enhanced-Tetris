package controller;

import controller.facade.GameFacade;
import model.Block;
import model.Configurations;
import model.PlayerType;
import model.factory.TetrominoFactory;
import view.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameLoop extends JPanel implements Runnable {
    final int FPS = 60;

    private double drawInterval;
    private Thread gameThread;
    private Thread player1Thread;
    private Thread player2Thread;
    private boolean running = false;



    private boolean paused = false;
    private GameController gameController;

    // Two facades, one for each player
    private GameFacade player1Facade;
    private GameFacade player2Facade = null;

    private GamePanel player1Panel;
    private GamePanel player2Panel;

    // Toggle for two-player mode
    private boolean isTwoPlayerMode;

    // External players
    private ServerControlledPlayer serverPlayer1;
    private ServerControlledPlayer serverPlayer2;

    public GameLoop(boolean isTwoPlayerMode, PlayerType player1Type, PlayerType player2Type, GameController gameController) {
        this.gameController = gameController;
        this.isTwoPlayerMode = isTwoPlayerMode;

        player1Facade = new GameFacade(100, 100, 1, gameController.getConfigurations().getPlayer1Type(), gameController.getConfigurations().getGameLevel());
        player2Facade = new GameFacade(100, 100, 2, gameController.getConfigurations().getPlayer2Type(), gameController.getConfigurations().getGameLevel());

        this.setBackground(Color.WHITE);

        initializeLayout();
    }

    private void initializeLayout() {
        this.removeAll();
        this.setOpaque(false);
        this.setBackground(new Color(238, 238, 238));
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (isTwoPlayerMode) {
            this.setLayout(new GridLayout(1, 2, 10, 0));

            JPanel player1Container = new JPanel(new BorderLayout());
            player1Container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
            player1Container.setOpaque(false);
            player1Panel = new GamePanel(player1Facade);
            player1Container.add(player1Panel, BorderLayout.CENTER);

            JPanel player2Container = new JPanel(new BorderLayout());
            player2Container.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
            player2Container.setOpaque(false);
            player2Panel = new GamePanel(player2Facade);
            player2Container.add(player2Panel, BorderLayout.CENTER);

            this.add(player1Container);
            this.add(player2Container);

            if (parentFrame != null) {
                parentFrame.setMinimumSize(null);
                parentFrame.setSize(new Dimension(
                        (player1Facade.getGameplay().getWidth() + GamePanel.LEFT_MARGIN + 20) * 2 + 30,
                        player1Facade.getGameplay().getHeight() + 300
                ));
                parentFrame.setMinimumSize(parentFrame.getSize());
                parentFrame.setLocationRelativeTo(null);
            }
        } else {
            this.setLayout(new BorderLayout());

            JPanel player1Container = new JPanel(new BorderLayout());
            player1Container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            player1Container.setOpaque(false);
            player1Panel = new GamePanel(player1Facade);
            player1Container.add(player1Panel, BorderLayout.CENTER);

            this.add(player1Container, BorderLayout.CENTER);

            if (parentFrame != null) {
                parentFrame.setMinimumSize(null);
                parentFrame.setSize(new Dimension(
                        player1Facade.getGameplay().getWidth() + GamePanel.LEFT_MARGIN + 20 + 30,
                        player1Facade.getGameplay().getHeight() + 300
                ));
                parentFrame.setMinimumSize(parentFrame.getSize());
                parentFrame.setLocationRelativeTo(null);
            }

        }

        this.revalidate();
        this.repaint();
    }

    public void startGame() {
        int gameWidth = gameController.getConfigurations().getFieldWidth() * Block.SIZE;
        int gameHeight = gameController.getConfigurations().getFieldHeight() * Block.SIZE;
        this.isTwoPlayerMode = gameController.getConfigurations().isExtendModeOn();
        player1Facade = new GameFacade(gameWidth, gameHeight, 1, gameController.getConfigurations().getPlayer1Type(), gameController.getConfigurations().getGameLevel());
        player1Panel = new GamePanel(player1Facade);

        if (gameController.getConfigurations().getPlayer1Type() == PlayerType.SERVER) {
            serverPlayer1 = new ServerControlledPlayer(1);
        } else {
            serverPlayer1 = null;
        }

        if (isTwoPlayerMode) {
            if (gameController.getConfigurations().getPlayer2Type() == PlayerType.SERVER) {
                serverPlayer2 = new ServerControlledPlayer(2);
            } else {
                serverPlayer2 = null;
            }
            player2Facade = new GameFacade(gameWidth, gameHeight, 2, gameController.getConfigurations().getPlayer2Type(), gameController.getConfigurations().getGameLevel());
            player2Panel = new GamePanel(player2Facade);
        } else {
            player2Facade = null;
            player2Panel = null;
            serverPlayer2 = null;
        }

        initializeLayout();

        if (gameThread == null || !running) {
            TetrominoFactory.reset();
            gameThread = new Thread(this);
            gameThread.start();
            running = true;
            paused = false;

            // Start separate threads for each player facade
            startFacadeThreads();
        }
    }


    Runnable createFacadeThread(GameFacade facade, String playerLabel) {
        return () -> {
            double drawInterval = (double) 1000000000 / (FPS * facade.getLevel());

            while (running) {
                if (!paused) {
                    // Check if the level changed and update the interval
                    int currentLevel = facade.getLevel();
                    drawInterval = (double) 1000000000 / (FPS * currentLevel);

                    System.out.println(playerLabel + " Level: " + currentLevel);
                    facade.updateGame();
                }

                try {
                    long sleepTimeMillis = (long) (drawInterval / 1_000_000);
                    Thread.sleep(sleepTimeMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
    }
    private void startFacadeThreads() {

        // Start thread for Player 1
        player1Thread = new Thread(createFacadeThread(player1Facade, "Player 1"));
        player1Thread.start();

        // Start thread for Player 2 if two-player mode is enabled
        if (isTwoPlayerMode) {
            player2Thread = new Thread(createFacadeThread(player2Facade, "Player 2"));
            player2Thread.start();
        }
    }




    public void pauseGame() {
        if (running && !paused) {
            paused = true;
        }
    }

    public void resumeGame() {
        if (running && paused) {
            paused = false;
        }
    }

    public void stopGame() {
        if (running) {
            stopExternalPlayers();

            running = false;
            paused = false;

            stopAndCleanupThread(gameThread);
            stopAndCleanupThread(player1Thread);
            if (isTwoPlayerMode) {
                stopAndCleanupThread(player2Thread);
            }
        }
    }

    private void stopExternalPlayers() {
        if (serverPlayer1 != null) {
            serverPlayer1.stop();
            serverPlayer1 = null;
        }

        if (serverPlayer2 != null) {
            serverPlayer2.stop();
            serverPlayer2 = null;
        }
    }

    private void stopAndCleanupThread(Thread thread) {
        if (thread != null) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Stopping game");
            }
            thread = null;
        }
    }


    public void resetGame() {
        stopGame();
        player1Facade.startNewGame();
        if (isTwoPlayerMode) {
            player2Facade.startNewGame();
        }
    }

    private void update() {
        if (!paused && !Controls.pause) {
            drawInterval = (double) 1000000000 / (FPS);
        }
        if (isGameOver()) {
            GameController.getInstance().setNewScore(player1Facade.getScore(), player1Facade.getGameNumber(), getConfigString(gameController.getConfigurations()));
            if (isTwoPlayerMode) {
                GameController.getInstance().setNewScore(player2Facade.getScore(), player2Facade.getGameNumber(), getConfigString(gameController.getConfigurations()));
            }
            stopGame();
        }
    }

    @Override
    public void run() {
        drawInterval = (double) 1000000000 / (FPS * GameController.getInstance().getConfigurations().getGameLevel());
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void adjustGameDimensions(int width, int height) {
        player1Facade.setGameDimensions(width, height);
        if (isTwoPlayerMode) {
            player2Facade.setGameDimensions(width, height);
        }
    }

    public GameFacade getPlayerFacade(int gameNumber) {
        if (gameNumber == 1) {
            return player1Facade;
        } else if (gameNumber == 2 && isTwoPlayerMode) {
            return player2Facade;
        }
        return null;
    }

    public ServerControlledPlayer getServerControlledPlayer(int gameNumber) {
        if (gameNumber == 1) {
            return serverPlayer1;
        } else if (gameNumber == 2) {
            return serverPlayer2;
        }
        return null;
    }

    public boolean isGameOver() {
        if (isTwoPlayerMode) {
            return player1Facade.isGameOver() && player2Facade.isGameOver();
        }
        return player1Facade.isGameOver();
    }


    private static String getConfigString(Configurations configurations) {
        int fieldWidth = configurations.getFieldWidth();
        int fieldHeight = configurations.getFieldHeight();
        int level = configurations.getGameLevel();

        String strField = fieldWidth + "x" + fieldHeight + "(" + level + ")";
        String strMode;
        if (configurations.isExtendModeOn()) {
            if (configurations.isSamePlayerType()) {
                strMode = configurations.getPlayer1Type().toString() + " Double";
            } else {
                strMode = configurations.getPlayer1Type().toString() + " VS " + configurations.getPlayer2Type().toString();
            }
        } else {
            strMode = configurations.getPlayer1Type().toString();
        }

        return strField + " " + strMode;
    }


    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }
}
