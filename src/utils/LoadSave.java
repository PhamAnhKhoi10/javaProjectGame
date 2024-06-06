package utils;

import entities.Entity;
import main.MyGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static utils.GameConstant.MenuConstants.*;
import static utils.GameConstant.mapConstants.*;

public class LoadSave {

    public static BufferedImage getMap(String path) {
        BufferedImage image = null;
        InputStream is = null;
        try {
            is = new FileInputStream(path);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            image = ImageIO.read(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static BufferedImage getSprite(Entity entity, int entityAction) {
        BufferedImage image = null;
        InputStream is = null;
        String path = entity.pathOfImages(entityAction);
        try {
            is = new FileInputStream(path);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            image = ImageIO.read(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
    public static String pathOfMap(int type) {

        switch (type) {
            case BACKGROUND_0:
                return "src/res/Background_0.png";
            case BACKGROUND_1:
                return "src/res/Background_1.png";
            case MY_LEVEL:
                return "src/res/level1_long.png";
            case BUTTON_MENU:
                return "src/res/button_atlas.png";
            case MENU_BACKGROUND:
                return "src/res/Button.png";
            case PAUSE_BACKGROUND:
                return "src/res/pause_menu.png";
            case SOUND_BUTTON:
                return "src/res/sound_button.png";
            case URM_BUTTON:
                return "src/res/urm_buttons.png";
            case VOLUME_BUTTON:
                return "src/res/volume_buttons.png";
            case GAME_TITLE:
                return "src/res/Title.png";
            case WALLPAPER:
                return "src/res/aaaa.jpg";
            case ENEMY:
                return "src/res/enemy.png";
        }
        return null;
    }
}
