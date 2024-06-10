package levels;

import main.MyGame;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.GameConstant.LevelConstant.*;
import static utils.GameConstant.mapConstants.*;
import static utils.LoadSave.pathOfMap;


public class LevelHandler {
    private MyGame myGame;
    private BufferedImage layer1;
    private BufferedImage layer2;
    private int[][] tiles;
    private int currenLevel;
    private BufferedImage map;

    public LevelHandler(MyGame myGame, int level) {
        this.myGame = myGame;
        setLevel(level);
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawLevel(currenLevel, g, xLevelOffset);
    }


    public void check(int[][] mapData, Graphics g, int xLevelOffset) {
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                if (mapData[i][j] == 1) {
                    g.setColor(Color.RED);
                    g.drawRect(j * 32 - xLevelOffset, i * 32, 32, 32);
                }
            }
        }
    }

    public MyGame getMyGame() {
        return myGame;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public void setLevel(int level) {
        if (level == LEVEL_1) {
            this.currenLevel = LEVEL_1;
            this.layer1 = LoadSave.getMap(pathOfMap(BACKGROUND_0));
            this.layer2 = LoadSave.getMap(pathOfMap(BACKGROUND_1));
            this.map = LoadSave.getMap(pathOfMap(MY_LEVEL));
            this.tiles = LevelData.getTilesLevel1();
        } else if (level == LEVEL_2) {
            this.currenLevel = LEVEL_2;
            this.layer1 = LoadSave.getMap(pathOfMap(BACKGROUND_3));
            this.map = LoadSave.getMap(pathOfMap(MY_LEVEL_2));
            this.tiles = LevelData.getTilesLevel2();
        }
    }

    private void drawLevel(int level, Graphics g, int xLevelOffset) {
        if (level == LEVEL_1) {
            g.drawImage(layer1, 0, 0, myGame.WIDTH, myGame.HEIGHT, null);
            g.drawImage(layer2, 0, 0, myGame.WIDTH, myGame.HEIGHT,null);
            g.drawImage(map, 0 - xLevelOffset, 0, null);
            check(tiles, g, xLevelOffset);
        } else if (level == LEVEL_2) {
            g.drawImage(layer1, 0, 0, myGame.WIDTH, myGame.HEIGHT, null);
            g.drawImage(map, 0 - xLevelOffset, 0, null);
            check(tiles, g, xLevelOffset);
        }
    }

    public int getCurrenLevel() {
        return currenLevel;
    }
}