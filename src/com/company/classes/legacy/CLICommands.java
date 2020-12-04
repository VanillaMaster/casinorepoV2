package com.company.classes.legacy;

import com.company.classes.Player;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLICommands {

    private final Scanner scanner = new Scanner(System.in);
    private final Gson gson = new Gson();
    private Scanner fileReader;

    /**
     * метод для регистрации пользователя
     */
    public void register() {
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


            String json = gson.toJson(new Player(username));

            try {
                FileWriter myWriter = new FileWriter("./users/" + filename);
                myWriter.write(json);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }

        }

    }

    /**
     * метод для авторизации пользователя
     * @return текущего игрока
     */
    public Player login() {
        System.out.println("Enter your username:");
        String username = scanner.next();
        String filename = username + ".json";
        File f = new File("./users");
        List<String> pathnames = Arrays.asList(f.list());


        if (pathnames.contains(filename)) {

            File myObj = new File("./users/" + filename);

            String str = "";
            try {
                fileReader = new Scanner(myObj);
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }

            while (fileReader.hasNextLine()) {
                str += fileReader.nextLine();
            }

            System.out.println("successfully logged in");
            return gson.fromJson(str, Player.class);


        } else {
            System.out.println("user dont exsist");
            return null;
        }

    }

}
