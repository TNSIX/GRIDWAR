package gridwar;

import java.awt.event.*;
import javax.swing.*;

public class GameMenu extends JFrame {

    HomePage homepagePanel = new HomePage();
    GameState gamestatePanel = new GameState();

    public GameMenu() {
        add(homepagePanel);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    homepagePanel.setVisible(false);
                    add(gamestatePanel);
                    gamestatePanel.requestFocusInWindow(); // ให้ GameState รับโฟกัส
                    gamestatePanel.gameCDThread.start();
                    revalidate(); // อัพเดตเฟรมให้แสดงผลใหม่
                    repaint();

                    System.out.println("Pressed: SPACEBAR");
                }
            }
        });

    }

    public static void main(String[] args) {
        JFrame gameFrame = new GameMenu();
        gameFrame.setTitle("Grid War");
        gameFrame.setSize(800, 800);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
    }
}
