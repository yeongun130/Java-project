package Strikers;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends Thread {
    private int delay = 20;
    private long pretime;
    private int cnt;
    private static int score;
    private Image player = new ImageIcon("src/images/PlayerPlane(1).png").getImage();

    public static Image gameover = new ImageIcon("src/images/gameover.jpg").getImage();

    private int playerX;
    private int playerY;
    private float gamescreenscrollX,gamescreenscrollY = 1000;
    private float gamescreenscrollsX,gamescreenscrollsY = -10575;
    private int playerWidth = player.getWidth(null);
    private int playerHeight = player.getHeight(null);
    private int playerSpeed = 10;
    public static int playerHP = 15;

    private boolean up,down,left,right,shooting;
    private boolean isOver;

    private ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
    private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    private ArrayList<EnemyAttack> enemyAttackList = new ArrayList<EnemyAttack>();

    private PlayerAttack playerAttack;
    private Enemy enemy;
    private EnemyAttack enemyAttack;

    @Override
    public void run() {
        reset();
        while(true) {
            while (!isOver) {
                pretime = System.currentTimeMillis();
                if (System.currentTimeMillis() - pretime < delay) {
                    try {
                        Thread.sleep(delay - System.currentTimeMillis() + pretime);
                        keyProcess();
                        playerAttackProcess();
                        enemyAppearProcess();
                        enemyMoveProcess();
                        enemyAttackProcess();
                        cnt++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void reset() {
        isOver = false;
        cnt = 0;
        score = 0;
        playerX = (Main.SCREEN_WIDTH - playerWidth) / 2;
        playerY = (Main.SCREEN_HEIGHT - 145);

        playerAttackList.clear();
        enemyList.clear();
        enemyAttackList.clear();
    }

    private void keyProcess() {
        if(up && playerY - playerSpeed > 0)
            playerY -= playerSpeed;
        if(down && playerY + playerHeight + playerSpeed < Main.SCREEN_HEIGHT)
            playerY += playerSpeed;
        if(left && playerX + playerSpeed > 0)
            playerX -= playerSpeed;
        if(right && playerX + playerWidth + playerSpeed <Main.SCREEN_WIDTH)
            playerX += playerSpeed;
        if(shooting && cnt % 10 == 0) {
            playerAttack = new PlayerAttack(playerX + 70, playerY + 25);
            playerAttackList.add(playerAttack);
        }
    }

    private void playerAttackProcess() {
        for (int i = 0; i < playerAttackList.size(); i++) {
            playerAttack = playerAttackList.get(i);
            playerAttack.fire();

            for (int j = 0; j < enemyList.size(); j++) {
                enemy = enemyList.get(j);
                if (playerAttack.x > enemy.x && playerAttack.x < enemy.x + enemy.width
                        && playerAttack.y > enemy.y && playerAttack.y < enemy.y + enemy.height) {
                    enemy.hp -= playerAttack.attack;
                    playerAttackList.remove(playerAttack);
                }
                if (enemy.hp <= 0) {
                    enemyList.remove(enemy);
                    score += 100;
                }
            }
        }
    }

    private void enemyAppearProcess() {
        if (cnt % 40 == 0) {
            enemy = new Enemy((int)(Math.random()*400),0);
            enemyList.add(enemy);
        }
    }

    private void enemyMoveProcess() {
        for (int i = 0; i < enemyList.size(); i++) {
            enemy = enemyList.get(i);
            enemy.move();
        }
    }
    private void enemyAttackProcess() {
        if (cnt % 10 == 0) {
            enemyAttack = new EnemyAttack((int)Math.random()+enemy.x, enemy.y+25);
            enemyAttackList.add(enemyAttack);
        }

        for (int i = 0; i <enemyAttackList.size(); i++) {
            enemyAttack = enemyAttackList.get(i);
            enemyAttack.fire();
            enemyAttack.fire();
            enemyAttack.fire();
            enemyAttack.fire();
            //enemyAttack.fires();
            for (int  j = 0; j< enemyAttackList.size(); j++) {
                enemyAttack = enemyAttackList.get(j);
                if (enemyAttack.x > playerX && enemyAttack.x < playerX + enemyAttack.width
                        && enemyAttack.y > playerY && enemyAttack.y <playerY + enemy.height) {
                    playerHP -= enemyAttack.attack;
                    enemyAttackList.remove(enemyAttack);
                }
                if (playerHP <= 0) {
                    playerAttackList.remove(playerAttack);
                    isOver = true;
                }
            }

        }
    }

    public void gameDraw(Graphics g) {
        if (playerHP <= 0) {
            gameoverDraw(g);
            infoDraw(g);
        }
        gamescreenscroll(g);
        if (playerHP > 0) {
            playerDraw(g);
        }
        enemyDraw(g);
        infoDraw(g);
    }

    public void infoDraw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,40));
        g.drawString("SCORE : " + score,40,80);
    }

    public void gamescreenscroll(Graphics g){
        if(gamescreenscrollY > 1000) {
            gamescreenscrollY -= 10575;
        }
        if (gamescreenscrollsY > 1000) {
            gamescreenscrollsY -= 21150;
        }
        g.drawImage(ShootingGame.gameScreen, (int)gamescreenscrollX,(int)gamescreenscrollY,null);
        g.drawImage(ShootingGame.gameScreen, (int)gamescreenscrollsX,(int)gamescreenscrollsY,null);
        gamescreenscrollY += 0.7;
        gamescreenscrollsY += 0.7;
    }

    public void playerDraw(Graphics g) {
        g.drawImage(player,playerX,playerY,null);
        g.setColor(Color.GREEN);
        g.fillRect(playerX, playerY+70, playerHP*6,20);
        for (int i = 0; i < playerAttackList.size(); i++) {
            playerAttack = playerAttackList.get(i);
            g.drawImage(playerAttack.image,playerAttack.x,playerAttack.y,null);
        }
    }

    public void enemyDraw(Graphics g) {
        for (int i = 0; i < enemyList.size(); i++) {
            enemy = enemyList.get(i);
            g.drawImage(enemy.image, enemy.x, enemy.y, null);
            g.drawImage(enemy.images, enemy.x, enemy.y, null);
            //g.setColor(Color.GREEN);
            //g.fillRect(enemy.x + 20, enemy.y -1, enemy.hp * 15, 20);
        }

        for (int i = 0; i < enemyAttackList.size(); i++) {
            enemyAttack = enemyAttackList.get(i);
            g.drawImage(enemyAttack.image, enemyAttack.x, enemyAttack.y, null);
        }
    }

    public static void gameoverDraw(Graphics g) {
        if (playerHP <= 0) {
            g.drawImage(gameover,0,0,null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.drawString("SCORE : " + score,165,500);
        }
    }

    public boolean isOver() {
        return isOver;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

}
