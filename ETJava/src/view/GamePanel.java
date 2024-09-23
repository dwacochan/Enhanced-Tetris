package view;

import controller.facade.GameFacade;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameFacade gameFacade;
    private static final int LEFT_MARGIN = 0;  // Constant 200 pixels of space on the left

    public GamePanel(GameFacade gameFacade) {
        this.gameFacade = gameFacade;
        setSize(new Dimension(gameFacade.getGameplay().getWidth() + LEFT_MARGIN, gameFacade.getGameplay().getHeight()));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Create a new Graphics2D object for rendering the game
        Graphics2D g2d = (Graphics2D) g.create();

        // Calculate the width and height of the game area
        int gameWidth = gameFacade.getGameplay().getWidth();
        int gameHeight = gameFacade.getGameplay().getHeight();

        // Get the width and height of the entire panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        System.out.println(this.getSize());
        // Calculate the translation offsets
        // Add the LEFT_MARGIN to xTranslation to ensure a 200px margin on the left
        int xTranslation = (panelWidth - gameWidth) / 2 + LEFT_MARGIN - (LEFT_MARGIN / 2);
        //int yTranslation = (panelHeight - gameHeight) / 2;

        // Apply translation to center the game with the left margin
        g2d.translate(xTranslation, 50);

        // Render the game
        gameFacade.renderGame(g2d);

        // Dispose the game graphics
        g2d.dispose();
    }
}
