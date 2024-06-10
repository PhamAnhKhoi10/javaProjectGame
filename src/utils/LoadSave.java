package utils;

import entities.Entity;
import entities.SoulReaper;
import main.MyGame;

import javax.imageio.ImageIO;
import javax.sql.rowset.BaseRowSet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static utils.GameConstant.EnemyConstants.SOUL_REAPER;
import static utils.GameConstant.LevelConstant.LEVEL_1;
import static utils.GameConstant.MenuConstants.*;
import static utils.GameConstant.Status.*;
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


    public static ArrayList<SoulReaper> getSRList(int currentLevel) {
        if (currentLevel == LEVEL_1) {
            ArrayList<SoulReaper> SRList1 = new ArrayList<>();
            SRList1.add(new SoulReaper(1600, 300));
            SRList1.add(new SoulReaper(600, 300));
            return SRList1;
        } else {
            ArrayList<SoulReaper> SRList2 = new ArrayList<>();
            SRList2.add(new SoulReaper(200, 0));
            SRList2.add(new SoulReaper(700, 500));
            SRList2.add(new SoulReaper(1400, 0));
            SRList2.add(new SoulReaper(2000, 500));
            return SRList2;
        }
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
            case BAR:
                return "src/res/health_power_bar.png";
            case LEVEL_COMPLETED:
                return "src/res/completed_sprite.png";
            case BACKGROUND_3:
                return "src/res/background_glacial_mountains.png";
            case MY_LEVEL_2:
                return "src/res/level2.png";
            case WIN:
                return "src/res/Win.png";
            case LOSE:
                return "src/res/Death.png";
        }
        return null;
    }
}
