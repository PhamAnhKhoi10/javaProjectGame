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

    public LevelHandler(MyGame myGame) {
        this.myGame = myGame;
        this.layer1 = LoadSave.getMap(pathOfMap(BACKGROUND_0));
        this.layer2 = LoadSave.getMap(pathOfMap(BACKGROUND_1));
    }

    public void draw(Graphics g) {
        g.drawImage(layer1, 0, 0, myGame.WIDTH, myGame.HEIGHT, null);
        g.drawImage(layer2, 0, 0, myGame.WIDTH, myGame.HEIGHT,null);
    }

}