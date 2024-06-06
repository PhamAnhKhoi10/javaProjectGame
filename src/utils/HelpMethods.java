package utils;

import main.MyGame;

import java.awt.geom.Rectangle2D;

public class HelpMethods {
    public static boolean CanMove(float x, float y, float width, float height, int[][] tiles) {
        return !isSolid(x, y, tiles) && // top left
               !isSolid(x, y + height, tiles) && // bottom left
               !isSolid(x + width, y, tiles) && // top right
               !isSolid(x + width, y + height, tiles); // bottom right
    }

    private static boolean isSolid(float x, float y,int[][] tiles) {
        // Check if the player is at the edge of the panel / out of bounds
        if (x < 0 || x >= MyGame.WIDTH_OF_WHOLE_LEVEL) {
            return true;
        }

        if (y < 0 || y >= MyGame.HEIGHT) {
            return true;
        }

        // The current index of the object in map
        float indexX = x / MyGame.TILES_SIZE;
        float indexY = y / MyGame.TILES_SIZE;

        int value = tiles[(int) indexY][(int) indexX];
        return value != 0;
    }

    // Get the next position of the player when it hits a barrier

    // OK does not change it
    public static float getXPositionNextToBarrier(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTileX = (int) (hitbox.x / MyGame.TILES_SIZE);

        if (xSpeed > 0) {
            // moving right
            int tileXPosition = currentTileX * MyGame.TILES_SIZE;
            int deltaX = (int)(tileXPosition - hitbox.x);
            return tileXPosition - deltaX;
        } else {
            // moving left
            return currentTileX * MyGame.TILES_SIZE;
        }
    }

    public static float getYPositionUnderOrAboveBarrier (Rectangle2D.Float hitbox, float airSpeed) {
        int currentTileY = (int) (hitbox.y / MyGame.TILES_SIZE);
        int nextTileY = (int) ((hitbox.y + hitbox.height) / MyGame.TILES_SIZE);

        if (airSpeed > 0) {
            // falling down
            return (nextTileY * MyGame.TILES_SIZE) - hitbox.height + MyGame.TILES_SIZE - 1;
        } else {
            // jumping up or standing still
            return currentTileY * MyGame.TILES_SIZE;
        }
    }

    public static boolean isOnGround(Rectangle2D.Float hitbox, int[][] tiles) {
        // check if the bottom left and bottom right of the player is on the ground
        return !isSolid(hitbox.x, hitbox.y + hitbox.height + 1, tiles) &&
                !isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, tiles);
    }

}
