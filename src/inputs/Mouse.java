package inputs;
import java.awt.event.*;

import gamestates.Gamestate;
import main.GamePanel;

public class Mouse implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;

    public Mouse(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // These methods return an x and y coordinate of the mouse

    // If the state is MENU, call the mouseClicked method from the Menu class
    // If the state is PLAY, call the mouseClicked method from the Play class
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getMyGame().getMenu().mouseClicked(e);
                break;
            case PLAY:
                gamePanel.getMyGame().getPlay().mouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getMyGame().getMenu().mousePressed(e);
                break;
            case PLAY:
                gamePanel.getMyGame().getPlay().mousePressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getMyGame().getMenu().mouseReleased(e);
                break;
            case PLAY:
                gamePanel.getMyGame().getPlay().mouseReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAY:
                gamePanel.getMyGame().getPlay().mouseDragged(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getMyGame().getMenu().mouseMoved(e);
                break;
            case PLAY:
                gamePanel.getMyGame().getPlay().mouseMoved(e);
                break;
            default:
                break;
        }
    }
}
