package Strikers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class ShootingGame extends JFrame {
    private Image bufferImage;
    private Graphics screenGraphic;

    private Image mainScreen = new ImageIcon("src/Images/game_title.gif").getImage();
    private Image loadingScreen = new ImageIcon("src/Images/selectPlane2(1).jpg").getImage();
    public static Image gameScreen = new ImageIcon("src/Images/stage(1).png").getImage();
    private boolean isMainScreen,isLoadingScreen,isGameScreen;

    public static Game game = new Game();

    //private Audio backgroundMusic;

    public ShootingGame() {
        setTitle("Shooting Strikers.Game");
        setUndecorated(true);
        setSize(Main.SCREEN_WIDTH,Main.SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);

        init();
    }

    private void init() {
        isMainScreen = true;
        isLoadingScreen = false;
        isGameScreen = false;

        //backgroundMusic = new Audio("src/audio/mainTitle.wav",true);
        //backgroundMusic.start();

        addKeyListener(new KeyListener());
    }

    private void gameStart() {
        isMainScreen = false;
        isLoadingScreen = true;

        Timer loadingTimer = new Timer();
        TimerTask loadingTask = new TimerTask() {
            @Override
            public void run() {
                //backgroundMusic.stop();
                isLoadingScreen = false;
                isGameScreen = true;
                game.start();
            }
        };
        loadingTimer.schedule(loadingTask,3000);
    }

    public void paint(Graphics g) {
        //super.paintComponents(g);
        bufferImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        screenGraphic = bufferImage.getGraphics();
        screenDraw(screenGraphic);
        if (Game.playerHP > 0) {
            g.drawImage(bufferImage,0,0,null);
        }
        else {
            //g.drawImage(Strikers.Game.gameover,0,0,null);
            Game.gameoverDraw(g);
        }
    }

    public void screenDraw(Graphics g) {
        if (isMainScreen) {
            g.drawImage(mainScreen,0,0,null);
        }
        if (isLoadingScreen) {
            g.drawImage(loadingScreen,0,0,null);
        }
        if (isGameScreen) {
            g.drawImage(gameScreen,0,0,null);
            game.gameDraw(g);
            //game.gameDraw(g);
        }
        this.repaint();
    }

    class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    game.setUp(true);
                    break;
                case KeyEvent.VK_S:
                    game.setDown(true);
                    break;
                case KeyEvent.VK_A:
                    game.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    game.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    game.setShooting(true);
                    break;
                case KeyEvent.VK_ENTER:
                    if (isMainScreen) {
                        gameStart();
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
            }
        }
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    game.setUp(false);
                    break;
                case KeyEvent.VK_S:
                    game.setDown(false);
                    break;
                case KeyEvent.VK_A:
                    game.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    game.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    game.setShooting(false);
                    break;
            }
        }
    }
}