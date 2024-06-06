package levels;

import main.MyGame;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import static utils.GameConstant.mapConstants.*;
import static utils.LoadSave.pathOfMap;


public class LevelHandler {
    private MyGame myGame;
    private BufferedImage layer1;
    private BufferedImage layer2;
    private int[][] tiles;

    public LevelHandler(MyGame myGame, int[][] tiles) {
        this.myGame = myGame;
        this.layer1 = LoadSave.getMap(pathOfMap(BACKGROUND_0));
        this.layer2 = LoadSave.getMap(pathOfMap(BACKGROUND_1));
        this.tiles = tiles;
    }

    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(layer1, 0, 0, myGame.WIDTH, myGame.HEIGHT, null);
        g.drawImage(layer2, 0, 0, myGame.WIDTH, myGame.HEIGHT,null);
    }


    public void check(int[][] mapData, Graphics g, int xLevelOffset) {
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                if (mapData[i][j] == 1) {
                    g.setColor(Color.RED);
                    g.drawRect(j * 32 - xLevelOffset, i * 32, 32, 32);
                } else {
//                    g.setColor(Color.GREEN);
//                    g.drawRect(j * 32, i * 32, 32, 32);
                    ;
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
}