package gamestates;

import entities.EnemyHandler;
import entities.Player;
import levels.LevelHandler;
import main.MyGame;
import ui.LevelCompletedOverlay;
import ui.LoseOverlLay;
import ui.PauseOverLay;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import static utils.GameConstant.LevelConstant.*;

public class Play extends State implements Statemethod{
    // Level
    private LevelHandler level;
    private boolean isStart;
    
    private Player myPlayer;
    private EnemyHandler enemyHandler;
    private boolean pause = false;
    private boolean gameOver = false;
    private boolean levelCompleted = false;
    private PauseOverLay pauseOverLay;
    private LoseOverlLay loseOverlLay;
    private LevelCompletedOverlay levelCompletedOverlay;

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
        // Set the level of the game (default is level 1)
        level = new LevelHandler(super.getMyGame(), LEVEL_1);

        // Initialize the player
        myPlayer = new Player(20, 0, (int) (MyGame.PLAYER_SCALE * 128), (int) (MyGame.PLAYER_SCALE * 80), this);
        myPlayer.setlevel(level);   // Set the current level of the player

        // Initialize the pause, lose, and level completed screen
        pauseOverLay = new PauseOverLay(this);
        loseOverlLay = new LoseOverlLay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        
        enemyHandler = new EnemyHandler(this);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyHandler.checkEnemyHit(attackBox);
    }

    // reset the player and the level
    public void resetAll() {
        level.setLevel(LEVEL_1);
        myPlayer.resetAll();
        enemyHandler.resetAllEnemies(level.getTiles());
        enemyHandler.addEnemies();
        gameOver = false;
        pause = false;
    }

    // =======================================SETTER AND GETTER==================================================
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }


    public int getxLevelOffset() {
        return xLevelOffset;
    }

    public EnemyHandler getEnemyHandler() {
        return enemyHandler;
    }

    public LevelHandler getLevel() {
        return level;
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.levelCompleted = levelCompleted;
    }
    
    // Reset the direction of the player when losing focus on the window
    public void windowFocusLost() {
        myPlayer.resetDir();
    }

    // ======================================================================================================================================
    // Update and draw the player and the level
    @Override
    public void update() {
        if (pause){
            pauseOverLay.update();
            return;
        }

        if(levelCompleted){
            levelCompletedOverlay.update();
            return;
        }

        if (gameOver){
            loseOverlLay.update();
            return;
        }

        myPlayer.update();
        enemyHandler.update(level.getTiles(), myPlayer);
        checkCloseToBorder();
        if (enemyHandler.isAllDefeated()) {
            levelCompleted = true;
        }
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

    @Override
    public void draw(Graphics g) {
        level.draw(g, xLevelOffset);
        myPlayer.render(g, xLevelOffset);
        enemyHandler.draw(g, xLevelOffset);
        if (pause) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, MyGame.WIDTH, MyGame.HEIGHT);
            pauseOverLay.draw(g);
        } else if (gameOver) {
            loseOverlLay.draw(g);
        } else if (levelCompleted) {
            levelCompletedOverlay.draw(g);
        }
    }

    // ======================================================================================================================================
    // Getting the keyboard and mouse event whenever the state is PLAY
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1) {
                myPlayer.setAttackingCombo(true);
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver)
            if (pause) {
                pauseOverLay.mousePressed(e);
            } else if (levelCompleted) {
                levelCompletedOverlay.mousePressed(e);
            }

        loseOverlLay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver)
            if (pause) {
                pauseOverLay.mouseReleased(e);
            } else if (levelCompleted) {
                levelCompletedOverlay.mouseReleased(e);
            }
        loseOverlLay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver)
            if (pause) {
                pauseOverLay.mouseMoved(e);
            } else if (levelCompleted) {
                levelCompletedOverlay.mouseMoved(e);
            }
       loseOverlLay.mouseMoved(e);
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver)
            if (pause) {
                pauseOverLay.mouseDragged(e);
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            loseOverlLay.keyPressed(e);
        }  else {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
//                myPlayer.setUp(true);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
//                myPlayer.setDown(true);
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
//            myPlayer.setUp(false);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
//            myPlayer.setDown(false);
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
