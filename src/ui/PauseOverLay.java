package ui;

import gamestates.Gamestate;
import gamestates.Play;
import main.MyGame;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.GameConstant.MenuConstants.Buttons.*;
import static utils.GameConstant.MenuConstants.PAUSE_BACKGROUND;

public class PauseOverLay {
    private Play play;
    private int backgroundX, backgroundY, width, height;
    private BufferedImage backgroundImage;
    private SoundButton musicButton, sfxButton;
    private UrmButton menuButton, replayButton, resumeButton;
    private VolumeButton volumeButton;

    public PauseOverLay(Play play) {
        loadImages();
        createSoundButton();
        createUrmButton();
        createVolumeButton();
        this.play = play;
    }

    // initialize the volume button
    public void createVolumeButton() {
        int volumeX = (int) (277 * MyGame.MENU_SCALE);
        int volumeY = (int) (258 * MyGame.MENU_SCALE);
        volumeButton = new VolumeButton(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    // initialize the urm buttons
    public void createUrmButton() {
        int menuX = (int) (283 * MyGame.MENU_SCALE);
        int replayX = (int) (357 * MyGame.MENU_SCALE);
        int resumeX = (int) (432 * MyGame.MENU_SCALE);
        int urmY = (int) (307 * MyGame.MENU_SCALE);

        menuButton = new UrmButton(menuX, urmY, URM_SIZE, URM_SIZE, 2);
        replayButton = new UrmButton(replayX, urmY, URM_SIZE, URM_SIZE, 1);
        resumeButton = new UrmButton(resumeX, urmY, URM_SIZE, URM_SIZE, 0);
    }

    // initialize the sound buttons
    public void createSoundButton() {
        int soundX = (int) (420 * MyGame.MENU_SCALE);
        int musicY = (int) (115 * MyGame.MENU_SCALE);
        int sfxY = (int) (165 * MyGame.MENU_SCALE);

        musicButton = new SoundButton(soundX, musicY,SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }


    public void loadImages() {
        backgroundImage = LoadSave.getMap(LoadSave.pathOfMap(PAUSE_BACKGROUND));
        width =  (int) (backgroundImage.getWidth() * MyGame.MENU_SCALE);
        height = (int) (backgroundImage.getHeight() * MyGame.MENU_SCALE);

        backgroundX =  (MyGame.WIDTH - width) / 2;
        backgroundY = 0;
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, backgroundX, backgroundY, width, height + 10, null);
        musicButton.draw(g);
        sfxButton.draw(g);
        menuButton.draw(g);
        replayButton.draw(g);
        resumeButton.draw(g);
        volumeButton.draw(g);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();

        menuButton.update();
        replayButton.update();
        resumeButton.update();
        volumeButton.update();
    }

    // ======================================================================================================================================
    // mouse event methods
    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed()) {
            volumeButton.updateX(e.getX());
        }
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(e, menuButton) ) {
            menuButton.setMousePressed(true);
        } else if (isIn(e, replayButton)) {
            replayButton.setMousePressed(true);
        } else if (isIn(e, resumeButton)) {
            resumeButton.setMousePressed(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }
    }


    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        } else if (isIn(e, menuButton)) {
            if (menuButton.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
                play.changePause();
            }
        } else if (isIn(e, replayButton)) {
            if (replayButton.isMousePressed()) {
                ;
            }
        } else if (isIn(e, resumeButton)) {
            if (resumeButton.isMousePressed()) {
                play.changePause();
            }
        }

        // Reset the mousePressed property of both buttons
        musicButton.setMousePressed(false);
        sfxButton.setMousePressed(false);
        menuButton.resetBoolean();
        replayButton.resetBoolean();
        resumeButton.resetBoolean();
        volumeButton.resetBoolean();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);
        resumeButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isIn(e, menuButton) ) {
            menuButton.setMouseOver(true);
        } else if (isIn(e, replayButton)) {
            replayButton.setMouseOver(true);
        } else if (isIn(e, resumeButton)) {
            resumeButton.setMouseOver(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }
    // ======================================================================================================================================
    // Check if the mouse is in the button
    private boolean isIn(MouseEvent e, ButtonPause button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }
}
