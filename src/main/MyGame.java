package main;

import entities.Player;
import levels.Level1;
import levels.LevelHandler;

import java.awt.*;

public class MyGame implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS = 144;
    private final int UPS = 144;
    private Player myPlayer;
    private long lastCheck = 0;
    private Level1 level1;

    // Tile constants
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static double SCALE = 2;
    public final static int TILES_WIDTH = 24;
    public final static int TILES_HEIGHT = 13;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int WIDTH = TILES_WIDTH * TILES_SIZE;
    public final static int HEIGHT = TILES_HEIGHT * TILES_SIZE;

    public MyGame() {
        initialize();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        start();
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    // Method to initialize the all objects in the game
    private void initialize() {
        myPlayer = new Player(50, 7 * 32, (int) (SCALE * 128), (int) (SCALE * 80));
        level1 = new Level1(this);
    }

    // Method to start the game thread
    private void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Update the game
    public void update () {
        myPlayer.update();
//        levelHandler.update();
    }

    // Draw everything in to the panel (Called in GamePanel class)
    public void render(Graphics g) {
        level1.draw(g);
        myPlayer.render(g);
    }

    @Override
    public void run() {
        // 1 second in nanoseconds
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;

        int frame = 0;
        int update = 0;

        long previousTime = System.nanoTime();
        double deltaU = 0;
        double deltaF = 0;


        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;

            previousTime = currentTime;

            if (deltaU >= 1) {
                myPlayer.update();
                update++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frame++;
                deltaF--;
            }


            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frame + "|| UPS: " + update);
                frame = 0;
                update = 0;
            }
        }
    }

    public void windowFocusLost() {
        myPlayer.resetDir();
    }
}

