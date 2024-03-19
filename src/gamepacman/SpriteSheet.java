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
 * Class to get image of file
 *
 * @author Lam Tan Phat - CE181023
 */
public class SpriteSheet {

    private BufferedImage sheet;

    /**
     * Method to get path of file
     *
     * @param path path of file
     */
    public SpriteSheet(String path) {
        try {
            sheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            System.out.println("fail to load the image");
        }
    }

    /**
     * Method to get sprite of character
     *
     * @param xx coordinates of image
     * @param yy coordinates of image
     * @return the sprite of character
     */
    public BufferedImage getSprite(int xx, int yy) {
        return sheet.getSubimage(xx, yy, 16, 16);
    }

}
