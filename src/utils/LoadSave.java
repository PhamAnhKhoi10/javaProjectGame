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
                return "./res/Background_0.png";
            case BACKGROUND_1:
                return "./res/Background_1.png";
            case MY_LEVEL:
                return "./res/Level_1.png";
        }
        return null;
    }
}
