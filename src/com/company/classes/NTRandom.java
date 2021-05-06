package com.company.classes;

import java.util.Arrays;
import java.util.Random;

public class NTRandom {

    final Random random = new Random();

    /**
     * https://imgur.com/a/LFgtH80 обьяснение работы
     * numberOfRolls это переменная определяющия размер выборки рандомных чисел чем больше размер тем рандомнее исход
     */
    public int roll(int winrate, int desiredWinrate, int minRoll, int maxRoll) {

        int numberOfRolls=3;
        int[] rn = new int[numberOfRolls];

        for (int i = 0; i < rn.length; i++)
            rn[i] = random.nextInt((maxRoll - minRoll + 1)) + minRoll;

        Arrays.sort(rn);

        int preRoll = random.nextInt(101);
        double multiplicator = 0.5;
        if (desiredWinrate>50){
            multiplicator = 50/(double)(-desiredWinrate);
        } else {
            multiplicator = 50/(double)(100-desiredWinrate);
        }
        int diffBetv = ((int)((winrate - desiredWinrate)*multiplicator) + 50);


        if (diffBetv < preRoll){
            //pseudo win
            return rn[rn.length-1];
        } else {
            //pseudo lose
            return rn[0];
        }
    }

    /**
     * https://imgur.com/a/NtnT2d1 обьяснение работы
     * @return квази рандомное число
     */
    public int rollNumber(int winrate, int desiredWinrate, int minRoll, int maxRoll, int desiredRoll, int defaultWinrate) {

        int chance = random.nextInt(101);

        if (chance < defaultWinrate + (desiredWinrate - winrate)) {
            return desiredRoll;
        } else {
            int roll = random.nextInt((maxRoll - minRoll)) + minRoll;
            if (roll >= desiredRoll) {
                roll++;
            }
            return roll;
        }
    }
    /**
     * https://imgur.com/a/R0FEFLp обьяснение работы метода
     * @param preRoll число в дипазоне 2 24 которое генерируеться посредством метода rollNumber
     */
    public int[] twoNumberGenerator(int preRoll){
        int secondDice,firsDice;
        if (preRoll > 12) {
             secondDice = random.nextInt(25 - preRoll) + preRoll - 12;
             firsDice = preRoll - secondDice;
        } else {
             firsDice = random.nextInt(preRoll - 1) + 1;
             secondDice = preRoll - firsDice;
        }
        return new int[] {firsDice, secondDice};
    }

    public int getRandom(int[] mx) {
        return mx[random.nextInt(mx.length)];
    }

    public int nextInt(int i){
        return random.nextInt(i);
    }
}
