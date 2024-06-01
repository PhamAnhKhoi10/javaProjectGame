package entities;

import levels.LevelData;
import levels.LevelHandler;
import main.MyGame;
import utils.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;


import static utils.GameConstant.*;
import static utils.GameConstant.playerConstants.*;
import static utils.LoadSave.*;
import static utils.HelpMethods.*;

public class Player extends Entity{
    private LevelHandler currentLevel;
    private BufferedImage[] subImages;
    private BufferedImage image;
    private int playerAction = IDLE;

    // drawOffsetX and drawOffsetY is the offset of the image
    // to make the hitbox and the image align
    private float drawOffsetX = (float) (45 * MyGame.PLAYER_SCALE);
    private float drawOffsetY = (float) (40 * MyGame.PLAYER_SCALE);

    // aniTick is the time between each frame
    // aniIndex is the index of the frame
    // aniSpeed is the speed of the animation
    private int aniTick, aniIndex, aniSpeed = 10;

    // Action
    private boolean moving = false, attacking = false, attackingCombo = false;
    private boolean up = false, left = false, right = false, down = false, jump = false;
    private float speed = 2.0f;


    // Gravity
    // airSpeed is the speed of the player in the air
    private float airSpeed = 0.0f;
    private float gravity = 0.04f * MyGame.PLAYER_SCALE;
    private float jumpSpeed = -2.25f * MyGame.PLAYER_SCALE;
    private float fallSpeedAfterCollision = 0.5f * MyGame.PLAYER_SCALE;
    private boolean inAir = false;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHibox(x, y,MyGame.PLAYER_SCALE * 26,MyGame.PLAYER_SCALE * 40);
        inAir = true;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isDown() {
        return down;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setDown(boolean down) {
        this.down = down;
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

    public void setCurrentLevel(LevelHandler currentLevel) {
        this.currentLevel = currentLevel;
    }

    public LevelHandler getCurrentLevel() {
        return currentLevel;
    }

    public void update() {
        updatePlayerPosition();
        updateAnimations();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(subImages[aniIndex], (int) (getHitbox().x - drawOffsetX), (int) (getHitbox().y - drawOffsetY), super.getWidth(),super.getHeight(),null);
        drawHitbox(g);
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
                attacking = false;
                attackingCombo = false;
            }
        }
    }

    // Set the animation
    public void setAnimation() {
        int startAnimation = playerAction;

        if (moving) {
            playerAction = RUN;

        } else {
            playerAction = IDLE;
        }

        if (inAir) {
            if (airSpeed > 0) {
                playerAction = JUMP;
            } else {
                playerAction = JUMP_FALL;
            }
        }

        if (attacking) {
            playerAction = ATTACK;
        }

        if (attackingCombo) {
            playerAction = ATTACK_COMBO;
        }

        if (startAnimation != playerAction) {
            resetAnimation();
        }

        loadAnimations();
    }

    // Update the player position
    public void updatePlayerPosition() {
        // test code
        moving = false;

        if (jump) {
            jump = true;
            jump();
        }

        if (!left && !right && !inAir) {
            return;
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= speed;
        }
        if (right) {
            xSpeed += speed;
        }

        if (!inAir) {
            if (isOnGround(getHitbox(), getCurrentLevel().getTiles())) {
                inAir = true;
            }
        }

        if (inAir) {
            if (CanMove(getHitbox().x, getHitbox().y + airSpeed, getHitbox().width, getHitbox().height, getCurrentLevel().getTiles())) {
                getHitbox().y += airSpeed;
                airSpeed += gravity;
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
        if (HelpMethods.CanMove(getHitbox().x + xSpeed, getHitbox().y, getHitbox().width, getHitbox().height, getCurrentLevel().getTiles())) {
            getHitbox().x += xSpeed;

        } else {
            getHitbox().x = HelpMethods.getXPositionNextToBarrier(getHitbox(), (float) xSpeed);
        }
    }

    public void resetDir() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    // Load the animations
    @Override
    public void loadAnimations() {
        // coi vid lai sau
        do {
            image = getSprite(this, playerAction);
        } while (image == null);

        subImages = new BufferedImage[GetSprireAmount(playerAction)];

        for (int i = 0; i < subImages.length; i++) {
            subImages[i] = image.getSubimage(i * 120, 0, 120, 80);
        }
    }

    // Reset the animation
    private void resetAnimation() {
        aniIndex = 0;
        aniTick = 0;
    }

    // Get the path of the images
    @Override
    public String pathOfImages(int playerAction) {
        switch (playerAction) {
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
}
