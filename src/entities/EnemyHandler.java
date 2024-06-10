package entities;

import gamestates.Play;
import utils.LoadSave;

import javax.lang.model.type.ArrayType;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.GameConstant.EnemyConstants.*;
import static utils.LoadSave.*;

public class EnemyHandler {
    private Play play;
    private BufferedImage[] enemyImages;
    private ArrayList<SoulReaper> SRList = new ArrayList<>();

    public EnemyHandler(Play play) {
        this.play = play;
        addEnemies();
        loadEnemyImages();
    }

    public void addEnemies() {
        SRList = LoadSave.getSRList(play.getLevel().getCurrenLevel());
    }

    public void update(int [][] tiles, Player player) {
        for (SoulReaper SR : SRList) {
            if (SR.isActive())
                SR.update(tiles, player);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawSR(g, xLevelOffset);
    }

    public void drawSR(Graphics g, int xLevelOffset) {
        for (SoulReaper SR : SRList) {
            if (SR.isActive()) {
                g.drawImage(enemyImages[SR.getAnimationIndex()], (int) (SR.getHitbox().x - SOUL_REAPER_DRAW_OFFSET_X) - xLevelOffset + SR.flipX(), (int) (SR.getHitbox().y - SOUL_REAPER_DRAW_OFFSET_Y), SOUL_REAPER_WIDTH * SR.flipW(), SOUL_REAPER_HEIGHT, null);
//                g.setColor(Color.GREEN); // Set the color to green before drawing the attack box
//                SR.drawAttackBox(g, xLevelOffset);
//
//                g.setColor(Color.RED);
//                SR.drawHitbox(g, xLevelOffset);
            }
        }
    }

    // Check if the enemy is hit by the player
    public void checkEnemyHit(Rectangle2D.Float playerAttackBox) {
        for (SoulReaper SR : SRList) {
            if (SR.isActive()) {
                if (SR.getHitbox().intersects(playerAttackBox)) {
                    SR.hurt(25);
                }
            }
        }
    }

    public void resetAllEnemies(int [][] tiles) {
        for (SoulReaper SR : SRList) {
            SR.reset(tiles);
        }
    }

    public boolean isAllDefeated() {
        boolean allDefeated = true;
        for (SoulReaper SR : SRList) {
            if (SR.isActive()) {
                allDefeated = false;
                break;
            }
        }
        return allDefeated;
    }

    private void loadEnemyImages() {
        int index = 0;
        enemyImages = new BufferedImage[64];
        BufferedImage SRArray = LoadSave.getSprite(SRList.getFirst(), SOUL_REAPER);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                enemyImages[index] = SRArray.getSubimage(j * SOUL_REAPER_WIDTH_DEFAULT, i * SOUL_REAPER_HEIGHT_DEFAULT, SOUL_REAPER_WIDTH_DEFAULT, SOUL_REAPER_HEIGHT_DEFAULT);
                index++;
            }
        }
    }
}
