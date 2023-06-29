package org.example;

public class PlayerToJson {
    /* La funzione di questa classe è quella di fare da tramite per le comunicazione JSON tra server e client.
    Il motivo di ciò è dato dal fatto che i JSON passano solo le coordinate ed il numero di cuori. Quindi non posso estrapolare dai JSON
    usando come riferimento la classe Player.
    PS: Ricordo che in player sono presenti altre variabili globali che al server non servono come CustonFrame f.
    */
    private int nHeart;
    private int x;
    private int y;
    private boolean shot;

    public PlayerToJson(int nHeart, double x, double y) {
        this.nHeart = nHeart;
        this.x = (int) x;
        this.y = (int) y;
    }

    public int getnHeart() {
        return nHeart;
    }

    public void setnHeart(int nHeart) {
        this.nHeart = nHeart;
    }

    public boolean isShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public int getNHeart() {
        return nHeart;
    }

    public void setNHeart(int nHeart) {
        this.nHeart = nHeart;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = (int) x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = (int) y;
    }
}
