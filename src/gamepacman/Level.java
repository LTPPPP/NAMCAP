/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

import static gamepacman.GamePacMan.MAP_CHOOSE;
import static gamepacman.GamePacMan.scale;
import static gamepacman.Player.totalPoint;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Class to get level from input
 *
 * @author Lam Tan Phat - CE181023
 */
public class Level {

    //Declare size of map
    public int width;
    public int height;
    public Tile[][] tiles;
    //Declare list to contains point, enemy, power
    public List<Point> point;
    public static List<Enemy> enemy;
    public List<PowerUp> power;
    public int countEnemy = 0;
    public static ArrayList<EneCo> listCo = new ArrayList<>();
    public static int count = 0;

    /**
     * Method to set the level from file(input)
     *
     * @param path
     */
    public Level(String path) {
        point = new ArrayList<>();
        enemy = new ArrayList<>();
        power = new ArrayList<>();
        try {
            BufferedImage map = ImageIO.read(getClass().getResource("/map/map_main.png"));
            switch (MAP_CHOOSE) {
                case 1:
                    map = ImageIO.read(getClass().getResource("/map/map_main1.png"));
                    break;
                case 2:
                    map = ImageIO.read(getClass().getResource("/map/map_main2.png"));
                    break;
                case 3:
                    map = ImageIO.read(getClass().getResource("/map/map_main3.png"));
                    break;
                case 4:
                    map = ImageIO.read(getClass().getResource("/map/map_main4.png"));
                    break;
                case 5:
                    map = ImageIO.read(getClass().getResource("/map/map_main.png"));
//                    map = ImageIO.read(getClass().getResource("/map/test.png"));
                    break;
            }
            //Set the size
            this.width = map.getWidth();
            this.height = map.getHeight();
            int[] pixels = new int[width * height];
            tiles = new Tile[width][height];
            //Set color of map
            map.getRGB(0, 0, width, height, pixels, 0, width);
            //Get the value from input by color
            for (int xx = 0; xx < width; xx++) {
                for (int yy = 0; yy < height; yy++) {
                    int val = pixels[xx + (yy * width)];
                    //Declare the variable to set value by color(hex color) from input
                    String hexColor = String.format("#%06X", (0xFFFFFF & val));
                    //Color code 0xFF000000__BLACK
                    if (hexColor.equals("#000000")) {
                        tiles[xx][yy] = new Tile(xx * scale, yy * scale);
                    } //Set the location of character by location in the map___center of map
                    else if (hexColor.equals("#000000")) {
                        GamePacMan.player.x = xx * scale;
                        GamePacMan.player.y = yy * scale;
                    } //Set the location of enemy by location in the map___RED
                    else if (hexColor.equals("#FF0000")) {
                        enemy.add(new Enemy(xx * scale, yy * scale));
                        countEnemy++;
                        listCo.add(new EneCo(xx, yy));
                    } else if (hexColor.equals("#00FF00")) {//Empty___green
                    } else if (hexColor.equals("#FF00FF")) {//Set the location of power by location in the map__PINK
                        power.add(new PowerUp(xx * scale, yy * scale));
                    } //Set the location of point by location in the map___OTHER___WHITE
                    else {
                        point.add(new Point(xx * scale, yy * scale));
                    }
                }
            }
            count = countEnemy;
        } catch (IOException ex) {
        }
    }

    /**
     * Method to set the value of enemy
     */
    public void tick() {
        for (int i = 0; i < enemy.size(); i++) {
            enemy.get(i).tick();
        }
    }

    /**
     * Method to draw all character
     * @param g
     */
    public void render(Graphics g) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tiles[i][j] != null) {
                    tiles[i][j].render(g);
                }
            }
        }
        for (int i = 0; i < point.size(); i++) {
            point.get(i).render(g);
        }
        for (int i = 0; i < power.size(); i++) {
            power.get(i).render(g);
        }
        for (int i = 0; i < enemy.size(); i++) {
            enemy.get(i).render(g);
        }

        //Draw the HOW_TO_PLAY on top of the map 
        g.setColor(Color.RED);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        g.drawString("Point : " + totalPoint, 0, 20);
        g.drawString("W_A_S_D or direction to move", 600, 20);
    }
}
