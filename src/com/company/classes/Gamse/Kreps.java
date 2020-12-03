package com.company.classes.Gamse;

import com.company.classes.NTRandom;
import com.company.classes.player;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Kreps {

    private NTRandom RNG = new NTRandom();

    public Kreps(){}

    public void Play(player p) {

        int winChance = RNG.roll(50, 50, 0, 1);

        System.out.println("pass или dpass и количество ставки");
        String[] input = scanner.nextLine().split(" ");
        String inputStatus = input[0];
        int inputBet = Integer.parseInt(input[1]);

        int roll = -1;

        if (winChance == 0) {
            if (inputStatus.equals("dpass")) {
                roll = random.nextInt(6) + 4;
                if (roll >= 7) {
                    roll++;
                }
            } else {
                roll = random.nextInt(6) + 1;
                if (roll >= 4) {
                    roll += 3;
                }
                if (roll >= 8) {
                    roll += 3;
                }
            }
        } else {
            if (inputStatus.equals("pass")) {
                roll = random.nextInt(6) + 4;
                if (roll >= 7) {
                    roll++;
                }
            } else {
                roll = random.nextInt(6) + 1;
                if (roll >= 4) {
                    roll += 3;
                }
                if (roll >= 8) {
                    roll += 3;
                }
            }
        }

        krepsPointer = roll;

        //1 раунд тута

        p.points -= inputBet;

        if (inputStatus.equals("dpass") && (roll == 1 || roll == 2 || roll == 3 || roll == 7 || roll == 11 || roll == 12)) {

            p.points += (inputBet * 2);
            showDice(roll, p.points);
            p.krepsParth1.addWin(2);

        } else if (inputStatus.equals("pass") && (roll == 4 || roll == 5 || roll == 6 || roll == 8 || roll == 9 || roll == 10)) {

            p.points += (inputBet * 2);
            showDice(roll, p.points);
            p.krepsParth1.addWin(2);
            seccondParth(p);

        } else {

            //p.points += inputBet;
            showDice(roll, p.points);
            p.krepsParth1.addLose(1);

        }

    }

    private void seccondParth(player p) {

        boolean keepRolling = true;

        System.out.println("число ставки и количество ставки");
        String str1 = scanner.nextLine();
        String[] s2 = str1.split(" ");

        int inputNumber = Integer.parseInt(s2[0]);
        int inputaBet = Integer.parseInt(s2[1]);

        p.points -= inputaBet;

        int roll12_1 = -1;
        int roll12_2 = -1;

        System.out.println(krepsPointer);

        do {

            int preroll = RNG.rollNumber(50, 50, 2, 24, inputNumber, 4);

            if (preroll > 12) {
                roll12_2 = random.nextInt(25 - preroll) + preroll - 12;
                roll12_1 = preroll - roll12_2;
            } else {
                roll12_1 = random.nextInt(preroll - 1) + 1;
                roll12_2 = preroll - roll12_1;
            }

            if (roll12_1 == krepsPointer || roll12_1 == 7 || roll12_2 == krepsPointer || roll12_2 == 7) {
                keepRolling = false;
                p.krepsParth2.addLose(1);
            }

            if ((keepRolling) && (inputNumber == (roll12_1 + roll12_2))) {
                p.points += (inputaBet * 8);
                keepRolling = false;
                p.krepsParth2.addWin(1);
            }

            System.out.println("*****");
            System.out.println(roll12_1);
            System.out.println(roll12_2);
            System.out.println("*****");


            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("dealy error(Kreps)");
            }

        } while (keepRolling);


        System.out.println(p.points);
    }

    private void showDice(int roll, int points) {
        System.out.println("*****");
        System.out.println(roll);
        System.out.println("*****");
        System.out.println(points);
    }

    private Scanner scanner = new Scanner(System.in);

    private int krepsPointer;

    final Random random = new Random();
}
