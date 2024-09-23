package model;

import controller.Controls;
import controller.GameController;
import controller.ServerControlledPlayer;
import controller.facade.GameFacade;
import model.factory.TetrominoFactory;
import view.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameLoop extends JPanel implements Runnable {
    final int FPS = 60;
    private Thread gameThread;
    private boolean running = false;
    private boolean paused = false;
    private GameController gameController;

    // Two facades, one for each player
    private GameFacade player1Facade;
    private GameFacade player2Facade;

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

        player1Facade = new GameFacade(100, 100, 1, gameController.getConfigurations().getPlayer1Type());
        player2Facade = new GameFacade(100, 100, 2, gameController.getConfigurations().getPlayer2Type());

        this.setBackground(Color.WHITE);

        initializeLayout();
    }

    private void initializeLayout() {
        this.removeAll();
        this.setBackground(new Color(238, 238, 238));
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (isTwoPlayerMode) {
            this.setLayout(new GridLayout(1, 2, 10, 0));

            // Wrap player1Panel in a container with padding
            JPanel player1Container = new JPanel(new BorderLayout());
            player1Container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
            player1Panel = new GamePanel(player1Facade);
            player1Container.add(player1Panel, BorderLayout.CENTER);

            // Wrap player2Panel in a container with padding
            JPanel player2Container = new JPanel(new BorderLayout());
            player2Container.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
            player2Panel = new GamePanel(player2Facade);
            player2Container.add(player2Panel, BorderLayout.CENTER);

            // Add both containers to the main panel
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

            // Wrap player1Panel in a container with padding
            JPanel player1Container = new JPanel(new BorderLayout());
            player1Container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            player1Panel = new GamePanel(player1Facade);
            player1Container.add(player1Panel, BorderLayout.CENTER);

            this.add(player1Container, BorderLayout.CENTER);

            if (parentFrame != null) {
                parentFrame.setMinimumSize(null);
                parentFrame.setSize(new Dimension(
                        player1Facade.getGameplay().getWidth() + GamePanel.LEFT_MARGIN + 20,
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
        player1Facade = new GameFacade(gameWidth, gameHeight, 1, gameController.getConfigurations().getPlayer1Type());
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
            player2Facade = new GameFacade(gameWidth, gameHeight, 2, gameController.getConfigurations().getPlayer2Type());
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
            // Stop external players
            if (serverPlayer1 != null) {
                serverPlayer1.stop();
                serverPlayer1 = null;
            }

            if (serverPlayer2 != null) {
                serverPlayer2.stop();
                serverPlayer2 = null;
            }

            running = false;
            paused = false;

            // Interrupt the game thread to exit the wait state if it's paused
            if (gameThread != null) {
                gameThread.interrupt();
            }

            try {
                // Ensure the game thread finishes execution
                gameThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve the interrupt status
                System.out.println("Stopping game");
            } finally {
                gameThread = null; // Clean up the reference to the game thread
            }
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
            player1Facade.updateGame();  // Update Player 1's game
            if (isTwoPlayerMode) {
                player2Facade.updateGame();  // Update Player 2's game in two-player mode
            }
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
        double drawInterval = 1000000000 / FPS;
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
        } else if (gameNumber == 2) {
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
            // Game over is true if both players' games are over in two-player mode
            return player1Facade.isGameOver() && player2Facade.isGameOver();
        }
        // Single-player mode, only check Player 1
        return player1Facade.isGameOver();
    }

    private String getConfigString(Configurations configurations) {
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
}
