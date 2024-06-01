package inputs;
import main.GamePanel;
import static utils.GameConstant.Directions.*;
import java.awt.event.*;

public class Keyboard implements KeyListener {
    private GamePanel gamePanel;

    public Keyboard(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            gamePanel.getMyGame().getMyPlayer().setUp(true);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            gamePanel.getMyGame().getMyPlayer().setDown(true);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            gamePanel.getMyGame().getMyPlayer().setLeft(true);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            gamePanel.getMyGame().getMyPlayer().setRight(true);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gamePanel.getMyGame().getMyPlayer().setJump(true);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            gamePanel.getMyGame().getMyPlayer().setUp(false);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            gamePanel.getMyGame().getMyPlayer().setDown(false);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            gamePanel.getMyGame().getMyPlayer().setLeft(false);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            gamePanel.getMyGame().getMyPlayer().setRight(false);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gamePanel.getMyGame().getMyPlayer().setJump(false);
        }
    }
}
