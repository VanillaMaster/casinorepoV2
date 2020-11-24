package com.company.classes;

import java.util.Arrays;
import java.util.Random;

public class NTRandom {

    final Random random = new Random();

    public int roll(int winrate,int desiredWinrate ,int minRoll, int maxRoll){

        int[] rn = new int[]{0,0,0};

        rn[0] = random.nextInt((maxRoll-minRoll+1)) + minRoll;
        rn[1] = random.nextInt((maxRoll-minRoll+1)) + minRoll;
        rn[2] = random.nextInt((maxRoll-minRoll+1)) + minRoll;

        Arrays.sort(rn);

        if(winrate == desiredWinrate){
            return rn[1];
        } else if (winrate > desiredWinrate) {
            return rn[0];
        } else {
            return rn[2];
        }
    }
}
