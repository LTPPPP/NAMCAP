/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepacman;

import static gamepacman.Player.totalPoint;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * NAMPAC ^_^
 *
 * @author Lam Tan Phat - CE181023
 */
public class GamePacMan extends Canvas implements Runnable, KeyListener {

    //Declare the size of the window to display the game
    public static final int WIDTH = 960;
    public static final int HEIGHT = 720;

    //The title of the game
    public static final String TITLE = "NAMCAP";
    //Declare variable to run the game
    private boolean isRunning = false;
    private Thread thread;
    public static Player player;
    public static Level level;
    public static SpriteSheet spriteSheet;
    public boolean playAgain = false;
    public int countGame = 0;
    public double timeBegin;
    public double timeEnd;
    public double totalTime;
    public double timeBeginEat;
    public double timeEndEat;
    public double totalTimeEat;
    private long quitStartTime = 0;
    public static int scale = 32;

    //Declare the variable of STATE of GAME
    public static final int GAME_PAUSE = 0;
    public static final int GAME = 1;
    public static final int GAME_WIN = 2;
    public static final int GAME_OVER = 3;
    public static final int GAME_STAT = 4;
    public static final int GAME_MENU = 5;
    public static final int GAME_HOWTOPLAY = 6;
    public static final int GAME_ABOUT = 7;
    public static final int GAME_QUIT = 8;
    public static int STATE = -1;

    //Declare the variable of STATUS of player(main character)
    public static final int NORMAL = 1;
    public static final int POWER = 2;
    public static int STATUS = 0;

    //Declare the variable of chosing MAP
    public static final int MAP_1 = 1;
    public static final int MAP_2 = 2;
    public static final int MAP_3 = 3;
    public static final int MAP_4 = 4;
    public static final int MAP_5 = 5;
    public static int MAP_CHOOSE = 0;

    //Declare the arraylist for contain the value of time, point,(lose-win) of player
    public static ArrayList<StaGameWin> listTimeWin = new ArrayList<>();
    public static ArrayList<StaGameLose> listTimeLose = new ArrayList<>();
    public static ArrayList<String> listTimeTotal = new ArrayList<>();
    public static ArrayList<Integer> listTotalPoint = new ArrayList<>();

    //Declare the variable to play music/audio when start the game
    AudioClip clip = Applet.newAudioClip(getClass().getResource("/sounds/pacman_theme.wav"));

    /**
     * Method to set size of game, STATE, data, image
     */
    public GamePacMan() {
        Dimension dimension = new Dimension(GamePacMan.WIDTH, GamePacMan.HEIGHT);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);

        addKeyListener(this);

        Animation animation = new Animation();

        //Set the STATE for the game
        STATE = GAME_MENU;
        // Add player to game data
        spriteSheet = new SpriteSheet("/sprites/pacman1.png");

        Char aChar = new Char();

    }

    /**
     * Method to start the game
     */
    public synchronized void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Method to stop the game
     */
    public synchronized void stop() {
        if (isRunning) {
            return;
        }
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Method to display the game, call the method
     */
    public void tick() {
        if (STATE == GAME) {
            player.tick();
            level.tick();
            if (timeBegin == 0) {
                timeBegin = System.currentTimeMillis();
            }
        } // Play again
        else if (STATE == GAME_PAUSE || STATE == GAME_OVER || STATE == GAME_WIN || STATE == GAME_STAT || STATE == GAME_MENU || STATE == GAME_ABOUT || STATE == GAME_HOWTOPLAY) {
            clip.stop();
            // reset the value to play game again
            if (STATE == GAME_WIN || STATE == GAME_STAT || STATE == GAME_OVER) {
                if (timeEnd == 0 && STATE == GAME_WIN) {
                    timeEnd = System.currentTimeMillis();
                    totalTime = (timeEnd - timeBegin) / 1000;
                    listTimeWin.add(new StaGameWin(totalTime, totalPoint));
                    String s = "";
                    s += totalTime + "-Win";
                    listTimeTotal.add(s);
                } else if (timeEnd == 0 && STATE == GAME_OVER) {
                    timeEnd = System.currentTimeMillis();
                    totalTime = (timeEnd - timeBegin) / 1000;
                    listTimeLose.add(new StaGameLose(totalTime, totalPoint));
                    String s = "";
                    s += totalTime + "-Lose";
                    listTimeTotal.add(s);
                }
                listTotalPoint.add(totalPoint);
            }//Pick map again
            switch (MAP_CHOOSE) {
                case MAP_1:
                    Level.listCo.clear();
                    level = new Level("/map/map_main1.png");
                    break;
                case MAP_2:
                    Level.listCo.clear();
                    level = new Level("/map/map_main2.png");
                    break;
                case MAP_3:
                    Level.listCo.clear();
                    level = new Level("/map/map_main3.png");
                    break;
                case MAP_4:
                    Level.listCo.clear();
                    level = new Level("/map/map_main4.png");
                    break;
                case MAP_5:
                    Level.listCo.clear();
                    level = new Level("/map/map_main.png");
//                    level = new Level("/map/test.png");
                    break;
            }
            if (playAgain) {
                totalPoint = 0;
                clip.loop();
                timeBegin = 0;
                timeEnd = 0;
                STATE = GAME;
                STATUS = NORMAL;
                playAgain = false;
                player = new Player(GamePacMan.WIDTH / 2, GamePacMan.HEIGHT / 2);
                switch (MAP_CHOOSE) {
                    case MAP_1:
                        level = new Level("/map/map_main1.png");
                        break;
                    case MAP_2:
                        level = new Level("/map/map_main2.png");
                        break;
                    case MAP_3:
                        level = new Level("/map/map_main3.png");
                        break;
                    case MAP_4:
                        level = new Level("/map/map_main4.png");
                        break;
                    case MAP_5:
                        level = new Level("/map/map_main.png");
                        break;
                }
            }
        }
    }

    /**
     * Method to render and display the image of the character to console
     */
    public void render() {
        // create the BufferStrategy's object ( khởi tạo bộ đệm )
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        // Set the color for the panel
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, GamePacMan.WIDTH, GamePacMan.HEIGHT);

        int screenWitdh = 200;
        int screenHeight = 200;
        int xs = GamePacMan.WIDTH / 2 - screenWitdh / 2;
        int ys = GamePacMan.HEIGHT / 2 - screenHeight / 2;
        // Check which is STATE of game then call method of that STATE  
        switch (STATE) {
            case GAME://Play the game
                player.render(g);
                level.render(g);
                break;
            case GAME_MENU:// Display the menu of game
                // Draw the image on the screen
                g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/sprites/BG_MAIN.jpg")), 0, 0, WIDTH, HEIGHT, null);
                break;
            case GAME_HOWTOPLAY:// Display the guide of the player
                g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/sprites/BG_HOWTOPLAY.jpg")), 0, 0, WIDTH, HEIGHT, null);
                break;
            case GAME_ABOUT:
                g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/sprites/BG_ABOUT.jpg")), 0, 0, WIDTH, HEIGHT, null);
                break;
            case GAME_QUIT://Display the frame thank you player then quit the game
                if (quitStartTime == 0) {
                    quitStartTime = System.currentTimeMillis();
                }
                g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/sprites/BG_QUIT.jpg")), 0, 0, WIDTH, HEIGHT, null);
                // Check if 10 seconds have elapsed
                GameStatistics stats = new GameStatistics();
                stats.writeFile(); // Save statistics to file
                if (System.currentTimeMillis() - quitStartTime >= 5000) {
                    // If 10 seconds have elapsed, exit the game
                    System.exit(0);
                }
                break;
            case GAME_PAUSE:// Display the frame for player chosing map
                // Choosing map
                g.setColor(new Color(141, 201, 239));
                g.fillRect(0, 0, GamePacMan.WIDTH, GamePacMan.HEIGHT);
                if ((System.currentTimeMillis() / 1000) % 2 < 1) {
                    g.drawImage(Char.player, 20, 0, 256, 256, null);
                } else {
                    g.drawImage(Char.player1, 20, 0, 256, 256, null);
                }
                g.setColor(Color.yellow);
                g.setFont(new Font(Font.DIALOG, Font.BOLD, 100));
                g.drawString("NAM-CAP", xs - 100, ys - 100);
                g.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
                g.drawString("Choosing map to play", xs - 40, ys);
                g.drawString("Map 1---Press 1", xs - 30, ys + 70);
                g.drawString("Map 2---Press 2", xs - 30, ys + 140);
                g.drawString("Map 3---Press 3", xs - 30, ys + 210);
                g.drawString("Map 4---Press 4", xs - 30, ys + 280);
                g.drawString("Map 5---Press 5 (RECOMMEND)", xs - 30, ys + 350);
                g.drawString("Press M to return to the menu", 520, 700);
                //Make flashes after 0.2 second
                if ((System.currentTimeMillis() / 200) % 2 == 0) {
                    g.drawImage(Char.spectre, 0, 450, 266, 256, null);
                }
                if ((System.currentTimeMillis() / 200) % 2 == 0) {
                    g.drawImage(Char.cherry, 620, 200, 266, 256, null);
                }
                break;
            case GAME_WIN:// Display the frame when the player win the game
                g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/sprites/BG_WIN.jpg")), 0, 0, WIDTH, HEIGHT, null);
                g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
                g.setColor(Color.GREEN);
                g.drawString("GGWP_EZ", xs - 50, ys);
                g.setColor(Color.black);
                g.drawString("Your time : " + totalTime + " s ", xs - 100, ys + 70);
                g.drawString("Your point : " + totalPoint + "pt", xs - 100, ys + 120);
                break;
            case GAME_OVER:// Display the frame when the player lose the game
                g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/sprites/BG_LOSE.jpg")), 0, 0, WIDTH, HEIGHT, null);
                g.setColor(Color.red);
                g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
                g.drawString("TOO NOOB 🤡", xs - 50, ys);
                g.setColor(Color.black);
                g.drawString("Your time : " + totalTime + " s ", xs - 100, ys + 70);
                g.drawString("Your point : " + totalPoint + "pt", xs - 100, ys + 120);
                break;
            case GAME_STAT:// Display the statistics of the player
                int a = 100;
                ArrayList<StaGameWin> listTimeTop = new ArrayList<>(listTimeWin);
                listTimeTop.sort((s1, s2) -> {
                    int pointComparison = Integer.compare(s1.getPoint(), s2.getPoint());
                    if (pointComparison != 0) {
                        return pointComparison;
                    } else {
                        // If points are the same, sort by time
                        return Double.compare(s1.getTime(), s2.getTime());
                    }
                });
                g.setColor(Color.yellow);
                g.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
                g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/sprites/BG_STAT.jpg")), 0, 0, WIDTH, HEIGHT, null);
                if (listTimeTop.size() <= 5) {
                    for (int i = 0; i < listTimeTop.size(); i++) {
                        g.drawString("TOP" + (i + 1) + " : " + listTimeTop.get(i).getPoint() + " pt__" + listTimeWin.get(i).getTime() + " s ", 50, a += 70);
                    }
                } else {
                    for (int i = 0; i < 5; i++) {
                        g.drawString("TOP" + (i + 1) + " : " + listTimeTop.get(i).getPoint() + " pt__" + listTimeWin.get(i).getTime() + " s ", 50, a += 70);
                    }
                }
                break;
        }
        // Display the color for the panel
        g.dispose();
        bs.show();
    }

    /**
     * Method run the game when start
     */
    @Override
    public void run() {
        int fps = 0;
        double timer = System.currentTimeMillis();
        long LastTime = System.nanoTime();
        double targetTick = 60;
        double delta = 0;
        // Change time to mili second
        double ns = 1000000000 / targetTick;

        while (isRunning) {
            // Caculate the fps( frame per second ) when running the game
            long now = System.nanoTime();
            delta += (now - LastTime) / ns;
            LastTime = now;
            while (delta >= 1) {
                tick();
                render();
                fps++;
                delta--;
            }
            // Refresh frame
            if (System.currentTimeMillis() - timer >= 1000) {
                fps = 0;
                timer += 1000;
            }
        }
        // Stop the game
        stop();
    }

    /**
     * The main method
     *
     * @param args
     */
    public static void main(String[] args) {
        // Create object method for main method
        GamePacMan game = new GamePacMan();
        JFrame frame = new JFrame();
        frame.setTitle(GamePacMan.TITLE);
        // Add object to game
        frame.add(game);
        frame.pack();
        // To close the console of the game by hit the close button
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Write a file when closing
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameStatistics stats = new GameStatistics();
                stats.writeFile(); // Save statistics to file
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // to start the game
        game.start();
    }

    /**
     * Method get the signal when player press the keyboard
     *
     * @param e is KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (STATE) {
            // Press enter to start the game
            case GAME:
                // Move using navigation buttons
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.right = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.left = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    player.up = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    player.down = true;
                } // Move with W, A, S, D
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    player.right = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    player.left = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    player.up = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    player.down = true;
                }
                break;
            case GAME_OVER:
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    STATE = GAME_MENU;
                }
            case GAME_WIN:
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    STATE = GAME_MENU;
                }
            case GAME_STAT:
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    playAgain = true;
                } else if (e.getKeyCode() == KeyEvent.VK_F) {
                    STATE = GAME_STAT;
                }
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    MAP_CHOOSE = MAP_1;
                    playAgain = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    MAP_CHOOSE = MAP_2;
                    playAgain = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    MAP_CHOOSE = MAP_3;
                    playAgain = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_4) {
                    MAP_CHOOSE = MAP_4;
                    playAgain = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_5) {
                    MAP_CHOOSE = MAP_5;
                    playAgain = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    STATE = GAME_MENU;
                }
                break;
            case GAME_PAUSE:
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    MAP_CHOOSE = MAP_1;
                    playAgain = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    MAP_CHOOSE = MAP_2;
                    playAgain = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    MAP_CHOOSE = MAP_3;
                    playAgain = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_4) {
                    MAP_CHOOSE = MAP_4;
                    playAgain = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_5) {
                    MAP_CHOOSE = MAP_5;
                    playAgain = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    STATE = GAME_MENU;
                }
                break;
            case GAME_MENU:
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    STATE = GAME_PAUSE;
                }
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    STATE = GAME_HOWTOPLAY;
                }
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    STATE = GAME_ABOUT;
                }
                if (e.getKeyCode() == KeyEvent.VK_4) {
                    STATE = GAME_QUIT;
                }
                break;
            case GAME_HOWTOPLAY:
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    STATE = GAME_MENU;
                }
                break;
            case GAME_ABOUT:
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    STATE = GAME_MENU;
                }
                break;
        }
    }

    /**
     * Method get the signal when player release the keyboard
     *
     * @param e is KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Move using navigation buttons
        if (STATE == GAME) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.right = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                player.left = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                player.up = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                player.down = false;
            }
            // Move with W, A, S, D
            if (e.getKeyCode() == KeyEvent.VK_D) {
                player.right = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                player.left = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                player.up = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                player.down = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
