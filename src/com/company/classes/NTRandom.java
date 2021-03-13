package com.company.classes;

import java.util.Arrays;
import java.util.Random;

public class NTRandom {

    final Random random = new Random();

    /**
     * @param winrate        текущий винрейт игрока
     * @param desiredWinrate нужный винрейт игрока
     * @param minRoll        нижняя граница броска
     * @param maxRoll        верхняя граница броска
     * @return рандомное число
     */
    public int roll(int winrate, int desiredWinrate, int minRoll, int maxRoll) {

        int[] rn = new int[3];

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
     * @param winrate        текущий винрейт игрока
     * @param desiredWinrate нужный винрейт игрока
     * @param minRoll        нижняя граница броска
     * @param maxRoll        верхняя граница броска
     * @param desiredRoll    нужныое число
     * @param defaultWinrate базовый винрейт
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

    public int getRandom(int[] mx) {
        return mx[random.nextInt(mx.length)];
    }

    public int nextTnt(int i){
        return random.nextInt(i);
    }
}
