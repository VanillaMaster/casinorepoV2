package com.company.classes;

import java.util.Arrays;
import java.util.Random;

public class NTRandom {

    final Random random = new Random();

    private static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    public int roll(int winrate,int desiredWinrate ,int minRoll, int maxRoll){

        int[] rn = new int[]{0,0};

        rn[0] = random.nextInt((maxRoll-minRoll+1)) + minRoll;
        rn[1] = random.nextInt((maxRoll-minRoll+1)) + minRoll;

        Arrays.sort(rn);

        if(winrate == desiredWinrate){
            return random.nextInt((maxRoll-minRoll+1)) + minRoll;
        } else if (winrate > desiredWinrate) {
            return rn[0];
        } else {
            return rn[1];
        }
    }

    public int rollNumber(int winrate,int desiredWinrate ,int minRoll, int maxRoll, int desiredRoll,int defaultWinrate){

        int chance = random.nextInt(101);

        if (chance < defaultWinrate){
            return desiredRoll;
        } else {
            int roll = random.nextInt((maxRoll-minRoll)) + minRoll;
            if( roll >= desiredRoll){
                roll++;
            }
            return roll;
        }
    }
}
