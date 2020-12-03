package com.company.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

import com.company.classes.Gamse.Kreps;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLI {

    private final Scanner scanner = new Scanner(System.in);

    private final Gson g = new Gson();

    private player currentPlayer = null;

    public void inerface() {

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
                    register();
                    break;

                case "/login":
                    login();
                    break;

                case "/log":
                    System.out.println(g.toJson(currentPlayer));
                    break;

                case "/casino":
                    Kreps k = new Kreps();
                    k.Play(currentPlayer);

            }

        }
    }

    private void register() {
        System.out.println("Enter username:");
        String username = scanner.next();
        String filename = username + ".json";


        File f = new File("./users");
        List<String> pathnames = Arrays.asList(f.list());


        if (pathnames.contains(username)) {
            System.out.println("nickname already taken");
        } else {

            try {
                File myObj = new File("./users/" + filename);
                myObj.createNewFile();
            } catch (IOException e) {
                System.out.println("error on file creation");
            }


            String json = g.toJson(new player(username));

            try {
                FileWriter myWriter = new FileWriter("./users/" + filename);
                myWriter.write(json);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }

        }

    }

    private void login() {
        System.out.println("Enter your username:");
        String username = scanner.next();
        String filename = username + ".json";

        File f = new File("./users");
        List<String> pathnames = Arrays.asList(f.list());


        if (pathnames.contains(filename)) {

            File myObj = new File("./users/" + filename);

            String str = "";
            Scanner myReader = null;
            try {
                myReader = new Scanner(myObj);
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }

            while (myReader.hasNextLine()) {
                str += myReader.nextLine();
            }


            currentPlayer = g.fromJson(str, player.class);


        } else {
            System.out.println("user dont exsist");
        }

    }

}
