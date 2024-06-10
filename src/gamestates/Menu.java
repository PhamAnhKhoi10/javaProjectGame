package gamestates;

import main.MyGame;
import ui.ButtonMenu;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.GameConstant.MenuConstants.*;

public class Menu extends State implements Statemethod {
    private ButtonMenu[] buttons = new ButtonMenu[3];
    private BufferedImage menuBackground, gameTitle, wallpaper;
    private int menuWidth, menuHeight, menuX, menuY;

    public Menu(MyGame myGame) {
        super(myGame);
        loadBackground();
        loadButtons();
    }

    // Load images for the menu
    public void loadBackground() {
        menuBackground = LoadSave.getMap(LoadSave.pathOfMap(MENU_BACKGROUND));
        gameTitle = LoadSave.getMap(LoadSave.pathOfMap(GAME_TITLE));
        wallpaper = LoadSave.getMap(LoadSave.pathOfMap(WALLPAPER));

        menuWidth = menuBackground.getWidth();
        menuHeight = menuBackground.getHeight();
        menuX = (int) (MyGame.WIDTH - menuWidth * MyGame.MENU_SCALE ) / 2;
        menuY = (int) (45 * MyGame.MENU_SCALE);
    }

    // initialize the menu
    public void loadButtons() {
        buttons[0] = new ButtonMenu(MyGame.WIDTH / 2, (int) (150 * MyGame.MENU_SCALE), 0, Gamestate.PLAY);
        buttons[1] = new ButtonMenu(MyGame.WIDTH / 2, (int) (220 * MyGame.MENU_SCALE), 1, Gamestate.OPTION);
        buttons[2] = new ButtonMenu(MyGame.WIDTH / 2, (int) (290 * MyGame.MENU_SCALE), 2, Gamestate.EXIT);
    }

    // Update the menu
    @Override
    public void update() {
        for (ButtonMenu button : buttons) {
            button.update();
        }
    }

    // Draw the menu
    @Override
    public void draw(Graphics g) {
        g.drawImage(wallpaper, 0, 0, MyGame.WIDTH, MyGame.HEIGHT, null);
        g.drawImage(menuBackground, menuX, menuY, (int) (menuWidth * MyGame.MENU_SCALE), (int) (menuHeight * MyGame.MENU_SCALE), null);
        g.drawImage(gameTitle, (int) (MyGame.WIDTH - gameTitle.getWidth() * MyGame.SCALE) / 2, (int) (150 * MyGame.SCALE), (int) (gameTitle.getWidth() * MyGame.SCALE), (int) (gameTitle.getHeight() * MyGame.SCALE), null);

        for (ButtonMenu button : buttons) {
            button.draw(g);
        }
    }


    // ======================================================================================================================================
    // Getting the keyboard and mouse event whenever the state is MENU
    @Override
    public void mouseClicked(MouseEvent e) {
        for (ButtonMenu buttonMenu : buttons) {
            if(isIn(e, buttonMenu)) {
                buttonMenu.applyGamestate();
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (ButtonMenu buttonMenu : buttons) {
            if(isIn(e, buttonMenu)) {
                buttonMenu.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (ButtonMenu buttonMenu : buttons) {
            if(isIn(e, buttonMenu)) {
                if (buttonMenu.isMousePressed()) {
                    buttonMenu.applyGamestate();
                    break;
                }
            }
        }
        resetButtons();
    }

    public void resetButtons() {
        for (ButtonMenu buttonMenu : buttons) {
            buttonMenu.resetMouseBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (ButtonMenu buttonMenu: buttons) {
            buttonMenu.setMouseOver(false);
        }

        for (ButtonMenu buttonMenu : buttons) {
            if(isIn(e, buttonMenu)) {
                buttonMenu.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Gamestate.state = Gamestate.PLAY;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
