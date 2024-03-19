/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

/**
 * Class contain the positions of the enemy
 *
 * @author Lam Tan Phat - CE181023
 */
public class EneCo {

    private int xx;
    private int yy;

    /**
     * Default constructor
     */
    public EneCo() {
    }

    /**
     * Constructor of the the positions of the enemy
     *
     * @param xx coordinates x
     * @param yy coordinates y
     */
    public EneCo(int xx, int yy) {
        this.xx = xx;
        this.yy = yy;
    }

    /**
     * Get coordinates x of enemy
     *
     * @return coordinates x of enemy
     */
    public int getXx() {
        return xx;
    }

    /**
     * Set coordinates x of enemy
     *
     * @param xx coordinates x of enemy
     */
    public void setXx(int xx) {
        this.xx = xx;
    }

    /**
     * Get coordinates y of enemy
     *
     * @return coordinates y of enemy
     */
    public int getYy() {
        return yy;
    }

    /**
     * Set coordinates y of enemy
     *
     * @param yy coordinates y of enemy
     */
    public void setYy(int yy) {
        this.yy = yy;
    }

    @Override
    public String toString() {
        return "xx=" + xx + ", yy=" + yy;
    }

}
