package entities;

import java.awt.*;

public abstract class Entity {
    private int x;
    private int y;
    private Rectangle hitBox;
    private int width;
    private int height;


    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
//        this.hitBox =
    }

    public void setX(int x) {
        this.x = x;
    }

    public void changeX(int x) {
        this.x += x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void changeY(int y) {
        this.y += y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract String pathOfImages(int playerAction);
    public abstract void loadAnimations();
}
