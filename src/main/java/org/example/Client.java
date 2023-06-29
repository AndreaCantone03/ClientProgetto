package org.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    static Player thisPlayer;
    static Player otherPlayer;
    static CustomFrame f;
    static PrintWriter out;
    static BufferedReader in;
    static PlayerToJson thisP = null;
    static PlayerToJson otherP = null;
    static Gson g = new Gson();

    public Client(Player thisPlayer, Player otherPlayer, CustomFrame f) {
        this.thisPlayer = thisPlayer;
        this.otherPlayer = otherPlayer;
        this.f = f;
        clientMain();
    }

    public Client() {}

    static void clientMain() {
        String hostName = "127.0.0.1";
        int portNumber = 1234;
        Socket echoSocket = null;

        try {
            echoSocket = new Socket(hostName, portNumber);
        } catch (IOException e) {
            System.out.println("cannot reach server " + e);
        }

        try {
            assert echoSocket != null;
            out = new PrintWriter(echoSocket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("YOU MUST CONNECT THE SERVER!!");
        }

        try {
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("cannot allocate bufferedreader");
        }

        //Inizializzazione della partita
        try {
            /*SPACCHETTAMENTO JSON NELLE COORDINATE*/
            String paramIniziali = in.readLine();
            System.out.println(paramIniziali);
            thisP = g.fromJson(paramIniziali, PlayerToJson.class);
            System.out.println(thisP);
            paramIniziali = in.readLine();
            otherP = g.fromJson(paramIniziali, PlayerToJson.class);
            System.out.println(otherP);
        } catch (Exception e) {
            System.out.println("Si è verificato un errore per via del messaggio ricevuto oppure il server non è connesso");
        }
        //Coordinate dei giocatori prese dal server
        setCoordinatesFromP();
        thisPlayer.setF(f);
        otherPlayer.setF(f);
        f.repaint();

        if (thisPlayer.getX() < 500) {
            f.setLeftPlayer(thisPlayer);
            f.setRightPlayer(otherPlayer);
        } else {
            f.setLeftPlayer(otherPlayer);
            f.setRightPlayer(thisPlayer);
        }
        //Fine inizializzazione

        while (true) {
            try {
                String s;
                if ((s = in.readLine()) != null) {
                    System.out.println("Incoming: "+s);
                    Command cmd = g.fromJson(s, Command.class);
                    if (cmd.command.equals("thisPlayer")) {
                        otherP = g.fromJson((String) cmd.message, PlayerToJson.class);
                        System.out.println("thisPlayer incoming");
                        f.playerChangesData(thisP, thisPlayer);
                    } else if (cmd.command.equals("otherPlayer")) {
                        otherP = g.fromJson((String) cmd.message, PlayerToJson.class);
                        System.out.println("otherPlayer incoming");
                        f.playerChangesData(otherP, otherPlayer);
                    } else if (cmd.command.equals("bullet")) {
                        Bullet b = g.fromJson((String) cmd.message, Bullet.class);
                        System.out.println("Bullet incoming");
                        f.checkBullets(b);
                    } else {
                        System.out.println("Message not recognized");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error");
                break;
            }
        }
    }

    public static void setCoordinatesFromP() {
        thisPlayer.setY(thisP.getY());
        thisPlayer.setX(thisP.getX());
        thisPlayer.setHeart(thisP.getNHeart());
        otherPlayer.setY(otherP.getY());
        otherPlayer.setX(otherP.getX());
        otherPlayer.setHeart(otherP.getNHeart());
        System.out.println("This player: {x: "+thisPlayer.getX()+" y: "+thisPlayer.getY()+"}");
        System.out.println("Other player: {x: "+otherPlayer.getX()+" y: "+otherPlayer.getY()+"}");
    }

    public static void sendPlayerData(boolean shot) {
        thisP.setX(thisPlayer.getX());
        thisP.setY(thisPlayer.getY());
        thisP.setNHeart(thisPlayer.getHeart());
        thisP.setShot(shot);
        System.out.println("Sending this player "+g.toJson(thisP)+" to the server");
        out.println(g.toJson(thisP));
    }
}