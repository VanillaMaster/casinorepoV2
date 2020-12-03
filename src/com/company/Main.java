package com.company;

import com.company.classes.CMDInterface;
import com.company.classes.NTRandom;
import com.company.classes.player;
import com.google.gson.Gson;


import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Main {


    public static void main(String[] args) {

        CMDInterface.inerface();

        /*

        try {
            File myObj = new File("./users/testfile.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("./users/testfile.txt");
            // Writes this content into the specified file
            myWriter.write("Java is the prominent programming language of the millenium!");

// Closing is necessary to retrieve the resources allocated
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        File f = new File("./users");
        String[] pathnames = f.list();

        for (String pathname : pathnames) {
            // Print the names of files and directories
            System.out.println(pathname);
        }


*/


/*

        Gson g = new Gson();

        player p = new player();

        String str = g.toJson(p);

        //NTRandom rng = new NTRandom();

        System.out.println(str);

        player p2 = g.fromJson(str, player.class);

        System.out.println(p2.gameName.winrate());

        for (int i=0; i < 400; i++)
        {
            p2.gameName.addWin();
        }

        System.out.println(p2.gameName.winrate());

        for (int i=0; i < 50; i++)
        {
            //System.out.println(rng.roll(p.gameName.winrate(),50,0,1));
        }
*/

    }
}
