package com.company.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CMDInterface {

    private static final Scanner scanner = new Scanner(System.in);

    private static final Gson g = new Gson();

    private static player currentPlayer = null;

    public static void inerface(){

        boolean isWorking = true;

        while (isWorking){

            String input = scanner.next();

            switch (input) {
                case "/exit": isWorking = false;
                break;

                case "/help": System.out.println("\"/help\" - show this info\n\"/exit\" - exit bot/trogramm/idk\n\"/register\" - register new user");
                break;

                case "/register": register();
                break;

            }

        }
    }

    private static void register(){
        System.out.println("Enter username:");
        String username = scanner.next();
        String filename= username + ".json";

        //System.out.println(username);

        File f = new File("./users");
        List<String> pathnames = Arrays.asList(f.list());

        /*
        for (String pathname : pathnames) {
            System.out.println(pathname);
        }
        */

        if(pathnames.contains(username)){
            System.out.println("nickname already taken");
        } else {

            try {
                File myObj = new File("./users/"+filename);
                myObj.createNewFile();
            } catch (IOException e) {
                System.out.println("error on file creation");
            }

            //player p = new player(username);

            String json = g.toJson(new player(username));

            try {
                FileWriter myWriter = new FileWriter("./users/"+filename);
                myWriter.write(json);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }

        }

    }

    private static void login(){
        System.out.println("Enter your username:");
        String username = scanner.next();
        String filename= username + ".json";

        File f = new File("./users");
        List<String> pathnames = Arrays.asList(f.list());
        if(pathnames.contains(username)){

            File myObj = new File("./users/"+filename);

            String str ="";
            Scanner myReader = null;
            try {
                myReader = new Scanner(myObj);
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }

            while (myReader.hasNextLine()) {
                str+=myReader.nextLine();
            }

            System.out.println(str);
            currentPlayer = g.fromJson(str, player.class);


        } else {
            System.out.println("user dont exsist");
        }

    }

}
