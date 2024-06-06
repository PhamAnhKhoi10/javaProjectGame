package gamestates;

import main.MyGame;
import ui.ButtonMenu;

import java.awt.event.MouseEvent;

// Super class of Menu state and Play state
public class State {
    private MyGame myGame;

    public State(MyGame myGame) {
        this.myGame = myGame;
    }

    public boolean isIn(MouseEvent e, ButtonMenu button) {
        return button.getButtonBounds().contains(e.getX(), e.getY());
    }

    public MyGame getMyGame() {
        return myGame;
    }
}
