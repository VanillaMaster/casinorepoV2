package com.company.classes.Gamse.Sloti;

import com.company.classes.Gamse.TCIGame;
import com.company.classes.NTRandom;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.utilits.TCISmartIO;

import java.util.Random;

public class SlotMachine implements TCIGame {

    public SlotMachine(TCI iTCI){
        //TCI = iTCI;
        SIO = new TCISmartIO(iTCI);
        stage = 0;
    }

    //private TCI TCI;

    private int stage;

    public int getStage(){
        return stage;
    }

    private final NTRandom RNG = new NTRandom();
    private final TCISmartIO SIO;
    private final Random random = new Random();


    public boolean play(playerData p,String data){
        //SlotMachineIO(p);

        System.out.println("slots stage: " + stage);

        switch (stage){
            case 0:
                stage = stageZero(p);
                return true;

            case 1:
                stage = stageOne(p,data);
                break;

            default:
                SIO.outPut(p,"wtf error");
                break;
        }

        return false;

    }

    private int stageZero(playerData p){
        SIO.outPut(p,"количество ставки");

        return 1;
    }

    private int stageOne(playerData p,String input){
        if (!input.matches("[0-9]+")) {
            SIO.outPut(p,"incorrect input, please try again");
            return 0;
        }
        if (Integer.parseInt(input) > p.points) {
            SIO.outPut(p,"у вас недостаточно средств для такой ставки, please try again");
            return 0;
        }

        return stageThree(p,Integer.parseInt(input));
    }

    private int stageThree(playerData p,int inputBet){

        p.points -= inputBet;
        int roll1=RNG.roll(50,50,1,7);
        int roll2=RNG.roll(50,50,1,7);
        int roll3=RNG.roll(50,50,1,7);
        if(roll1 == 6 && roll2 == 6 && roll3 == 6){
            p.points -= p.points;
        }else if (roll1 == 7 && roll2 == 7 && roll3 == 7){
            p.points += (inputBet * 100);
        }
        else if (roll1 == roll2) {
            p.points += (inputBet * 2);
            if (roll2 == roll3){
                p.points += (inputBet * 5);
            }
        }
        SIO.slotResultOutput(p,("ваши очки: "+p.points),roll1,roll2,roll3);

        return 0;
    }

}

