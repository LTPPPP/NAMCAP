/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

import static gamepacman.GamePacMan.listTimeTotal;
import static gamepacman.GamePacMan.listTotalPoint;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to record point, time of player
 * @author Lam Tan Phat - CE181023
 */
public class GameStatistics {

    /**
     * Method to record point, time of player then write it to file
     */
    public void writeFile() {
        try {
            FileWriter out = new FileWriter("stat.txt");
            out.write("--- STATISTICS OF PLAYER ---(SECOND)\n");
            for (int i = 0; i < listTimeTotal.size(); i++) {
                String ns = "";
                out.write("Player no. " + (i + 1) + " :  point = " + listTotalPoint.get(i) + "pt __ time = " + (ns += listTimeTotal.get(i)));
            }
            out.close();
        } catch (IOException e) {
        }
    }
}
