/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gridwar;

import javax.swing.*;

public class EnemyNormal extends Character {

    public ImageIcon[] enNormal = new ImageIcon[5];
    ;
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
    private int gridNO = 4;
    private int enemyPositionX = 300; // -95
    private int enemyPositionY = 142;
    private int enemyWidth = 88;
    private int enemyHeight = 88;

    EnemyNormal() {

        for (int i = 0; i < enNormal.length; i++) {
            enNormal[i] = new ImageIcon(this.getClass().getResource("en_" + (i + 1) + ".png"));
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
            this.gridNO--;
        } else if (direction == 1) {
            this.gridNO++;
        }
    }

    @Override
    int getPositionX() {
        return this.enemyPositionX;
    }

    @Override
    int getPositionY() {
        return this.enemyPositionY;
    }

    @Override
    int getEntityWidth() {
        return this.enemyWidth;
    }

    @Override
    int getEntityHeight() {
        return this.enemyHeight;
    }

    @Override
    void setPositionX(int direction) {
        if (direction == -1) {
            this.enemyPositionX -= 95;
        } else if (direction == 1) {
            this.enemyPositionX += 95;
        }
        if (this.enemyPositionX < 10) {
            this.enemyPositionX = 680;
            this.gridNO = 8;
        } else if (this.enemyPositionX > 700) {
            this.enemyPositionX = 15;
            this.gridNO = 1;
        }
        System.out.println("EN X: " + this.enemyPositionX + " " + "EN Grid NO: " + this.gridNO);
    }

    @Override
    void resetSP() {
        this.skillPoint = 0;
    }

}
