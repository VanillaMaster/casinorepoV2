package com.company.classes.pointsModifier.statusesProcessing;

import com.company.classes.playerDataConstruct.playerData;

import java.util.Random;

public class vipProcessing implements statusProcessing {

    private final Random random = new Random();

    public void execute(playerData target,int amount){
        //System.out.println("vip status");
        if (random.nextInt(2) == 1){
            target.addPoints(amount*2);
            //System.out.println("vip doubling");
        } else {
            target.addPoints(amount);
        }
    }
}
