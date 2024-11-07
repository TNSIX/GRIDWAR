/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gridwar;

/**
 *
 * @author User
 */
public abstract class Character {

    abstract int getHealth();

    abstract int getSkillPoint();

    abstract int getGridNO();

    abstract int getPositionX();

    abstract int getPositionY();

    abstract int getEntityWidth();

    abstract int getEntityHeight();

    abstract void setHealth(int damage);

    abstract void setSkillPoint();

    abstract void setGridNO(int direction);

    abstract void setPositionX(int direction);

    abstract void resetSP();
}
