package inputs;
import gamestates.Gamestate;
import main.GamePanel;
import static utils.GameConstant.Directions.*;
import java.awt.event.*;

public class Keyboard implements KeyListener {
    private GamePanel gamePanel;

    public Keyboard(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // if the state is MENU, call these method from the Menu class

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getMyGame().getMenu().keyPressed(e);
                break;
            case PLAY:
                gamePanel.getMyGame().getPlay().keyPressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getMyGame().getMenu().keyReleased(e);
                break;
            case PLAY:
                gamePanel.getMyGame().getPlay().keyReleased(e);
                break;
            default:
                break;
        }
    }
}
