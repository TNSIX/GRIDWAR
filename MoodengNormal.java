package gridwar;

import javax.swing.ImageIcon;

public class MoodengNormal extends Character {

    public ImageIcon mdNormal;
    public ImageIcon[] mdSkills = new ImageIcon[5];
    public int x;
    public int count = 0;
    /**
     * ********ATTRIBUTES*********
     */
    private int health = 20;
    private int skillPoint = 0;
    private boolean actionPoint = true;
    private int cdSkill = 3;
    /**
     * ********POSITION*********
     */
    private int gridNO = 5;
    private int playerPositionX = 400; // -95
    private int playerPositionY = 533;
    private int playerWidth = 88;
    private int playerHeight = 88;

    MoodengNormal() {
        mdNormal = new ImageIcon(this.getClass().getResource("md_normal.png"));
        for (int i = 0; i < mdSkills.length; i++) {
            mdSkills[i] = new ImageIcon(this.getClass().getResource("md_pro_" + (i + 1) + ".png"));
        }
    }

    @Override
    int getHealth() {
        return this.health;
    }

    @Override
    int getSkillPoint() {
        return this.skillPoint;
    }

    @Override
    void setHealth(int damage) {
        this.health = this.health - damage;
    }

    @Override
    void setSkillPoint() {
        this.skillPoint++;
    }

    @Override
    int getGridNO() {
        return this.gridNO;
    }

    @Override
    void setGridNO(int direction) {
        if (direction == -1) {
            this.gridNO = this.gridNO - 1;
        } else if (direction == 1) {
            this.gridNO = this.gridNO + 1;
        }
    }

    @Override
    int getPositionX() {
        return this.playerPositionX;
    }

    @Override
    int getPositionY() {
        return this.playerPositionY;
    }

    @Override
    int getEntityWidth() {
        return this.playerWidth;
    }

    @Override
    int getEntityHeight() {
        return this.playerHeight;
    }

    @Override
    void setPositionX(int direction) {
        if (direction == -1) {
            this.playerPositionX -= 95;
        } else if (direction == 1) {
            this.playerPositionX += 95;
        }

        if (this.playerPositionX < 16) {
            this.playerPositionX = 685;
            this.gridNO = 8;
        } else if (this.playerPositionX > 700) {
            this.playerPositionX = 20;
            this.gridNO = 1;
        }
        System.out.println("X: " + this.playerPositionX + " " + "Grid NO: " + this.gridNO);
    }

    @Override
    void resetSP() {
        this.skillPoint = 0;
    }
}
