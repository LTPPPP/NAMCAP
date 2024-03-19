/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

import static gamepacman.GamePacMan.scale;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Class to contains the map
 *
 * @author Lam Tan Phat - CE181023
 */
public class Tile extends Rectangle {

    /**
     * Set rectangle for map
     *
     * @param x coordinates x of map
     * @param y coordinates y of map
     */
    public Tile(int x, int y) {
        setBounds(x, y, scale, scale);
    }

    /**
     * Method to draw the map
     *
     * @param g Graphics
     */
    public void render(Graphics g) {
        // Fill the inner part with the desired color
        g.setColor(new Color(23, 33, 92)); // Use your desired color here
        g.fillRect(x, y, width, height);
    }
}
