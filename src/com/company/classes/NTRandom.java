package com.company.classes;

import java.util.Arrays;
import java.util.Random;

public class NTRandom {

    final Random random = new Random();


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

        if (chance < defaultWinrate + (desiredWinrate - winrate)){
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
