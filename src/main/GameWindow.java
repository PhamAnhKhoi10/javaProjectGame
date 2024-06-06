package main;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

// JFrame is used to create a GUI

public class GameWindow {
    private JFrame jFrame;


    public GameWindow(GamePanel gamePanel) {
        jFrame = new JFrame("MY GAME");
        // Close the window when the close button is clicked
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add the game panel to the window
        jFrame.add(gamePanel);
        jFrame.setResizable(false);
        // Pack the panel to the window and make it visible
        jFrame.pack();
        // Set the window to be in the center of the screen
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            // The method is automatically called when the window loses focus
            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getMyGame().windowFocusLost();
            }

        });
    }
}
