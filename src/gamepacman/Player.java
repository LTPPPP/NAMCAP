/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

import static gamepacman.GamePacMan.scale;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

/**
 * Class about player
 *
 * @author Lam Tan Phat - CE181023
 */
public class Player extends Rectangle {

    //Declare the variable of player( move, power up, animation,..)
    public boolean isPower = false;
    public boolean right, left, up, down;
    private final int speed = 4;
    private int time = 0;
    private final int timeAnim = 10;
    public int imgIndex = 0;
    private int lastDirPac = 1;
    public static double beginEat;
    public List<Enemy> enemy;
    public static Level level;
    Random random = new Random();
    public static int amoutEat = 0;
    public static int totalPoint = 0;

    //Declare the variable to contains audio of game( eat, die, lose, win,...)
    AudioClip clip = Applet.newAudioClip(getClass().getResource("/sounds/pacman_eat.wav"));
    AudioClip clip2 = Applet.newAudioClip(getClass().getResource("/sounds/pacman_die.wav"));
    AudioClip clip3 = Applet.newAudioClip(getClass().getResource("/sounds/pacman_voice.wav"));
    AudioClip clip4 = Applet.newAudioClip(getClass().getResource("/sounds/pacman_winning.wav"));

    /**
     * Set rectangle for player
     *
     * @param x coordinates x of player
     * @param y coordinates y of player
     */
    public Player(int x, int y) {
        setBounds(x, y, scale, scale);
    }

    /**
     * Set value for player
     */
    public void tick() {
        //Set move for player
        if (right && canMove(x + speed, y)) {
            x += speed;
            lastDirPac = 1;
        }
        if (left && canMove(x - speed, y)) {
            x -= speed;
            lastDirPac = -1;
        }
        if (up && canMove(x, y - speed)) {
            y -= speed;
            lastDirPac = 2;
        }
        if (down && canMove(x, y + speed)) {
            y += speed;
            lastDirPac = -2;
        }

        Level level = GamePacMan.level;
        //Makes points disappear when the character touches it
        for (int i = 0; i < level.point.size(); i++) {
            if (this.intersects(level.point.get(i))) {
                level.point.remove(i);
                clip.play();
                totalPoint += 1;
                break;
            }
        }

        //Being powerful when eat the power up
        for (int i = 0; i < level.power.size(); i++) {
            if (this.intersects(level.power.get(i))) {
                level.power.remove(i);
                beginEat = System.currentTimeMillis();
                isPower = true;
                GamePacMan.STATUS = GamePacMan.POWER;
                beginEat = System.currentTimeMillis();
                //Each point player get 10 points
                totalPoint += 10;
                break;
            }

        }

        //Game win when not left point
        if (level.point.isEmpty() || level.enemy.isEmpty()) {
            //WINNING
            GamePacMan.STATE = GamePacMan.GAME_WIN;
            clip4.play();
            clip3.play();
            return;
        }

        //Make animation for character
        time++;
        if (time == timeAnim) {
            time = 0;
            imgIndex++;
        }

        //Losing game
        if (GamePacMan.STATUS == GamePacMan.NORMAL) {
            for (int i = 0; i < Level.enemy.size(); i++) {
                Enemy enemy = Level.enemy.get(i);
                if (enemy.intersects(this)) {
                    GamePacMan.STATE = GamePacMan.GAME_OVER;
                    clip2.play();
                    return;
                }
            }
        } else {
            for (int i = 0; i < Level.enemy.size(); i++) {
                Enemy enemy = Level.enemy.get(i);
                if (enemy.intersects(this)) {
                    level.enemy.remove(i);
                    //Each enemy player get 50 points
                    totalPoint += 50;
                    clip.play();
                    return;
                }
            }
        }
        //Time of power up is 7 second
        if (System.currentTimeMillis() - beginEat > 7000) {
            isPower = false;
            GamePacMan.STATUS = GamePacMan.NORMAL;
        }
    }

    /**
     * Method to check move of the player
     *
     * @param nextx coordinates x of player want to move
     * @param nexty coordinates y of player want to move
     * @return return true if it can move, false if can not move
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
     * Make animation for character when eat power and normal
     *
     * @param g Graphics
     */
    public void render(Graphics g) {
        SpriteSheet sheet = GamePacMan.spriteSheet;
        if (!isPower) {
            switch (lastDirPac) {
                case 1:
                    g.drawImage(Char.player, x, y, width, height, null);
                    break;
                case -1:
                    g.drawImage(Char.player, x + 32, y, -width, height, null);
                    break;
                case 2:
                    g.drawImage(Char.player, x, y, width, height, null);
                    break;
                case -2:
                    g.drawImage(Char.player, x, y + 32, width, -height, null);
                    break;
                default:
                    break;
            }
        } else {
            if (isPower == true && System.currentTimeMillis() - beginEat > 3000) {
                switch (lastDirPac) {
                    case 1:
                        if ((System.currentTimeMillis() / 100) % 2 == 0) {
                            g.drawImage(Char.player, x, y, width, height, null);
                        } else {
                            g.drawImage(Char.playerPower, x, y, width, height, null);
                        }
                        break;
                    case -1:
                        if ((System.currentTimeMillis() / 100) % 2 == 0) {
                            g.drawImage(Char.player, x + 32, y, -width, height, null);
                        } else {
                            g.drawImage(Char.playerPower, x + 32, y, -width, height, null);
                        }
                        break;
                    case 2:
                        if ((System.currentTimeMillis() / 100) % 2 == 0) {
                            g.drawImage(Char.player, x, y, width, height, null);
                        } else {
                            g.drawImage(Char.playerPower, x, y, width, height, null);
                        }
                        break;
                    case -2:
                        if ((System.currentTimeMillis() / 100) % 2 == 0) {
                            g.drawImage(Char.player, x, y + 32, width, -height, null);
                        } else {
                            g.drawImage(Char.playerPower, x, y + 32, width, -height, null);
                        }
                        break;
                    default:
                        break;
                }
            } else {
                switch (lastDirPac) {
                    case 1:
                        g.drawImage(Char.playerPower, x, y, width, height, null);
                        break;
                    case -1:
                        g.drawImage(Char.playerPower, x + 32, y, -width, height, null);
                        break;
                    case 2:
                        g.drawImage(Char.playerPower, x, y, width, height, null);
                        break;
                    case -2:
                        g.drawImage(Char.playerPower, x, y + 32, width, -height, null);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
