package entities;

import main.MyGame;
import java.awt.geom.Rectangle2D;

import static utils.GameConstant.Directions.*;
import static utils.GameConstant.EnemyConstants.*;
import static utils.GameConstant.*;
import static utils.GameConstant.Status.getEnemyDamage;
import static utils.GameConstant.Status.getMaxHealth;
import static utils.HelpMethods.*;

public abstract class Enemy extends Entity {
    // Enemy animation
    protected int enemyType;
    protected int aniSpeed = 15;
    protected int animationIndex, startIndex, endIndex;

    // Boolean start the game
    protected boolean firstUpdate = true;

    // Patrolling
    protected int walkDirection = LEFT;

    // Can see player
    protected int tileY;

    // Attack
    protected float attackRange = MyGame.TILES_SIZE * 2;

    protected boolean attackCheck;
    // check if the enemy is active
    protected boolean active = true;

    public Enemy(int x, int y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.maxHealth = getMaxHealth(enemyType);
        this.currentHealth = maxHealth;
        this.walkSpeed = 1.0f * MyGame.SCALE;
    }

    // Enemy hurt
    protected void hurt(int damage) {
        currentHealth -= damage;
        if (currentHealth <= 0) {
            newEnemyState(DEATH);
        } else {
            newEnemyState(HURT);
        }
    }
        /*Idle: 0 -> 7
        * Walk: 8 -> 15
        * Attack: 16 -> 25
        * Hurt: 26 -> 28
        * Death: 29 -> 38
        * Cast: 39 -> 47
        * Spell: 48 -> 63 */
// =========================================UPDATING THE ENEMY=========================================
    // Update animation
    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            animationIndex++;
            if(animationIndex > endIndex) {
                animationIndex = startIndex;
                switch (state) {
                    case ATTACK, HURT -> newEnemyState(IDLE);
                    case DEATH -> active = false;
                }
            }
        }
    }

    // check if the game is first updated
    protected void firstUpdateCheck(int [][] tiles) {
        if (firstUpdate) {
            if (!isOnGround(getHitbox(), tiles)) {
                inAir = true;
            }

            firstUpdate = false;
        }
    }

    // reset all the game
    public void reset(int [][] tiles) {
        getHitbox().x = getX();
        getHitbox().y = getY();
        currentHealth = maxHealth;
        firstUpdate = true;
        newEnemyState(IDLE);

        // Reset the inAir flag and airSpeed value
        inAir = true;
        airSpeed = 0;

        updateInAir(tiles);
        active = true;
    }

    protected void updateInAir(int tiles[][]) {
        if (CanMove(getHitbox().x, getHitbox().y + airSpeed, getHitbox().width, getHitbox().height, tiles)) {
            getHitbox().y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            getHitbox().y = getYPositionUnderOrAboveBarrier(getHitbox(), airSpeed);
            tileY = (int) ((getHitbox().y + getHitbox().height) / MyGame.TILES_SIZE);
        }
    }

    protected void move(int tiles[][]) {
        float xSpeed = 0;
        if (walkDirection == LEFT) {
            xSpeed = - walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }

        if (CanMove(getHitbox().x + xSpeed , getHitbox().y, getHitbox().width, getHitbox().height, tiles)) {
            if (isFloor(getHitbox(), xSpeed, tiles, walkDirection)) {
                getHitbox().x += xSpeed;
                return;
            } else {
                changeDirection();
            }
        } else {
            changeDirection();
        }

    }

    protected void turnTowardsPlayer(Player player) {
        if (player.getHitbox().x > getHitbox().x) {
            walkDirection = RIGHT;
        }
        else {
            walkDirection = LEFT;
        }
    }

    protected boolean canSeePlayer(int[][] tiles, Player player) {
        int playerTileY = (int) ((player.getHitbox().y + player.getHitbox().height) / MyGame.TILES_SIZE);

        if (playerTileY == tileY) {
            if (isPlayerInRange(player)) {
                if (isSightClear(tiles, getHitbox(), player.getHitbox(), tileY)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPlayerInRange(Player player) {
        int absDeltaX = (int) Math.abs(player.getHitbox().x - getHitbox().x);

        return absDeltaX <= attackRange * 2 * MyGame.PLAYER_SCALE;
    }

    public boolean isActive() {
        return active;
    }

    protected boolean canAttackPlayer(Player player) {
        int absValue = (int) Math.abs(player.getHitbox().x - getHitbox().x);
        return absValue <= attackRange * MyGame.PLAYER_SCALE && canSeePlayer(player.getLevel().getTiles(), player);
    }

    // Check if the player is hit by the enemy
    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.getHitbox())) {
            player.changeHealth(-getEnemyDamage(enemyType));
            attackCheck = true;
        }
    }
// =========================================GETTERS AND SETTERS=========================================

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return state;
    }

    protected void newEnemyState(int enemyState) {
        this.state = enemyState;
        updateAnimationFrames();
    }

    protected void updateAnimationFrames() {
        startIndex = getStartIndex(enemyType, state);
        endIndex = startIndex + getSpriteAmount(enemyType, state) - 1;
        animationIndex = startIndex;
    }

    protected void changeDirection() {
        if (walkDirection == LEFT) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }
// =========================================GET THE PATH OF IMAGES=========================================
    @Override
    public String pathOfImages(int enemyType) {
        switch (enemyType) {
            case SOUL_REAPER -> {
                return "src/res/enemy.png";
            }
        }
        return null;
    }

}
