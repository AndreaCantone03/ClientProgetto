package org.example;

public class Bullet {
    private int x;
    private int y;
    private String owner;
    private long id;

    public Bullet(double x, double y, String owner, long id) {
        this.x = (int) x;
        this.y = (int) y;
        this.owner = owner;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String string) {
        this.owner = string;
    }

    public int getX(){
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

}