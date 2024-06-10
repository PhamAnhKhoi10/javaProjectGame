package main;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Play;


import java.awt.*;

public class MyGame implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS = 144;
    private final int UPS = 180;
    private long lastCheck = 0;
    private Play play;
    private Menu menu;

    // Tile constants
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.0f;
    public final static float PLAYER_SCALE = 2.0f;
    public final static float MENU_SCALE = 2.0f;
    public final static int TILES_WIDTH = 48;
    public final static int TILES_WHOLE_WIDTH = 80;
    public final static int TILES_HEIGHT = 26;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int WIDTH = TILES_WIDTH * TILES_SIZE;
    public final static int WIDTH_OF_WHOLE_LEVEL = TILES_WHOLE_WIDTH * TILES_SIZE;
    public final static int HEIGHT = TILES_HEIGHT * TILES_SIZE;


    public MyGame() {
        initialize();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        start();
    }

    // Method to initialize the all objects in the game
    private void initialize() {
        menu = new Menu(this);
        play = new Play(this);
    }

    // Method to start the game thread
    private void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Update the game
    public void update () {
        switch (Gamestate.state) {
            case PLAY:
                play.update();
                break;
            case MENU:
                menu.update();
                break;
            case OPTION:
                break;
            case EXIT:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    // Draw everything in to the panel (Called in GamePanel class)
    public void render(Graphics g) {
        switch (Gamestate.state) {
            case PLAY:
                play.draw(g);
                break;
            case MENU:
                menu.draw(g);
                break;
            case OPTION:
                break;
            case EXIT:
                System.exit(0);
            default:
                break;
        }
    }

    // ======================================================================================================================================
    // Game loop
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
                update();
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
        if (Gamestate.state == Gamestate.PLAY) {
            play.windowFocusLost();
        }
    }

    // ==================================================SETTER AND GETTER==============================================================
    public Menu getMenu() {
        return menu;
    }

    public Play getPlay() {
        return play;
    }
}

