package Strikers;

import javax.swing.*;
import java.awt.*;

public class Enemy {
    Image image  = new ImageIcon("src/images/enemy_1.png").getImage();
    Image images = new ImageIcon("src/images/enemy_3.png").getImage();
    int x, y;
    int width = image.getWidth(null);
    int height = image.getHeight(null);

    int widths = images.getWidth(null);
    int heights = images.getHeight(null);
    int hp = 15;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        this.x += 3;
        this.y += 15;
    }
}
