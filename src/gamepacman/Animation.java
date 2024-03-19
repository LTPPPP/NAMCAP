/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The class contain image of the main-player
 *
 * @author Lam Tan Phat - CE181023
 */
public final class Animation {

    //Declare the variable to get image from file
    public static BufferedImage[] enemyWidth;
    public static BufferedImage[] enemyVertical;
    public static BufferedImage[] enemyWidthPower;
    public static BufferedImage[] enemyVerticalPower;
    public BufferedImage spritesheet;

    /**
     * Method to get image from file
     */
    public Animation() {
        try {
            spritesheet = ImageIO.read(getClass().getResource("/sprites/pacman1.png"));
        } catch (IOException ex) {
        }
        enemyWidth = new BufferedImage[2];
        enemyVertical = new BufferedImage[2];
        enemyWidthPower = new BufferedImage[2];
        enemyVerticalPower = new BufferedImage[2];
        
        enemyWidth[0] = getSprite(0, 0);
        enemyWidth[1] = getSprite(16, 0);
        enemyVertical[0] = getSprite(32, 0);
        enemyVertical[1] = getSprite(48, 0);
        //Animations when power up
        enemyWidthPower[0] = getSprite(64, 0);
        enemyWidthPower[1] = getSprite(80, 0);
        enemyVerticalPower[0] = getSprite(96, 0);
        enemyVerticalPower[1] = getSprite(112, 0);

    }

    /**
     * Method to get sprite, cut the image from file
     * @param x coordinates x
     * @param y coordinates y 
     * @return the image from file
     */
    public BufferedImage getSprite(int x, int y) {
        return spritesheet.getSubimage(x, y, 16, 16);
    }

}
