package ui;

import main.MyGame;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.GameConstant.MenuConstants.Buttons.*;
import static utils.GameConstant.MenuConstants.*;

public class UrmButton extends ButtonPause {
    private BufferedImage[] images;
    private int rowIndex, index;
    private boolean mouseOver = false, mousePressed = false;

    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = LoadSave.getMap(LoadSave.pathOfMap(URM_BUTTON));
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * URM_SIZE_DEFAULT, rowIndex * URM_SIZE_DEFAULT, URM_SIZE_DEFAULT, URM_SIZE_DEFAULT);
        }
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

    public void resetBoolean() {
        mouseOver = false;
        mousePressed = false;
    }

    // =================== GETTERS AND SETTERS ===================


    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void draw(Graphics g) {
        g.drawImage(images[index], x, y, (int) (URM_SIZE * MyGame.MENU_SCALE), (int) (URM_SIZE * MyGame.MENU_SCALE), null);
    }
}
