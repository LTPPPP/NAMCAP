/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

import static gamepacman.GamePacMan.scale;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Class to contain point of game
 *
 * @author Lam Tan Phat - CE181023
 */
public class Point extends Rectangle {

    /**
     * Set the coordinates of point to console
     *
     * @param x coordinates of points
     * @param y coordinates of points
     */
    public Point(int x, int y) {
        setBounds(x + 10, y + 10, scale / 8, scale / 8);
    }

    /**
     * Method to draw the point
     *
     * @param g Graphics
     */
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(Char.spectre, x, y, scale / 2, scale / 2, null);
    }
}
