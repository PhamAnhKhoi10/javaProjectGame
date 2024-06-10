package ui;

import gamestates.Gamestate;
import gamestates.Play;
import main.MyGame;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.GameConstant.LevelConstant.LEVEL_1;
import static utils.GameConstant.LevelConstant.LEVEL_2;
import static utils.GameConstant.MenuConstants.Buttons.*;
import static utils.GameConstant.MenuConstants.LEVEL_COMPLETED;

public class LevelCompletedOverlay {
    private Play play;
    private UrmButton menuButton, nextButton;
    private BufferedImage backgroundImage;
    private int backgroundX, backgroundY, width, height;


    public LevelCompletedOverlay(Play play) {
        this.play = play;
        loadImages();
        createButton();
    }

    private void createButton() {
        int menuX = (int) (305 * MyGame.MENU_SCALE);
        int nextX = (int) (405 * MyGame.MENU_SCALE);
        int y = (int) (200 * MyGame.MENU_SCALE);

        nextButton = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
        menuButton = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void loadImages() {
        backgroundImage = LoadSave.getMap(LoadSave.pathOfMap(LEVEL_COMPLETED));
        width = (int) (backgroundImage.getWidth() * MyGame.MENU_SCALE);
        height = (int) (backgroundImage.getHeight() * MyGame.MENU_SCALE);
        backgroundX = (MyGame.WIDTH - width) / 2;
        backgroundY = (int) (75 * MyGame.MENU_SCALE);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, backgroundX, backgroundY, width, height, null);
        menuButton.draw(g);
        nextButton.draw(g);
    }

    private boolean isIn(UrmButton button, MouseEvent e) {
        return button.getBounds().contains(e.getX(), e.getY());
    }

    public void update() {
        menuButton.update();
        nextButton.update();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menuButton, e)) {
            menuButton.setMousePressed(true);
        } else if (isIn(nextButton, e)) {
            nextButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menuButton, e)) {
            Gamestate.state = Gamestate.MENU;
        } else if (isIn(nextButton, e)){
            if (nextButton.isMousePressed()) {
                if (play.getLevel().getCurrenLevel() == LEVEL_1) {
                    play.getEnemyHandler().resetAllEnemies(play.getLevel().getTiles());
                    play.setLevelCompleted(false);
                    play.getLevel().setLevel(LEVEL_2);
                    play.getEnemyHandler().addEnemies();
                    play.getMyPlayer().reset(play.getLevel().getTiles());
                } else if (play.getLevel().getCurrenLevel() == LEVEL_2) {
                    System.exit(0);
                }
            }
        }
        menuButton.resetBoolean();
        nextButton.resetBoolean();
    }
        public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);
        nextButton.setMouseOver(false);

        if (isIn(menuButton, e)) {
            menuButton.setMouseOver(true);
        } else if (isIn(nextButton, e)) {
            nextButton.setMouseOver(true);
        }
    }
}
