package gridwar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameState extends JPanel implements KeyListener {

    /**
     * ******** ATTRIBUTES *********
     */
    /**
     * ******** MAPS *********
     */
    ImageIcon beachMap = new ImageIcon(this.getClass().getResource("beach_map.png"));
    ImageIcon solarsystemMap = new ImageIcon(this.getClass().getResource("solarsystem_map.png"));

    /**
     * ******** CHARACTERS *********
     */
    MoodengNormal mdChar = new MoodengNormal();
    EnemyNormal enChar = new EnemyNormal();
    /**
     * ******** MOODENG ANIMATION *********
     */
    Thread mdAnimation;
    int mdcurrentImageIndex = 0;
    /**
     * ******** ENEMY ANIMATION *********
     */
    Thread enAnimation;
    int encurrentImageIndex = 0;
    /**
     * ******** BULLET ANIMATION *********
     */
    Timer bulletAnimation;
    Bullet bullet = new Bullet();
    boolean isbulletVisible;
    /**
     * ******** TEXT *********
     */
    JLabel mdHealth = new JLabel("HP: " + mdChar.getHealth());
    JLabel mdSkillPoint = new JLabel("SP: " + mdChar.getSkillPoint());
    JLabel enHealth = new JLabel("HP: " + enChar.getHealth());
    JLabel enSkillPoint = new JLabel("SP: " + enChar.getHealth());
    /**
     * ******** CONTROLLER *********
     */
    boolean skillStatus = false; // false = skill unuse
    int skillCD = 0;
    int skillRound = 0;
    boolean spON = false;

    boolean action = true;

    boolean gameOver = false;

    /**
     * ******** COUNTDOWN *********
     */
    int fixedTime = 15;
    int gameCD = fixedTime;
    Thread gameCDThread;

    /**
     * ******** TURN STATUS *********
     */
    boolean turnStatus = true; // true = moodeng , false = enemy

    public GameState() {
        setFocusable(true);
        addKeyListener(this);

        enAnimation = new Thread(() -> {
            while (true) {
                encurrentImageIndex = (encurrentImageIndex + 1) % enChar.enNormal.length;
                repaint();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        enAnimation.start();

        mdAnimation = new Thread(() -> {
            while (true) {
                if (skillStatus) {
                    mdcurrentImageIndex = (mdcurrentImageIndex + 1) % mdChar.mdSkills.length;
                    repaint();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(100);  // หยุดชั่วคราวเมื่อไม่ต้องการให้ mdAnimation ทำงาน
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        bulletAnimation = new Timer(1, e -> {
            if (turnStatus) {
                if (isbulletVisible) {
                    bullet.setbY(bullet.getbY() - 15);
                    checkCollision();
                    repaint();
                }
            } else {
                if (isbulletVisible) {
                    bullet.setbY(bullet.getbY() + 15);
                    checkCollision();
                    repaint();
                }
            }

        });

        gameCDThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); // หยุด 1 วินาที
                    gameCD--; // ลดค่าของ countdown ทีละ 1
                    if (gameCD < 0) {
                        updateAction();
                    }
                    repaint(); // รีเฟรชหน้าจอเพื่ออัปเดตตัวเลขนับถอยหลัง
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void updateHealth() {
        if (mdChar.getGridNO() == enChar.getGridNO()) {
            if (turnStatus) { //เทิร์นหมูเด้ง
                mdChar.setSkillPoint();
                if (mdChar.getSkillPoint() >= 6) {
                    mdChar.resetSP();
                    enChar.setHealth(4);
                } else {
                    enChar.setHealth(2);
                }
            } else {
                enChar.setSkillPoint();
                if (skillStatus == false) {
                    if (enChar.getSkillPoint() >= 6) {
                        enChar.resetSP();
                        mdChar.setHealth(4);
                    } else {
                        mdChar.setHealth(2);
                    }
                }
            }
        }
    }

    public void updateLabels() {
        mdHealth.setText("HP: " + mdChar.getHealth());
        mdSkillPoint.setText("SP: " + mdChar.getSkillPoint());
    }

    public void checkCollision() {
        if (turnStatus) { // เทิร์นเรา
            int enemyY = enChar.getPositionY() + 65;
            if (bullet.getbY() <= enemyY) {
                isbulletVisible = false;
                bulletAnimation.stop();
            }
        } else { // เทิร์นศัตรู
            int mdY = mdChar.getPositionY();
            if (bullet.getbY() >= mdY) {
                isbulletVisible = false;
                bulletAnimation.stop();
            }
        }
    }

    public void updateAction() {
        turnStatus = !turnStatus;
        gameCD = fixedTime;

        if (turnStatus == true) {//Moodeng's turn
            if (skillStatus) {//Use skill
                skillStatus = false;
                skillCD = 3;
            }
            updateSkillCD();
        }
    }

    public void updateSkillCD() {
        if (skillCD > 0) {
            skillCD--;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (turnStatus) {
            if (key == KeyEvent.VK_A) {
                mdChar.setGridNO(-1); // ตั้งค่าทิศทางซ้าย
                mdChar.setPositionX(-1);
                System.out.println("Pressed: A");
                updateAction();
            } else if (key == KeyEvent.VK_D) {
                mdChar.setGridNO(1); // ตั้งค่าทิศทางขวา
                mdChar.setPositionX(1);
                updateAction();
                System.out.println("Pressed: D");
            } else if (key == KeyEvent.VK_W) {
                bullet.setbX(mdChar.getPositionX() - 5);
                bullet.setbY(mdChar.getPositionY());
                isbulletVisible = true;
                if (!bulletAnimation.isRunning()) {
                    bulletAnimation.start();
                    System.out.println("Pressed: W - Bullet moving upward");
                }

                updateHealth();
                updateLabels();
                updateAction();

            } else if (key == KeyEvent.VK_S && skillCD == 0) {
                if (!skillStatus) {  // ถ้ายังไม่เริ่มต้น
                    skillStatus = true;
                    if (!mdAnimation.isAlive()) {
                        mdAnimation.start();  // เริ่มต้นเฉพาะเมื่อ thread ยังไม่รัน
                    }
                }
                System.out.println("Pressed: S");
                updateAction();
            }
        } else {
            if (key == KeyEvent.VK_A) {
                enChar.setGridNO(-1); // ตั้งค่าทิศทางซ้าย
                enChar.setPositionX(-1);
                System.out.println("Pressed: A");
                updateAction();
            } else if (key == KeyEvent.VK_D) {
                enChar.setGridNO(1); // ตั้งค่าทิศทางขวา
                enChar.setPositionX(1);
                System.out.println("Pressed: D");
                updateAction();
            } else if (key == KeyEvent.VK_W) {
                bullet.setbX(enChar.getPositionX());
                bullet.setbY(enChar.getPositionY() + 80);
                isbulletVisible = true;
                if (!bulletAnimation.isRunning()) {
                    bulletAnimation.start();
                    System.out.println("Pressed: W - Bullet moving upward");
                }
                updateHealth();
                updateLabels();
                updateAction();

            } else if (key == KeyEvent.VK_S) {
                updateAction();
                System.out.println("Pressed: S");
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (enChar.getHealth() <= 0 || mdChar.getHealth() <= 0) {
            JPanel resultPanel = new JPanel() {
                ImageIcon victoryBG = new ImageIcon(this.getClass().getResource("victory.png"));
                ImageIcon defeatBG = new ImageIcon(this.getClass().getResource("defeat.png"));

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (enChar.getHealth() <= 0) {
                        g.drawImage(victoryBG.getImage(), 0, 0, getWidth(), getHeight(), this);
                    } else {
                        g.drawImage(defeatBG.getImage(), 0, 0, getWidth(), getHeight(), this);
                    }
                }
            };
            resultPanel.setBackground(Color.BLACK); // กำหนดพื้นหลังสีขาว
            resultPanel.setSize(getWidth(), getHeight());

            // ล้าง JPanel เดิมใน GameState และเพิ่ม resultPanel ใหม่
            this.removeAll();
            this.add(resultPanel);
            resultPanel.requestFocusInWindow();
            revalidate();
            repaint();
        }

        g.drawImage(solarsystemMap.getImage(), 0, 0, getWidth(), getHeight(), this);

        /**
         * ******** DISPLAY TIMER *********
         */
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString("Time: " + gameCD, getWidth() / 2 - 50, 50); // แสดงตัวเลขนับถอยหลังที่กลางหน้าจอด้านบน

        if (turnStatus) {
            g.setColor(Color.BLUE);
            g.drawString("Moodeng's turn", getWidth() / 2 - 100, 90);
        } else {
            g.setColor(Color.RED);
            g.drawString("Enemy's turn", getWidth() / 2 - 100, 90);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        if (skillStatus) {
            g.drawString("Skill: Protected", 155, 710);
        } else if (skillStatus == false) {
            if (skillCD == 0) {
                g.drawString("Skill: Can Use", 155, 710);
            } else {
                g.drawString("Skill: Can't Use", 155, 710);
            }

        }
        g.drawString("CD: " + skillCD, 155, 740);

        /**
         * ******** MOODENG DETAILS *********
         */
        g.setColor(Color.BLACK);

        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("HP:", 25, 710);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("" + mdChar.getHealth(), 75, 710);

        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("SP:", 25, 740);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("" + mdChar.getSkillPoint(), 75, 740);

        /**
         * ******** ENEMY DETAILS *********
         */
        g.setColor(Color.BLACK);

        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("HP:", 600, 710);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("" + enChar.getHealth(), 650, 710);

        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("SP:", 600, 740);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("" + enChar.getSkillPoint(), 650, 740);

        if (!skillStatus) {
            g.drawImage(mdChar.mdNormal.getImage(), mdChar.getPositionX(), mdChar.getPositionY(), mdChar.getEntityWidth(), mdChar.getEntityHeight(), this);
        } else {
            if (mdChar.mdSkills[mdcurrentImageIndex] != null) {
                g.drawImage(mdChar.mdSkills[mdcurrentImageIndex].getImage(), mdChar.getPositionX(), mdChar.getPositionY(), mdChar.getEntityWidth(), mdChar.getEntityHeight(), this);
            }
        }
        if (enChar.enNormal[encurrentImageIndex] != null) {
            g.drawImage(enChar.enNormal[encurrentImageIndex].getImage(), enChar.getPositionX(), enChar.getPositionY(), enChar.getEntityWidth(), enChar.getEntityHeight(), this);
        }
        if (isbulletVisible) {
            g.setColor(Color.black);
            g.fillRect(bullet.getbX(), bullet.getbY(), 5, 20);
        }

    }

    public static void main(String[] args) {
        JFrame gameFrame = new JFrame();
        gameFrame.setTitle("Grid War");
        gameFrame.setSize(800, 800);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
    }
}
