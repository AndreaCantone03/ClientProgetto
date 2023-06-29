package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomFrame extends JFrame{
    private Thread thread;
    Player leftPlayer;
    Player rightPlayer;
    List<Bullet> bullets = new ArrayList<Bullet>();
    InputStream bullet;
    ClassLoader cl;
    InputStream initialImg;
    InputStream heart;
    InputStream rightTank;
    InputStream leftTank;
    BufferedImage[] imgList;
    BufferedImage bl;
    public CustomFrame(Player thisPlayer) throws HeadlessException {
        cl = this.getClass().getClassLoader();
        initialImg = cl.getResourceAsStream("initialIMG.png");
        heart = cl.getResourceAsStream("Heart.png");
        rightTank = cl.getResourceAsStream("rightTank.png");
        leftTank = cl.getResourceAsStream("LeftTank.png");
        bullet = cl.getResourceAsStream("Bullet.png");
        try {
            imgList = new BufferedImage[]{ImageIO.read(initialImg), ImageIO.read(heart), ImageIO.read(rightTank), ImageIO.read(leftTank)};
            bl = ImageIO.read(bullet);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        this.addKeyListener(thisPlayer);
        this.getContentPane().setBackground(Color.cyan);
    }

    public void setLeftPlayer(Player leftPlayer) {
        this.leftPlayer = leftPlayer;
    }

    public void setRightPlayer(Player rightPlayer) {
        this.rightPlayer = rightPlayer;
    }

    public void paint(Graphics g) {
        if (imgList.length > 0) {
            super.paint(g);

            if (leftPlayer == null || rightPlayer == null) {
                blockDrawImage(g);
                return;
            }

            for (int i = 1; i <= leftPlayer.getHeart(); i++) {
                heartDrawImage(g, 60 * i);
            }
            for (int i = 1; i <= rightPlayer.getHeart(); i++) {
                heartDrawImage(g, (getWidth() - 50) - (60 * i));
            }

            g.setColor(Color.yellow);
            g.drawLine(0, 87, this.getWidth(), 87);
            g.drawLine(0, 88, this.getWidth(), 88);

            rightTankImage(g);
            leftTankImage(g);

            if (bullets.size() > 0) {
                for (Bullet bullet : bullets) {
                    drawBullet(g, bullet.getX(), bullet.getY());
                }
            }
        }
    }

    private void blockDrawImage(Graphics g) {
        g.drawImage(imgList[0], 0, 0, this.getWidth(), this.getHeight(), null);
    }

    private void heartDrawImage(Graphics g, int x) {
        g.drawImage(imgList[1], x, 35, this.getWidth() / 24, this.getHeight() / 16, null);
    }

    private void rightTankImage(Graphics g) {
        g.drawImage(imgList[2], rightPlayer.getX(), rightPlayer.getY(), 100, 100, null);
    }

    private void leftTankImage(Graphics g) {
        g.drawImage(imgList[3], leftPlayer.getX(), leftPlayer.getY(), 100, 100, null);
    }

    private void drawBullet(Graphics g, int x, int y) {
        g.drawImage(bl, x, y, 40, 40, null);
    }

    public void checkBullets(Bullet b) {
        boolean presence = false;
        for (Bullet bullet: bullets) {
            if (b.getId() == bullet.getId()) {
                bullet.setX(b.getX());
                presence = true;
                if (bullet.getX() <= -10 || bullet.getX() >= getWidth() + 10) {
                    bullets.remove(bullet);
                }
                break;
            }
        }
        if (presence == false) {
            bullets.add(b);
        }
        if (b.getOwner().equals("leftPlayer")) {
            repaint(b.getX() - 80, b.getY(), 40, 40);
        } else {
            repaint(b.getX() + 80, b.getY(), 40, 40);
        }
        repaint(b.getX(), b.getY(), 40, 40);
    }

    public void playerChangesData(PlayerToJson pFromJson, Player player) {
        player.setY(pFromJson.getY());
        repaint(player.getX(), player.getY(), 100, 180);
        repaint(player.getX(), player.getY() - 80, 101, 180);
        if (pFromJson.getNHeart() != player.getHeart()) {
            player.setHeart(pFromJson.getnHeart());
            repaint();
        }
    }
}