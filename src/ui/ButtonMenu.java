package ui;

import gamestates.Gamestate;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.MyGame.MENU_SCALE;
import static utils.GameConstant.MenuConstants.*;
import static utils.GameConstant.MenuConstants.Buttons.*;

// WIDTH, HEIGHT, WIDTH_DEFAULT, HEIGHT_DEFAULT is belong to GameConstant.MenuConstants.Buttons
public class ButtonMenu {
    private int x, y, rowIndex, index;
    private int xOffsetCenter = (int) (WIDTH * MENU_SCALE/ 2); // when initialize, x will be subtracted by this value. Then, x in the parameter is the center of the button
    private Gamestate gamestate;
    private BufferedImage[] images;
    private boolean mouseOver = false, mousePressed = false;
    private Rectangle buttonBounds;

    public ButtonMenu(int x, int y, int rowIndex, Gamestate gamestate) {
        this.x = x;
        this.y = y;
        this.rowIndex = rowIndex;
        this.gamestate = gamestate;
        loadImages();
        initialize();
    }

    public void initialize() {
        buttonBounds = new Rectangle(x - xOffsetCenter, y, WIDTH, HEIGHT);
    }

    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage temp = LoadSave.getMap(LoadSave.pathOfMap(BUTTON_MENU));

        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * WIDTH_DEFAULT, rowIndex * HEIGHT_DEFAULT, WIDTH_DEFAULT, HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(images[index], x - xOffsetCenter, y, (int) (WIDTH * MENU_SCALE), (int) (HEIGHT * MENU_SCALE), null);
//        g.drawImage(img, x - xOffsetCenter, y, WIDTH, HEIGHT, null);
    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }

        if (mousePressed) {
            index = 2;
        }
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void applyGamestate() {
        Gamestate.state = gamestate;
    }

    public Rectangle getButtonBounds() {
        return buttonBounds;
    }

    public void resetMouseBooleans() {
        mouseOver = false;
        mousePressed = false;
    }
}
