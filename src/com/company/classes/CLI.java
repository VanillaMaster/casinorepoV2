package com.company.classes;

import com.company.classes.commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * интерфейс командных строк
 */
public class CLI {

    private final Scanner scanner = new Scanner(System.in);
    private Player currentPlayer = new Player();
    private boolean isWorking = true;
    private CLI IThis = this;

    /**
     * заканчивает работу CIL
     */
    public void Stop(){ this.isWorking = false; }


    Map<String, Command> commands = new HashMap<String, Command>() {{
        put("/login", new LogIn(currentPlayer));
        put("/register", new Register());
        put("/help", new Help());
        put("/log", new Log(currentPlayer));
        put("/Kreps", new CasinoKreps(currentPlayer));
        put("/Slot", new CasinoSlot(currentPlayer));
        put("/exit", new Exit(IThis));
        put("/info", new Info(currentPlayer));
    }};

    /**
     * запускает CLI
     */
    public void start() {
        while (isWorking) {
            String input = scanner.next();
            if (commands.containsKey(input)) { commands.get(input).execute(); }
            else {System.out.println("unknown command, \"/help\" for command list");}
        }
    }
}
