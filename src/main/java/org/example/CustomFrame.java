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
    public CustomFrame(Player thisPlayer) throws HeadlessException {
        cl = this.getClass().getClassLoader();
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
        g.drawLine(0,87,this.getWidth(),87);
        g.drawLine(0,88,this.getWidth(),88);

        rightTankImage(g);
        leftTankImage(g);

        if(bullets.size() > 0) {
            for (Bullet bullet: bullets) {
                drawBullet(g, bullet.getX(), bullet.getY());
            }
        }
    }

    private void blockDrawImage(Graphics g) {
        InputStream url = cl.getResourceAsStream("initialIMG.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    private void heartDrawImage(Graphics g, int x) {
        InputStream url = cl.getResourceAsStream("Heart.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, x, 35, this.getWidth() / 24, this.getHeight() / 16, null);
    }

    private void rightTankImage(Graphics g) {
        InputStream url = cl.getResourceAsStream("rightTank.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, rightPlayer.getX(), rightPlayer.getY(), 100, 100, null);
    }

    private void leftTankImage(Graphics g) {
        InputStream url = cl.getResourceAsStream("LeftTank.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, leftPlayer.getX(), leftPlayer.getY(), 100, 100, null);
    }

    private void drawBullet(Graphics g, int x, int y) {
        BufferedImage img = null;
        bullet = cl.getResourceAsStream("Bullet.png");
        try {
            img = ImageIO.read(bullet);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, x, y, 40, 40, null);
    }

    public void checkBullets(Bullet b) {
        boolean presence = false;
        for (Bullet bullet: bullets) {
            if (b.getId() == bullet.getId()) {
                System.out.println("I proiettili sono uguali");
                presence = true;
                break;
            }
        }
        if (presence == false) {
            bullets.add(b);
            System.out.println("Proiettile aggiunto alla lista");
        }
        if (b.getOwner().equals("leftPlayer")) {
            repaint(b.getX() - 80, b.getY(), 40, 40);
            System.out.println("Left player exec");
        } else {
            repaint(b.getX() + 80, b.getY(), 40, 40);
            System.out.println("Right player exec");
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