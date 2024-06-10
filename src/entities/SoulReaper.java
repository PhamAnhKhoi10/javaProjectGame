package entities;

import main.MyGame;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.GameConstant.Directions.*;
import static utils.GameConstant.EnemyConstants.*;

public class SoulReaper extends Enemy {
    // Attack range
    private int attackBoxOffsetX;

    public SoulReaper(int x, int y) {
        super(x, y, SOUL_REAPER_WIDTH, SOUL_REAPER_HEIGHT, SOUL_REAPER);
        initHibox((int) (55 * MyGame.PLAYER_SCALE), (int) (65 * MyGame.PLAYER_SCALE));
        initAttackBox();
    }

    // =========================================ATTACK BOX=======================================
    public void initAttackBox() {
        attackBoxOffsetX = (int) (30 * MyGame.PLAYER_SCALE);
        attackBox = new Rectangle2D.Float(getX(), getY(), (int) (125 * MyGame.PLAYER_SCALE), (int) (65 * MyGame.PLAYER_SCALE));
    }

    public void updateAttackBox() {
        if (walkDirection == LEFT) {
            attackBox.x = getHitbox().x  - attackBox.width + getHitbox().width;
        } else if (walkDirection == RIGHT) {
            attackBox.x = getHitbox().x;
        }

        attackBox.y = getHitbox().y;
    }

    // =========================================UPDATING=======================================

    public void update(int [][] tiles, Player player) {
        updateAnimationTick();
        updateMovement(tiles, player);
        updateAttackBox();
    }

    // Movement of the enemy
    public void updateMovement(int tiles[][], Player player) {
        firstUpdateCheck(tiles);
        // If the enemy is in the air

        if (inAir) {
            updateInAir(tiles);
        } else {
            switch (state) {
                case IDLE:
                    newEnemyState(WALK);
                    updateAnimationFrames();
                    break;
                case WALK:
                    move(tiles);

                    if (canSeePlayer(tiles, player)) {
                        turnTowardsPlayer(player);
                    }

                    if (canAttackPlayer(player)) {
                        newEnemyState(ATTACK);
                    }
                    break;
                case ATTACK:
                    if (animationIndex == 19) {
                        attackCheck = false;
                    }

                    if (animationIndex == 21 && !attackCheck) {
                        // check if the player is hit by the enemy
                        checkPlayerHit(attackBox, player);
                    }
            }
        }
    }

    public int flipX() {
        if (walkDirection == RIGHT) {
            return getWidth() + SOUL_REAPER_WIDTH/2;
        }
        return 0;
    }

    public int flipW() {
        if (walkDirection == RIGHT) {
            return -1;
        }
        return 1;
    }

    // draw the attack box
    public void drawAttackBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.GREEN);
        g.drawRect((int)attackBox.x - xLevelOffset, (int)attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

}


