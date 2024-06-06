package ui;
import main.MyGame;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.GameConstant.MenuConstants.Buttons.*;
import static utils.GameConstant.MenuConstants.VOLUME_BUTTON;

public class VolumeButton extends ButtonPause {
    private BufferedImage[] images;
    private BufferedImage slider;
    private boolean mouseOver = false, mousePressed = false;
    private int index, buttonX, minX, maxX;

    public VolumeButton(int x, int y, int width, int height) {
        super(x + width / 2, y, VOLUME_WIDTH, height);
        getBounds().x -= VOLUME_WIDTH;
        buttonX = x + width / 2;
        this.x = x;
        this.width = width;
        minX = x + VOLUME_WIDTH;
        maxX = x + (int) (width* MyGame.MENU_SCALE) - VOLUME_WIDTH;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = LoadSave.getMap(LoadSave.pathOfMap(VOLUME_BUTTON));
        images = new BufferedImage[3];

        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * VOLUME_WIDTH_DEFAULT, 0, VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
        }

        slider = temp.getSubimage(3 * VOLUME_WIDTH_DEFAULT, 0, SLIDER_DEFAULT_WIDTH, VOLUME_HEIGHT_DEFAULT);
    }

    public void updateX(int x) {
        if (x < minX) {
            buttonX = minX;
        } else if (x > maxX) {
            buttonX = maxX;
        } else {
            buttonX = x;
        }
        getBounds().x = buttonX - VOLUME_WIDTH;
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
        g.drawImage(slider, x, y, (int) (width * MyGame.MENU_SCALE), (int) (height * MyGame.MENU_SCALE), null);
        g.drawImage(images[index], buttonX - VOLUME_WIDTH, y, (int) (VOLUME_WIDTH * MyGame.MENU_SCALE), (int) (height * MyGame.MENU_SCALE), null);
    }
}
