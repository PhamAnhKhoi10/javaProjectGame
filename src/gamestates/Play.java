package gamestates;

import entities.Player;
import levels.Level1;
import levels.LevelData;
import levels.LevelHandler;
import main.MyGame;
import ui.PauseOverLay;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Play extends State implements Statemethod{
    private Player myPlayer;
    private LevelHandler level1;
    private boolean pause = false;
    private PauseOverLay pauseOverLay;

    // Variables to change the visible level when the player is moving to the boarder of the screen
    private int xLevelOffset;
    private int leftBorder = (int) (MyGame.WIDTH * 0.2); // Does not move the level when the player is at the distance of 20% of the screen to the left
    private int rightBorder = (int) (MyGame.WIDTH * 0.8); // Does not move the level when the player is at the distance of 80% of the screen to the right
    private int maxTileOffset = MyGame.TILES_WHOLE_WIDTH - MyGame.TILES_WIDTH;
    private int maxOffsetx = maxTileOffset * MyGame.TILES_SIZE;

    public Play (MyGame myGame) {
        super(myGame);
        initialize();
    }

    // Initialize the player and the level
    private void initialize() {
        level1 = new Level1(super.getMyGame(), LevelData.getTilesLevel1());
        myPlayer = new Player(20, 0, (int) (MyGame.PLAYER_SCALE * 128), (int) (MyGame.PLAYER_SCALE * 80));
        myPlayer.setCurrentLevel(level1);   // Set the current level of the player
        pauseOverLay = new PauseOverLay(this);
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    // Reset the direction of the player when losing focus on the window
    public void windowFocusLost() {
        myPlayer.resetDir();
    }

    // ======================================================================================================================================
    // Update and draw the player and the level
    @Override
    public void update() {
        if (!pause)
            myPlayer.update();
            checkCloseToBorder();
        if (pause)
            pauseOverLay.update();
    }

    private void checkCloseToBorder() {                  // Move to the border -> check | Not move after check (xLevelOffset = 5)
        int playerX = (int) getMyPlayer().getHitbox().x; // playerX = 85                | playerX = 85
        int deltaX = playerX - xLevelOffset;             // deltaX = 85 - 0 = 85        | deltaX = 85 - 5 = 80
        // check right boarder                           // rightBorder = 80            | rightBorder = 80
        if (deltaX > rightBorder) {                      // if (85 > 80)                | if (80 > 80)
            xLevelOffset += deltaX - rightBorder;        // xLevelOffset += 85 - 80 = 5 | false -> do nothing
        }
        // check left boarder
        if (deltaX < leftBorder) {
            xLevelOffset -= leftBorder - deltaX;
        }

        if (xLevelOffset < 0) {
            xLevelOffset = 0;
        } else if (xLevelOffset > maxOffsetx) {
            xLevelOffset = maxOffsetx;
        }
    }



    public int getxLevelOffset() {
        return xLevelOffset;
    }

    @Override
    public void draw(Graphics g) {
        level1.draw(g, xLevelOffset);
        myPlayer.render(g, xLevelOffset);
        if (pause) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, MyGame.WIDTH, MyGame.HEIGHT);
            pauseOverLay.draw(g);
        }
    }

    // ======================================================================================================================================
    // Getting the keyboard and mouse event whenever the state is PLAY
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            myPlayer.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
           myPlayer.setAttackingCombo(true);
        }

        if (pause) {
            pauseOverLay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pause) {
            pauseOverLay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (pause) {
            pauseOverLay.mouseMoved(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (pause) {
            pauseOverLay.mouseDragged(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            myPlayer.setUp(true);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            myPlayer.setDown(true);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            myPlayer.setLeft(true);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            myPlayer.setRight(true);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            myPlayer.setJump(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            changePause();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            myPlayer.setUp(false);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            myPlayer.setDown(false);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            myPlayer.setLeft(false);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            myPlayer.setRight(false);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            myPlayer.setJump(false);
        }
    }

    // Resume the game
    public void changePause() {
        pause = !pause;
    }
}
