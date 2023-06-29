package org.example;

import javax.swing.*;

public class App {

    static CustomFrame f = null;

    public static void main(String[] args) {
        Player thisPlayer = new Player(3);
        Player otherPlayer = new Player(3);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(thisPlayer);
            }
        });
        while (f == null) {
            System.out.println("Waiting for another player...");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("Interrupted exception");
            }
        }
        new Client(thisPlayer, otherPlayer, f);
    }

    private static void createAndShowGUI(Player thisPlayer) {
        f = new CustomFrame(thisPlayer);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1220, 720);
        f.setResizable(false);
        f.setVisible(true);
    }
}