package Strikers;

import javax.swing.*;
import java.awt.*;

public class EnemyAttack {
    Image image = new ImageIcon("src/images/Bullet1 (1).png").getImage();
    int x,y;
    int width = image.getWidth(null);
    int height = image.getHeight(null);
    int attack = 5;

    public EnemyAttack(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void fire() {
        this.y += 8;
    }
}
