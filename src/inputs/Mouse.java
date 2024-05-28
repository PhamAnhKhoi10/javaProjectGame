package inputs;
import java.awt.event.*;
import main.GamePanel;

public class Mouse implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;

    public Mouse(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // These methods return an x and y coordinate of the mouse
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            gamePanel.getMyGame().getMyPlayer().setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            gamePanel.getMyGame().getMyPlayer().setAttackingCombo(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gamePanel.getMyGame().getMyPlayer().setAttacking(false);
        gamePanel.getMyGame().getMyPlayer().setAttackingCombo(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
