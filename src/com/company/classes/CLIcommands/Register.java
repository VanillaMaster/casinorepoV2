package com.company.classes.CLIcommands;

import com.company.classes.legacy.Player;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * комманда регистрации нового пользователя
 */
public class Register implements Command{

    private final Scanner scanner = new Scanner(System.in);
    private final Gson gson = new Gson();
    FileWriter writer;

    public void execute() {
        System.out.println("Enter username:");
        String username = scanner.next();
        String filename = username + ".json";


        File f = new File("./users");
        List<String> pathnames = Arrays.asList(f.list());


        if (pathnames.contains(username)) {
            System.out.println("nickname already taken");
        } else {


            if (new File("./users/" + filename).exists()){
                System.out.println("User already exist");
            } else {
                try {
                    writer = new FileWriter("./users/" + filename);
                    gson.toJson(new Player(username), writer);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    System.out.println("error on File writing.");
                }
            }

        }

    }
}
