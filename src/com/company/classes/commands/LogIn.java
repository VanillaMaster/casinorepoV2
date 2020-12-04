package com.company.classes.commands;

import com.company.classes.Player;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LogIn implements Command{

    public LogIn(Player iPlayer){
        this.p = iPlayer;
    }

    private Player p;
    private Player tmpPlayer;

    private final Scanner scanner = new Scanner(System.in);
    private final Gson gson = new Gson();
    private Scanner fileReader;

    public void execute() {
        System.out.println("Enter your username:");
        String username = scanner.next();
        String filename = username + ".json";
        File f = new File("./users");
        List<String> pathnames = Arrays.asList(f.list());
        String str;

        if (pathnames.contains(filename)) {

            File myObj = new File("./users/" + filename);

            try {
                fileReader = new Scanner(myObj);
            } catch (FileNotFoundException e) {
                System.out.println("error on File reading.");
            }

            str = "";
            while (fileReader.hasNextLine()) {
                str += fileReader.nextLine();
            }

            System.out.println("successfully logged in");
            tmpPlayer = gson.fromJson(str, Player.class);

            p.assemble(tmpPlayer.points,tmpPlayer.Name,tmpPlayer.krepsPart1,tmpPlayer.krepsPart2);

        } else {
            System.out.println("user doesn't exist");
        }

    }
}
