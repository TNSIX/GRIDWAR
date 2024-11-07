/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gridwar;

import javax.swing.*;

public class DrivenProgram{
    public static void main(String[] args){
        JFrame gameFrame = new GameMenu();
        gameFrame.setTitle("Grid War");
        gameFrame.setSize(800, 800);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
    }
}
