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

    public void Stop(){ this.isWorking = false; }

    private Command exit = new Exit(this);

    Map<String, Command> commands = new HashMap<String, Command>() {{
        put("/login", new LogIn(currentPlayer));
        put("/register", new Register());
        put("/help", new Help());
        put("/log", new Log(currentPlayer));
        put("/casino", new Casino(currentPlayer));
        put("/exit", exit);
    }};

    /**
     * запускает CLI
     */
    public void start() {
        while (isWorking) {
            String input = scanner.next();
            if (commands.containsKey(input)) { commands.get(input).execute(); }
        }
    }
}
