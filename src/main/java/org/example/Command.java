package org.example;

public class Command {
    String command;
    Object message;

    public Command(String command, Object message) { //per le coordinate del player // o per il proiettile
        this.command = command;
        this.message = message;
    }
}
