package gridwar;

import java.awt.*;
import javax.swing.*;

public class HomePage extends JPanel {

    ImageIcon homepageBG = new ImageIcon(this.getClass().getResource("homepage.png"));

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(homepageBG.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
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
