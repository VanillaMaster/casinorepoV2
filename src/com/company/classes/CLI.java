package com.company.classes;

import com.company.classes.Gamse.Kreps;

import java.util.Scanner;

/**
 * интерфейс командных строк
 */
public class CLI {

    private final Scanner scanner = new Scanner(System.in);
    private Player currentPlayer = null;
    Kreps k = new Kreps();
    CLICommands commands = new CLICommands();

    /**
     * запускает CLI
     */
    public void start() {
        boolean isWorking = true;
        while (isWorking) {

            String input = scanner.next();

            switch (input) {
                case "/exit":
                    isWorking = false;
                    break;

                case "/help":
                    System.out.println("\"/help\" - show this info\n\"/exit\" - exit bot/trogramm/idk\n\"/register\" - register new user");
                    break;

                case "/register":
                    commands.register();
                    break;

                case "/login":
                    currentPlayer = commands.login();
                    break;

                case "/casino":
                    if (currentPlayer!=null){
                        k.play(currentPlayer);
                    } else {
                        System.out.println("u should login first");
                    }
            }

        }
    }

}
