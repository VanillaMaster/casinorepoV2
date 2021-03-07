package com.company.classes.pointsModifier;

import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.pointsModifier.statusesProcessing.defaultProcessing;
import com.company.classes.pointsModifier.statusesProcessing.statusProcessing;
import com.company.classes.pointsModifier.statusesProcessing.vipProcessing;

import java.util.HashMap;
import java.util.Map;

public class pointsModifier {

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
    
    private void modify(playerData target, int amount,boolean isStatusApplied){
        if (!isStatusApplied){
            target.addPoints(amount);
        } else {
            if (statuses.containsKey(target.getStatus())){
                statuses.get(target.getStatus()).execute(target,amount);
            } else {
                target.addPoints(amount);
                System.out.println("unexpected status");
            }
        }
    }


}
