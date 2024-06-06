package ui;

import main.MyGame;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.GameConstant.MenuConstants.Buttons.SOUND_SIZE_DEFAULT;
import static utils.GameConstant.MenuConstants.SOUND_BUTTON;

public class SoundButton extends ButtonPause {
    private BufferedImage[][] soundImage;
    private boolean mouseOver = false, mousePressed = false;
    private boolean muted = false;
    private int rowIndex, columnIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImage();
    }

    private void loadSoundImage() {
        BufferedImage temp = LoadSave.getMap(LoadSave.pathOfMap(SOUND_BUTTON));
        soundImage = new BufferedImage[2][3];

        for (int i = 0; i < soundImage.length; i++) {
            for (int j = 0; j < soundImage[i].length; j++) {
                soundImage[i][j] = temp.getSubimage(j * SOUND_SIZE_DEFAULT, i * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }
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

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
// ===================================================================
    public void update() {
        // the icon of mute is in the second row
        // the icon of sound is in the first row
        if (muted) {
           rowIndex = 1;
        } else {
           rowIndex = 0;
        }

        // If the mouse point to the button, then change the animation
        // else, reset to the default animation
        columnIndex = 0;
        if (mouseOver) {
           columnIndex = 1;
        }

        // If the mouse is pressed, then change the animation
        if (mousePressed) {
           columnIndex = 2;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(soundImage[rowIndex][columnIndex], x, y, (int) (width * MyGame.MENU_SCALE), (int) (height * MyGame.MENU_SCALE), null);
    }

}
