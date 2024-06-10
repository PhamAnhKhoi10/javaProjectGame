package ui;

import gamestates.Gamestate;
import gamestates.Play;
import main.MyGame;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.GameConstant.MenuConstants.Buttons.*;
import static utils.GameConstant.mapConstants.*;

public class LoseOverlLay {
    private Play play;
    private BufferedImage image;
    private int imageX, imageY, imageWidth, imageHeight;
    private UrmButton menuButton, restartButton;

    public LoseOverlLay(Play play) {
        createImage();
        this.play = play;
        createButton();
    }

    // Update the overlay
    public void update(){
        menuButton.update();
        restartButton.update();
    }

    // Draw the overlay
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, MyGame.WIDTH, MyGame.HEIGHT);
        g.drawImage(image, imageX, imageY, imageWidth, imageHeight, null);
        menuButton.draw(g);
        restartButton.draw(g);
    }


    private void createButton() {
        int menuX = (int) (305 * MyGame.MENU_SCALE);
        int replayX = (int) (405 * MyGame.MENU_SCALE);
        int y = (int) (170 * MyGame.MENU_SCALE);

        menuButton = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
        restartButton = new UrmButton(replayX, y, URM_SIZE, URM_SIZE, 1);

    }

    private void createImage() {
        image = LoadSave.getMap(LoadSave.pathOfMap(LOSE));
        imageWidth = image.getWidth() * 2;
        imageHeight = image.getHeight() * 2;
        imageX = (MyGame.WIDTH- imageWidth) / 2;
        imageY = (MyGame.HEIGHT - imageHeight) / 2 - 50;
    }

    // ================== KEY AND MOUSE INPUT ==================
    public void keyPressed (KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            play.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }

    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);
        restartButton.setMouseOver(false);

        if (isIn(menuButton, e)) {
            menuButton.setMouseOver(true);
        } else if (isIn(restartButton, e)) {
            restartButton.setMouseOver(true);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menuButton, e)) {
            menuButton.setMousePressed(true);
        } else if (isIn(restartButton, e)) {
            restartButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e){
        if (menuButton.isMousePressed() && isIn(menuButton, e)){
            play.resetAll();
            Gamestate.state = Gamestate.MENU;
        }

        if (restartButton.isMousePressed() && isIn(restartButton, e)){
            play.resetAll();
            Gamestate.state = Gamestate.PLAY;
        }

        menuButton.resetBoolean();
        restartButton.resetBoolean();
    }

    private boolean isIn(UrmButton button, MouseEvent e) {
        return button.getBounds().contains(e.getX(), e.getY());
    }
}
