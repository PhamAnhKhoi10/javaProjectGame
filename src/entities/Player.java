package entities;

import gamestates.Play;
import levels.LevelHandler;
import main.MyGame;
import utils.HelpMethods;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


import static utils.GameConstant.GRAVITY;
import static utils.GameConstant.Status.*;
import static utils.GameConstant.playerConstants.*;
import static utils.LoadSave.*;
import static utils.HelpMethods.*;

public class Player extends Entity{
    private LevelHandler level;
    private Play play;
    private BufferedImage[] subImages;
    private BufferedImage image;
//    private int state = IDLE;

    // drawOffsetX and drawOffsetY is the offset of the image
    // to make the hitbox and the image align
    private float drawOffsetX = 45 * MyGame.PLAYER_SCALE;
    private float drawOffsetY = 40 * MyGame.PLAYER_SCALE;

    // aniTick is the time between each frame
    // aniIndex is the index of the frame
    // aniSpeed is the speed of the animation
    private int aniSpeed = 7;

    // Action
    private boolean moving = false, attacking = false, attackingCombo = false;
    private boolean left = false, right = false, jump = false;
//    private float speed = 2.0f;

    // Gravity
    private float jumpSpeed = -3.0f * MyGame.PLAYER_SCALE;
    private float fallSpeedAfterCollision = 0.5f * MyGame.PLAYER_SCALE;

    // status bar
    private BufferedImage statusBar;

    private int statusBarWidth = (int) (192 * MyGame.SCALE);
    private int statusBarHeight = (int) (58 * MyGame.SCALE);
    // x and y due to the game coordinate
    private int statusBarX = (int) (10 * MyGame.SCALE);
    private int statusBarY = (int) (15 * MyGame.SCALE);

    private int healthBarWidth = (int) (150 * MyGame.SCALE);
    private int healthBarHeight = (int) (4 * MyGame.SCALE);
    // x and y due to the status bar coordinate
    private int healthBarXStart = (int) (34 * MyGame.SCALE);
    private int healthBarYStart = (int) (14 * MyGame.SCALE);

//    private int maxHealth = 100;
//    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    // Attack
    private int xFlip = 0;
    private int wFlip = 1;
    private boolean attackChecked;

    public Player(int x, int y, int width, int height, Play play) {
        super(x, y, width, height);
        this.play = play;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkSpeed = 2.0f * MyGame.SCALE;
        loadAnimations();
        initHibox(MyGame.PLAYER_SCALE * 26,MyGame.PLAYER_SCALE * 40);
        initAttackBox();
    }

    public void initAttackBox() {
        attackBox = new Rectangle2D.Float(getX(), getY(), 150, getHitbox().height);
    }

    public void updateAttackBox() {
        if (left) {
            attackBox.x = getHitbox().x  - attackBox.width + getHitbox().width;
        } else if (right) {
            attackBox.x = getHitbox().x;
        }

        attackBox.y = getHitbox().y;

    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }


    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setAttackingCombo(boolean attackingCombo) {
        this.attackingCombo = attackingCombo;
    }

    public void setlevel(LevelHandler level) {
        this.level = level;
    }

    public LevelHandler getLevel() {
        return level;
    }

    public void update() {
        updateHealthBar();

        if (currentHealth <= 0){
            play.setGameOver(true);
            return;
        }

        updatePlayerPosition();
        updateAnimations();
        setAnimation();
        if (attackingCombo) {
            checkAttack();
        }
        updateHealthBar();
        updateAttackBox();
    }

    public void render(Graphics g, int xLevelOffset) {
        g.drawImage(subImages[aniIndex], (int) (getHitbox().x - drawOffsetX) - xLevelOffset + xFlip, (int) (getHitbox().y - drawOffsetY), super.getWidth() * wFlip,super.getHeight(),null);
//        drawHitbox(g, xLevelOffset);
        drawBar(g);

//        // draw attack box
//        g.setColor(Color.GREEN);
//        g.drawRect((int) (attackBox.x - xLevelOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public void drawBar(Graphics g) {
        g.drawImage(statusBar, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.RED);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    public void changeHealth(int amount) {
        currentHealth += amount;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        } else if (currentHealth < 0) {
            currentHealth = 0;
            // Game over
            // gameOver();
        }
    }

    public void updateHealthBar() {
        healthWidth = (int) (currentHealth / (float) maxHealth * healthBarWidth);
    }

    // Update the animation
    public void updateAnimations() {
        aniTick++;
        // If the animation tick > the speed we set
        // Then the next index is coming
        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;

            // If the index reaches max -> reset
            if (aniIndex >= subImages.length) {
                aniIndex = 0;
                attackingCombo = false;
                attackChecked = false;
            }
        }
    }

    // Set the animation
    public void setAnimation() {
        int startAnimation = state;

        if (moving) {
            state = RUN;

        } else {
            state = IDLE;
        }

        if (inAir) {
            if (airSpeed > 0) {
                state = JUMP;
            } else {
                state = JUMP_FALL;
            }
        }

        if (attacking) {
            state = ATTACK;
        }

        if (attackingCombo) {
            state = ATTACK_COMBO;

        }

        if (startAnimation != state) {
            resetAnimation();
        }

        loadAnimations();
    }

    private void checkAttack() {
        if (attackChecked || (aniIndex != 1 && aniIndex != 6)) {
            return;
        }
        attackChecked = true;
        play.checkEnemyHit(attackBox);

    }

    // Update the player position
    public void updatePlayerPosition() {
        // test code
        moving = false;

        if (jump) {
            jump = true;
            jump();
        }

       if (!inAir) {
           if((!left && !right) || (left && right)){
               return;
           }
       }

        float xSpeed = 0;

        if (left) {
            xSpeed -= walkSpeed;
            xFlip = getWidth() - 20;
            wFlip = -1;
        }

        if (right) {
            xSpeed += walkSpeed;
            xFlip = 0;
            wFlip = 1;
        }

        if (!inAir) {
            if (isOnGround(getHitbox(), getLevel().getTiles())) {
                inAir = true;
            }
        }

        if (inAir) {
            if (CanMove(getHitbox().x, getHitbox().y + airSpeed, getHitbox().width, getHitbox().height, getLevel().getTiles())) {
                getHitbox().y += airSpeed;
                airSpeed += GRAVITY;
                updateXPosition((int) xSpeed);
            } else {
                getHitbox().y = getYPositionUnderOrAboveBarrier(getHitbox(), airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPosition((int) xSpeed);
            }
        } else {
            updateXPosition((int) xSpeed);
        }

        moving = true;
    }

    public void jump() {
        if (!inAir) {
            inAir = true;
            airSpeed = jumpSpeed;
        }
    }

    public void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    // ti nua bo ySpeed
    public void updateXPosition(int xSpeed) {
        if (HelpMethods.CanMove(getHitbox().x + xSpeed, getHitbox().y, getHitbox().width, getHitbox().height, getLevel().getTiles())) {
            getHitbox().x += xSpeed;
            super.setX((int)getHitbox().x);

        } else {
            getHitbox().x = HelpMethods.getXPositionNextToBarrier(getHitbox(), (float) xSpeed);
        }
    }

    public void resetDir() {
        left = false;
        right = false;
    }

    // Load the animations
    public void loadAnimations() {
        do {
            image = getSprite(this, state);
        } while (image == null);

        subImages = new BufferedImage[GetSprireAmount(state)];

        for (int i = 0; i < subImages.length; i++) {
            subImages[i] = image.getSubimage(i * 120, 0, 120, 80);
        }

        statusBar = LoadSave.getMap(pathOfMap(BAR));
    }

    // Reset the animation
    private void resetAnimation() {
        aniIndex = 0;
        aniTick = 0;
    }

    public void resetAll() {
        resetDir();
        resetAnimation();
        inAir = true;
        airSpeed = 0;
        currentHealth = maxHealth;
//        attacking = false;
        attackingCombo = false;
        attackChecked = false;
        state = IDLE;

        getHitbox().x = 20;
        getHitbox().y = 0;

        if (!isOnGround(getHitbox(), getLevel().getTiles())) {
            inAir = true;
        }
    }

    @Override
    // Get the path of the images
    public String pathOfImages(int state) {
        switch (state) {
            case ATTACK:
                return "src/res/_Attack.png";
            case ATTACK_2:
                return "src/res/_Attack2.png";
            case ATTACK_2_NO_MOVEMENT:
                return "src/res/_Attack2NoMovement.png";
            case ATTACK_COMBO:
                return "src/res/_AttackCombo.png";
            case ATTACK_COMBO_NO_MOVEMENT:
                return "src/res/_AttackComboNoMovement.png";
            case ATTACK_NO_MOVEMENT:
                return "src/res/_AttackNoMovement.png";
            case CROUCH:
                return "src/res/_Crouch.png";
            case CROUCH_ATTACK:
                return "src/res/_CrouchAttack.png";
            case CROUCH_FULL:
                return "src/res/_CrouchFull.png";
            case CROUCH_TRANSITION:
                return "src/res/_CrouchTransition.png";
            case CROUCH_WALK:
                return "src/res/_CrouchWalk.png";
            case DASH:
                return "src/res/_Dash.png";
            case DEATH:
                return "src/res/_Death.png";
            case DEATH_NO_MOVEMENT:
                return "src/res/_DeathNoMovement.png";
            case FALL:
                return "src/res/_Fall.png";
            case HIT:
                return "src/res/_Hit.png";
            case IDLE:
                return "src/res/_Idle.png";
            case JUMP:
                return "src/res/_Jump.png";
            case JUMP_FALL:
                return "src/res/_JumpFallInbetween.png";
            case ROLL:
                return "src/res/_Roll.png";
            case RUN:
                return "src/res/_Run.png";
            case SLIDE:
                return "src/res/_Slide.png";
            case SLIDE_FULL:
                return "src/res/_SlideFull.png";
            case SLIDE_TRANSITION_END:
                return "src/res/_SlideTransitionEnd.png";
            case SLIDE_TRANSITION_START:
                return "src/res/_SlideTransitionStart.png";
            case TURN_AROUND:
                return "src/res/_TurnAround.png";
            case WALL_CLIMB:
                return "src/res/_WallClimb.png";
            case WALL_CLIMB_NO_MOVEMENT:
                return "src/res/_WallClimbNoMovement.png";
            case WALL_HANG:
                return "src/res/_WallHang.png";
            case WALL_SLIDE:
                return "src/res/_WallSlide.png";
        }
        return null;
    }


    public void reset(int[][] tiles) {
        getHitbox().x = 20;
        getHitbox().y = 0;
        currentHealth = maxHealth;
        inAir = true;
        airSpeed = 0;
        attacking = false;
        attackingCombo = false;
        attackChecked = false;
        state = IDLE;
        updateHealthBar();
        updateAttackBox();
    }
}
