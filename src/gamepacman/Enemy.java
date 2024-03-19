/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

import static gamepacman.GamePacMan.player;
import static gamepacman.GamePacMan.scale;
import static gamepacman.Level.count;
import static gamepacman.Player.beginEat;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class contain all method of the enemy
 *
 * @author Lam Tan Phat - CE181023
 */
public class Enemy extends Rectangle {

    //Declare the variable to get the random move for enemy
    private final int random = 0;
    Random randome = new Random();
    int randomNumber;

    //Declare the variable of direction of the enemy
    private final int right = 1;
    private final int left = 2;
    private final int up = 3;
    private final int down = 0;
    private int dir = -1;
    private int lastdir = 10;

    //Declare the variable for animation of character
    private int time = 0;
    private final int timeAnim = 10;
    public int imgIndex = 0;
    
    //Declare the random move for enemy
    public Random randomEne;
    //Declare speed for enemy
    public int spd = 2;
    //Declare the coordinates for revie enemy
    static int xrev;
    static int yrev;
    List<Enemy> enemy;
    private final List<Enemy> enemyList;
    boolean move = false;
    
    ArrayList<Integer> listCantMove = new ArrayList<>();

    /**
     * Method to get direction and coordinates of enemy
     * @param x x coordinates of enemy
     * @param y y coordinates of enemy
     */
    public Enemy(int x, int y) {
        randomEne = new Random();
        setBounds(x, y, scale, scale);
        dir = randomEne.nextInt(4);
        enemyList = Level.enemy;
    }

    /**
     * Method to call movement of enemy
     */
    public void tick() {
        if (GamePacMan.STATUS == GamePacMan.NORMAL) {
            aIMove();
        } else if (GamePacMan.STATUS == GamePacMan.POWER) {
            move();
        }
    }

    /**
     * Method to Chasing player 
     */
    public void aIMove() {
        boolean canmove = false;
        if (x == player.x) {
            if (y < player.y) {
                if (canMove(x, y + spd)) {
                    y += spd;
                    dir = down;
                    canmove = true;
                } else {
                    dir = randomEne.nextInt(4);
                }
            } else {
                if (canMove(x, y - spd)) {
                    y -= spd;
                    dir = up;
                    canmove = true;
                } else {
                    dir = randomEne.nextInt(4);
                }
            }
        } else if (y == player.y) {
            if (x < player.x) {
                if (canMove(x + spd, y)) {
                    x += spd;
                    dir = right;
                    canmove = true;
                } else {
                    dir = randomEne.nextInt(4);
                }
            } else {
                if (canMove(x - spd, y)) {
                    x -= spd;
                    dir = left;
                    canmove = true;
                } else {
                    dir = randomEne.nextInt(4);
                }
            }
        }
        if (!canmove || GamePacMan.STATE == GamePacMan.POWER) {
            move();
        }
    }

    /**
     * Method to make enemy move random
     */
    public void move() {
        switch (dir) {
            case right:
                if (canMove(x + spd, y)) {
                    x += spd;
                } else {
                    dir = randomEne.nextInt(4);
                }
                break;
            case left:
                if (canMove(x - spd, y)) {
                    x -= spd;
                } else {
                    dir = randomEne.nextInt(4);
                }
                break;
            case down:
                if (canMove(x, y + spd)) {
                    y += spd;
                } else {
                    dir = randomEne.nextInt(4);
                }
                break;
            case up:
                if (canMove(x, y - spd)) {
                    y -= spd;
                } else {
                    dir = randomEne.nextInt(4);
                }
                break;
        }
    }

    /**
     *  Method to check direction for enemy can move or not
     * @param nextx x coordinates
     * @param nexty y coordinates
     * @return true if that direction can move, false when can not move
     */
    private boolean canMove(int nextx, int nexty) {
        Rectangle bounds = new Rectangle(nextx, nexty, width, height);
        Level level = GamePacMan.level;
        for (Tile[] tile : level.tiles) {
            for (int j = 0; j < level.tiles[0].length; j++) {
                if (tile[j] != null) {
                    if (bounds.intersects(tile[j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method to draw the enemy
     * @param g Graphics 
     */
    public void render(Graphics g) {
        //Make animation for character
        time++;
        if (time == timeAnim) {
            time = 0;
            imgIndex++;
        }
        //Check the status of the player
        switch (GamePacMan.STATUS) {
            case GamePacMan.POWER:
                spd = 4;
                //Power only in 3s
                if (GamePacMan.STATUS == GamePacMan.POWER && System.currentTimeMillis() - beginEat > 3000) {
                    //Check direction to draw the animation
                    switch (dir) {
                        case 1:
                            if ((System.currentTimeMillis() / 100) % 2 == 0) {
                                g.drawImage(Animation.enemyWidth[imgIndex % 2], x, y, width, height, null);
                            } else {
                                g.drawImage(Animation.enemyWidthPower[imgIndex % 2], x, y, width, height, null);
                            }
                            break;
                        case 2:
                            if ((System.currentTimeMillis() / 100) % 2 == 0) {
                                g.drawImage(Animation.enemyWidth[imgIndex % 2], x + 32, y, -width, height, null);
                            } else {
                                g.drawImage(Animation.enemyWidthPower[imgIndex % 2], x + 32, y, -width, height, null);
                            }
                            break;
                        case 3:
                            if ((System.currentTimeMillis() / 100) % 2 == 0) {
                                g.drawImage(Animation.enemyVertical[imgIndex % 2], x, y, width, height, null);
                            } else {
                                g.drawImage(Animation.enemyVerticalPower[imgIndex % 2], x, y, width, height, null);
                            }
                            break;
                        case 0:
                            if ((System.currentTimeMillis() / 100) % 2 == 0) {
                                g.drawImage(Animation.enemyVertical[imgIndex % 2], x, y + 32, width, -height, null);
                            } else {
                                g.drawImage(Animation.enemyVerticalPower[imgIndex % 2], x, y + 32, width, -height, null);
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (dir) {
                        case 1:
                            g.drawImage(Animation.enemyWidthPower[imgIndex % 2], x, y, width, height, null);
                            break;
                        case 2:
                            g.drawImage(Animation.enemyWidthPower[imgIndex % 2], x + 32, y, -width, height, null);
                            break;
                        case 3:
                            g.drawImage(Animation.enemyVerticalPower[imgIndex % 2], x, y, width, height, null);
                            break;
                        case 0:
                            g.drawImage(Animation.enemyVerticalPower[imgIndex % 2], x, y + 32, width, -height, null);
                            break;
                        default:
                            break;
                    }
                }
                break;

            default:
                //Reset speed of enemy
                spd = 2;
                //Revie the enemy
                if (Level.enemy.size() < count) {
                    for (int i = 0; i < count - Level.enemy.size(); i++) {
                        randomNumber = randome.nextInt(count) + 1;
                        xrev = Level.listCo.get(randomNumber).getXx();
                        yrev = Level.listCo.get(randomNumber).getYy();
                        rev(g);
                        enemyList.add(new Enemy(xrev * scale, yrev * scale));
                    }
                }
                switch (dir) {
                    case 1:
                        g.drawImage(Animation.enemyWidth[imgIndex % 2], x, y, width, height, null);
                        break;
                    case 2:
                        g.drawImage(Animation.enemyWidth[imgIndex % 2], x + 32, y, -width, height, null);
                        break;
                    case 3:
                        g.drawImage(Animation.enemyVertical[imgIndex % 2], x, y, width, height, null);
                        break;
                    case 0:
                        g.drawImage(Animation.enemyVertical[imgIndex % 2], x, y + 32, width, -height, null);
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    /**
     * Method to make enemy revival 
     * @param g Graphics
     */
    public void rev(Graphics g) {
        //Get coordinates of enemy from map( input ) then revie enemy on that
        g.drawImage(Char.enemy, xrev * scale, yrev * scale, width, height, null);
    }
}
