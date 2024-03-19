/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

/**
 * Class contains time and point of player when lose
 *
 * @author Lam Tan Phat - CE181023
 */
public class StaGameLose {

    //Declare the variable
    private double time;
    private int point;

    /**
     * Default constructor
     */
    public StaGameLose() {
    }

    /**
     * Constructor of class
     *
     * @param time time playing
     * @param point point of player
     */
    public StaGameLose(double time, int point) {
        this.time = time;
        this.point = point;
    }

    /**
     * Method to get time
     *
     * @return time of player
     */
    public double getTime() {
        return time;
    }

    /**
     * Method to set time
     *
     * @param time time of player
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * Method to get point
     *
     * @return point of player
     */
    public int getPoint() {
        return point;
    }

    /**
     * Method to set point
     *
     * @param point point of player
     */
    public void setPoint(int point) {
        this.point = point;
    }

}
