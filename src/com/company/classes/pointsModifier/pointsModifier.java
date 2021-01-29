package com.company.classes.pointsModifier;

import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.pointsModifier.statusesProcessing.defaultProcessing;
import com.company.classes.pointsModifier.statusesProcessing.statusProcessing;
import com.company.classes.pointsModifier.statusesProcessing.vipProcessing;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class pointsModifier {

    private final Random random = new Random();

    private final Map<String, statusProcessing> statuses = new HashMap<>(){{
        put("vip",new vipProcessing());
        put("default",new defaultProcessing());
    }};

    public void add(playerData target, int amount, boolean isStatusApplied){
        modify(target,amount,isStatusApplied);
    }

    public void remove(playerData target, int amount,boolean isStatusApplied){
        modify(target,(-amount),isStatusApplied);
    }


    private void modify(playerData pd, int amount,boolean isStatusApplied){
        if (!isStatusApplied){
            pd.addPoints(amount);
        } else {

            switch (pd.getStatus()) {

                case ("vip"):
                    //System.out.println("vip status");
                    if (random.nextInt(2) == 1){
                        pd.addPoints(amount*2);
                        //System.out.println("vip doubling");
                    } else {
                        pd.addPoints(amount);
                    }
                break;

                case ("default"):
                    pd.addPoints(amount);
                break;

                default:
                    pd.addPoints(amount);
                    System.out.println("unexpected status");
                break;

            }

        }
    }


}
