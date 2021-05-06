package com.company.classes.pointsModifier;

import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.pointsModifier.statusesProcessing.defaultProcessing;
import com.company.classes.pointsModifier.statusesProcessing.statusProcessing;
import com.company.classes.pointsModifier.statusesProcessing.vipProcessing;

public class pointsModifier {

    /*
    private final Map<String, statusProcessing> statuses = new HashMap<>(){{
        put("vip",new vipProcessing());
        put("default",new defaultProcessing());
    }};

     */





    private enum statuses {
        vip(new vipProcessing()),
        DEFAULT(new defaultProcessing());


        public static statusProcessing get(String s)
        {
            for(statuses choice:values()) {
                if (choice.name().equals(s))
                    return choice.status;
            }
            return DEFAULT.status;
        }

        public statusProcessing getStatus() {
            return status;
        }

        private final statusProcessing status;

        statuses(statusProcessing command) {
            this.status = command;
        }

    }



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

            statuses.get(target.getStatus()).execute(target,amount);

        }
    }


}
