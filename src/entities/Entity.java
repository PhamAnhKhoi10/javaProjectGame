package entities;

import main.MyGame;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    private int x;
    private int y;

    private Rectangle2D.Float hitbox;
    private int width;
    private int height;


    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initHibox(float x, float y, float width, float height) {
        this.hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    // test method
    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x, (int)hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }


    public Rectangle2D.Float getHitbox() {
        return hitbox;
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

    public int getTileX() {
        return (int) hitbox.getX() / MyGame.TILES_SIZE;
    }

    public int getTileY() {
        return (int) hitbox.getX() / MyGame.TILES_SIZE;
    }

    public abstract String pathOfImages(int playerAction);
    public abstract void loadAnimations();
}
