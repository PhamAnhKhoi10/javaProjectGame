package entities;

import java.awt.*;
import java.awt.image.BufferedImage;


import static utils.GameConstant.*;
import static utils.GameConstant.playerConstants.*;
import static utils.LoadSave.*;

public class Player extends Entity{
    private BufferedImage[] subImages;
    private BufferedImage image;
    private int playerAction = IDLE;

    // aniTick is the time between each frame
    // aniIndex is the index of the frame
    // aniSpeed is the speed of the animation
    private int aniTick, aniIndex, aniSpeed = 10;


    private boolean moving = false, attacking = false, attackingCombo = false;
    private boolean up = false, left = false, right = false, down = false;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
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

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setAttackingCombo(boolean attackingCombo) {
        this.attackingCombo = attackingCombo;
    }

    public void update() {
        updatePlayerPosition();
        updateAnimations();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(subImages[aniIndex], super.getX(), super.getY(), super.getWidth(),super.getHeight(),null);
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
        moving = false;

        if (left && !right) {
            super.changeX(-2);
            moving = true;
        } else if (right && !left) {
            super.changeX(2);
            moving = true;
        }

        if (up && !down) {
            super.changeY(-2);
            moving = true;
        } else if (down && !up) {
            super.changeY(2);
            moving = true;
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
                return "./res/_Attack.png";
            case ATTACK_2:
                return "./res/_Attack2.png";
            case ATTACK_2_NO_MOVEMENT:
                return "./res/_Attack2NoMovement.png";
            case ATTACK_COMBO:
                return "./res/_AttackCombo.png";
            case ATTACK_COMBO_NO_MOVEMENT:
                return "./res/_AttackComboNoMovement.png";
            case ATTACK_NO_MOVEMENT:
                return "./res/_AttackNoMovement.png";
            case CROUCH:
                return "./res/_Crouch.png";
            case CROUCH_ATTACK:
                return "./res/_CrouchAttack.png";
            case CROUCH_FULL:
                return "./res/_CrouchFull.png";
            case CROUCH_TRANSITION:
                return "./res/_CrouchTransition.png";
            case CROUCH_WALK:
                return "./res/_CrouchWalk.png";
            case DASH:
                return "./res/_Dash.png";
            case DEATH:
                return "./res/_Death.png";
            case DEATH_NO_MOVEMENT:
                return "./res/_DeathNoMovement.png";
            case FALL:
                return "./res/_Fall.png";
            case HIT:
                return "./res/_Hit.png";
            case IDLE:
                return "./res/_Idle.png";
            case JUMP:
                return "./res/_Jump.png";
            case JUMP_FALL:
                return "./res/_JumpFallInbetween.png";
            case ROLL:
                return "./res/_Roll.png";
            case RUN:
                return "./res/_Run.png";
            case SLIDE:
                return "./res/_Slide.png";
            case SLIDE_FULL:
                return "./res/_SlideFull.png";
            case SLIDE_TRANSITION_END:
                return "./res/_SlideTransitionEnd.png";
            case SLIDE_TRANSITION_START:
                return "./res/_SlideTransitionStart.png";
            case TURN_AROUND:
                return "./res/_TurnAround.png";
            case WALL_CLIMB:
                return "./res/_WallClimb.png";
            case WALL_CLIMB_NO_MOVEMENT:
                return "./res/_WallClimbNoMovement.png";
            case WALL_HANG:
                return "./res/_WallHang.png";
            case WALL_SLIDE:
                return "./res/_WallSlide.png";
        }
        return null;
    }
}
