package levels;

import main.MyGame;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.GameConstant.mapConstants.*;
import static utils.LoadSave.pathOfMap;

public class Level1 extends LevelHandler {
    private BufferedImage map;

    public Level1(MyGame myGame, int[][] tiles) {
        super(myGame, tiles);
        this.map = LoadSave.getMap(pathOfMap(MY_LEVEL));
    }

    // Draw the level, import image of the level
    @Override
    public void draw(Graphics g, int xLevelOffset) {
        super.draw(g, xLevelOffset);
        this.map = LoadSave.getMap(pathOfMap(MY_LEVEL));
        g.drawImage(map, 0 - xLevelOffset, 0, null);
        check(LevelData.getTilesLevel1(), g, xLevelOffset);
    }
}
