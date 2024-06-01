package main;

import javax.swing.*;
import java.awt.*;
import inputs.*;
import static main.MyGame.WIDTH;
import static main.MyGame.HEIGHT;

public class GamePanel extends JPanel {
    private Mouse mouseInput;
    private MyGame myGame;

    public GamePanel(MyGame myGame) {
        addKeyListener(new Keyboard(this));
        this.myGame = myGame;
        mouseInput = new Mouse(this);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        setPanelSize();

    }

    public MyGame getMyGame() {
        return myGame;
    }

    // Draw everything of the game to the panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        myGame.render(g);
    }

    // Set the fixed size of the panel
    private void setPanelSize() {
        Dimension size = new Dimension(MyGame.WIDTH,MyGame.HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

}
