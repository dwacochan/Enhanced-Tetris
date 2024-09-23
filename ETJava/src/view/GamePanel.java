package view;

import controller.facade.GameFacade;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameFacade gameFacade;
    public static final int LEFT_MARGIN = 200;
    public static final int TOP_MARGIN = 50;
    public GamePanel(GameFacade gameFacade) {
        this.gameFacade = gameFacade;
        setSize(new Dimension(gameFacade.getGameplay().getWidth() + LEFT_MARGIN, gameFacade.getGameplay().getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        int gameWidth = gameFacade.getGameplay().getWidth();
        int gameHeight = gameFacade.getGameplay().getHeight();

        int panelWidth = getWidth();

        int xTranslation = (panelWidth - gameWidth) / 2 + LEFT_MARGIN - (LEFT_MARGIN / 2);

        g2d.translate(xTranslation, TOP_MARGIN);

        g2d.setColor(new Color(238, 238, 238));
        g2d.fillRect(-LEFT_MARGIN, -2, LEFT_MARGIN, gameHeight + 4);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(-LEFT_MARGIN, -2, LEFT_MARGIN, gameHeight + 4);

        gameFacade.renderGame(g2d);

        g2d.dispose();
    }


}
