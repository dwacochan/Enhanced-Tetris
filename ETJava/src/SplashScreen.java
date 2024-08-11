import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.InputStream;

public class SplashScreen extends AbstractScreen {

    private BufferedImage splashImage;

    public SplashScreen(GameController gameController) {
        super(gameController);

        // Load the PNG image
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("resources/splashScreenOption2.png");
            if (inputStream == null) {
                System.out.println("Image not found!");
            } else {
                splashImage = ImageIO.read(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(new ImagePanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    private class ImagePanel extends Panel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (splashImage != null) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int imageWidth = splashImage.getWidth();
                int imageHeight = splashImage.getHeight();

                int x = (panelWidth - imageWidth) / 2;
                int y = (panelHeight - imageHeight) / 2;

                g.drawImage(splashImage, x, y, this);
            }
        }
    }
}
