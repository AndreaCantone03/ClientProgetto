package org.example;

import java.awt.event.*;

public class Player extends Client implements KeyListener {
    private int heart;
    private int x;
    private int y;
    private CustomFrame f;
    private long lastPressProcessedUP = 0;
    private long lastPressProcessedDown = 0;
    private long lastPressProcessedSpace = 0;

    public Player(int heart) {
        this.heart = heart;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 38: //tasto freccia su
                if (this.y > 120 && (System.currentTimeMillis() - lastPressProcessedUP) > 550) {
                    this.y -= 80;
                    Client.sendPlayerData(false);
                    f.repaint(this.getX(), this.getY(), 101, 180);
                    lastPressProcessedUP = System.currentTimeMillis();
                }
                break;
            case 40: //tasto freccia giù
                if (this.y < (this.f.getHeight() - 140) && (System.currentTimeMillis() - lastPressProcessedDown) > 550) {
                    y += 80;
                    Client.sendPlayerData(false);
                    f.repaint(this.getX(), this.getY() - 80, 101, 180);
                    lastPressProcessedDown = System.currentTimeMillis();
                    break;
                }
            case 32:
                if ((System.currentTimeMillis() - lastPressProcessedSpace) > 5000) {
                    System.out.println("cliccato space!");
                    try {
                        Client.sendPlayerData(true);
                    } catch (Exception ex) {
                        System.out.println("Can't fire!");
                    }
                    lastPressProcessedSpace = System.currentTimeMillis();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public void setF(CustomFrame f) {
        this.f = f;
    }
}