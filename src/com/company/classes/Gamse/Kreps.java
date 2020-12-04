package com.company.classes.Gamse;

import com.company.classes.NTRandom;
import com.company.classes.Player;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * игра Крепс
 */
public class Kreps implements Game{
 enum betOption{
     pass,
     dpass
 }
    private NTRandom RNG = new NTRandom();

    public void play(Player p) {

        int winChance = RNG.roll(50, 50, 0, 1);

        System.out.println("pass или dpass и количество ставки");
        String[] input = scanner.nextLine().split(" ");
        String inputStatus = input[0];
        int inputBet = Integer.parseInt(input[1]);

        int roll = -1;

        if (winChance == 0) {
            if (inputStatus.equals((betOption.dpass).toString())){
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
            if (inputStatus.equals((betOption.pass).toString())) {
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

        p.points -= inputBet;
        String rollString=Integer.toString(roll);
        if (inputStatus.equals((betOption.dpass).toString()) && (rollString.matches("1|2|3|7|11|12"))) {
            System.out.println("jija");
            p.points += (inputBet * 2);
            showDice(roll, p.points);
            p.krepsPart1.addWin(2);

        } else if (inputStatus.equals((betOption.pass).toString()) && (rollString.matches("4|5|6|8|9|10|11"))) {

            p.points += (inputBet * 2);
            showDice(roll, p.points);
            p.krepsPart1.addWin(2);
            seccondParth(p);

        } else {

            showDice(roll, p.points);
            p.krepsPart1.addLose(1);

        }

    }

    private void seccondParth(Player p) {

        boolean keepRolling = true;

        System.out.println("число ставки и количество ставки");
        String str1 = scanner.nextLine();
        String[] s2 = str1.split(" ");

        int inputNumber = Integer.parseInt(s2[0]);
        int inputaBet = Integer.parseInt(s2[1]);

        p.points -= inputaBet;

        int firstRoll12 = -1;
        int secondRoll12 = -1;

        do {

            int preRoll = RNG.rollNumber(50, 50, 2, 24, inputNumber, 4);

            if (preRoll > 12) {
                secondRoll12 = random.nextInt(25 - preRoll) + preRoll - 12;
                firstRoll12 = preRoll - secondRoll12;
            } else {
                firstRoll12 = random.nextInt(preRoll - 1) + 1;
                secondRoll12 = preRoll - firstRoll12;
            }

            if (firstRoll12 == krepsPointer || firstRoll12 == 7 || secondRoll12 == krepsPointer || secondRoll12 == 7) {
                keepRolling = false;
                p.krepsPart2.addLose(1);
            }

            if ((keepRolling) && (inputNumber == (firstRoll12 + secondRoll12))) {
                p.points += (inputaBet * 8);
                keepRolling = false;
                p.krepsPart2.addWin(1);
            }

            System.out.println("*****");
            System.out.println(firstRoll12);
            System.out.println(secondRoll12);
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
