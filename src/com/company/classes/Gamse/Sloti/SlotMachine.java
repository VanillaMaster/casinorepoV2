package com.company.classes.Gamse.Sloti;

import com.company.classes.Gamse.TCIGame;
import com.company.classes.NTRandom;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.pointsModifier.pointsModifier;
import com.company.classes.utilits.TCISmartIO;

public class SlotMachine implements TCIGame {

    public SlotMachine(TCI iTCI){
        SIO = new TCISmartIO(iTCI);
        stage = 0;
    }

    private int stage;

    //public int getStage(){ return stage; }

    private final NTRandom RNG = new NTRandom();
    private final TCISmartIO SIO;
    //private final Random random = new Random();
    private final pointsModifier modifier = new pointsModifier();

    private boolean isAdditionalInputRequired = false;


    public boolean play(playerData playerData, String[] data){

        isAdditionalInputRequired = false;

        switch (stage){
            case 0:
                stage = stageZero(playerData);
                break;

            case 1:
                stage = stageOne(playerData,data);
                break;

            default:
                SIO.outPut(playerData,"wtf error","commands");
                break;
        }

        return isAdditionalInputRequired;

    }

    private int stageZero(playerData playerData){
        SIO.outPut(playerData,"количество ставки","slots");
        isAdditionalInputRequired = true;
        return 1;
    }

    private int stageOne(playerData p,String[] input) {
        if (input.length == 1) {
            if (!input[0].matches("[0-9]+")) {
                SIO.outPut(p, "incorrect input, please try again (slots)","slots");
                isAdditionalInputRequired = true;
                return 1;
            }
            if (Integer.parseInt(input[0]) > p.getPoints()) {
                SIO.outPut(p, "у вас недостаточно средств для такой ставки, please try again","slots");
                isAdditionalInputRequired = true;
                return 1;
            }

            return stageThree(p,Integer.parseInt(input[0]));

        } else {
            SIO.outPut(p, "incorrect input, please try again (slots)","slots");
            isAdditionalInputRequired = true;
            return 1;
        }

    }

    private int stageThree(playerData playerData,int inputBet){

        modifier.remove(playerData,inputBet,false);
        //playerData.PointModify(-inputBet,false);
        int roll1=RNG.roll(50,50,1,7);
        int roll2=RNG.roll(50,50,1,7);
        int roll3=RNG.roll(50,50,1,7);
        if(roll1 == 6 && roll2 == 6 && roll3 == 6){
            modifier.remove(playerData,playerData.getPoints(),false);
            //playerData.PointModify(-playerData.getPoints(),false);
        }else if (roll1 == 7 && roll2 == 7 && roll3 == 7){
            modifier.add(playerData,inputBet * 100,true);
            //playerData.PointModify(inputBet * 100,true);
        }
        else if (roll1 == roll2) {
            modifier.add(playerData,inputBet * 2,true);
            //playerData.PointModify(inputBet * 2,true);
            if (roll2 == roll3){
                modifier.remove(playerData,inputBet * 5,true);
                //playerData.PointModify(inputBet * 5,true);
            }
        }
        SIO.slotResultOutput(playerData,("ваши очки: "+playerData.getPoints()),roll1,roll2,roll3);

        return 0;
    }

}

