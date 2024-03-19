/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

import java.awt.image.BufferedImage;

/**
 * Class to get image of all character from file
 *
 * @author Lam Tan Phat - CE181023
 */
public class Char {

    //Declare the variable to get image of all character from file
    public static BufferedImage player;
    public static BufferedImage player1;
    public static BufferedImage playerPower;
    public static BufferedImage enemy;
    public static BufferedImage enemyWeak;
    public static BufferedImage cherry;
    public static BufferedImage spectre;
    public static BufferedImage sword;

    /**
     * Method to get image of all character from file
     */
    public Char() {
        enemy = GamePacMan.spriteSheet.getSprite(0, 0);
        enemyWeak = GamePacMan.spriteSheet.getSprite(64, 0);
        player = GamePacMan.spriteSheet.getSprite(0, 16);
        player1 = GamePacMan.spriteSheet.getSprite(32, 16);
        playerPower = GamePacMan.spriteSheet.getSprite(16, 16);
        cherry = GamePacMan.spriteSheet.getSprite(0, 32);
        spectre = GamePacMan.spriteSheet.getSprite(16, 32);
        sword = GamePacMan.spriteSheet.getSprite(0, 48);

    }
}
