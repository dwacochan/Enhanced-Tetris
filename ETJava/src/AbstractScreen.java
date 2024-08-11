import java.awt.*;

public abstract class AbstractScreen extends Panel {
    protected Panel mainPanel;
    protected GameController gameController;

    public AbstractScreen(GameController gameController) {
        mainPanel = new Panel();
        this.gameController = gameController;
    }

    public Panel getPanel(){
        return mainPanel;
    }
}
