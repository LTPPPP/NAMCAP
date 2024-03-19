/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

import static gamepacman.GamePacMan.scale;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Class contain power up of player
 *
 * @author Lam Tan Phat - CE181023
 */
public class PowerUp extends Rectangle {

    /**
     * Set the coordinates of power to console
     *
     * @param x coordinates of power
     * @param y coordinates of power
     */
    public PowerUp(int x, int y) {
        setBounds(x, y, 20, 20);
    }

    /**
     * Method to draw th power up
     *
     * @param g Graphics
     */
    public void render(Graphics g) {
        g.drawImage(Char.sword, x, y, scale, scale, null);
    }
}
