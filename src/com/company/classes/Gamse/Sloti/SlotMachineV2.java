package com.company.classes.Gamse.Sloti;

import com.company.classes.Gamse.TCIGame;
import com.company.classes.NTRandom;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.pointsModifier.pointsModifier;

public class SlotMachineV2 implements TCIGame {


    private enum stages {
        zero{
            public boolean run(stageHolder currStage,TCI TCI,playerData playerData,String[] data) {
                currStage.setCurrentStage(stages.one);

                TCI.sendMsg("количество ставки",playerData.telegramID,"slots");

                return true;
            }
        },
        one{

            private final pointsModifier modifier = new pointsModifier();
            private final NTRandom RNG = new NTRandom();

            public boolean run(stageHolder currStage,TCI TCI,playerData playerData,String[] data) {
                currStage.setCurrentStage(stages.one);

                //====

                if (data.length == 1) {
                    if (!data[0].matches("[0-9]+")) {
                        TCI.sendMsg("incorrect input, please try again (slots)",playerData.telegramID,"slots");
                        currStage.setCurrentStage(stages.one);
                        return true;
                    }
                    if (Integer.parseInt(data[0]) > playerData.getPoints()) {
                        TCI.sendMsg("у вас недостаточно средств для такой ставки, please try again",playerData.telegramID,"slots");
                        currStage.setCurrentStage(stages.one);
                        return true;
                    }

                    //================

                    int inputBet = Integer.parseInt(data[0]);

                    modifier.remove(playerData,inputBet,false);

                    //docs: https://imgur.com/a/bqpI9Gv

                    int preRoll = RNG.roll(50,50,1,(729+1));

                    int[] roll = new int[3];
                    roll[0] = RNG.nextTnt(9)+1;
                    roll[1] = RNG.nextTnt(8)+1;
                    if (roll[1] == roll[0])
                        roll[1]++;
                    roll[2] = RNG.nextTnt(7)+1;
                    if (roll[2] == roll[0])
                        roll[2]++;
                    if (roll[2] == roll[1])
                        roll[2]++;

                    if (preRoll > (729 - 9)){
                        // three
                        roll[1] = roll[0];
                        roll[2] = roll[0];
                        modifier.add(playerData,inputBet * 100,true);
                        //playerData.
                    }
                    if (preRoll > (729-9-81) && preRoll < (729 - 9)){
                        roll[1] = roll[2];
                        modifier.add(playerData,inputBet * 2,true);
                    }
                    if (preRoll > (729-9-81-81) && preRoll < (729-9-81)){
                        roll[1] = roll[0];
                        modifier.add(playerData,inputBet * 2,true);
                    }

                    TCI.sendMsg("\\/\\/\\/\\/\\/ \n"+"| "+roll[0]+" | "+roll[1]+" | "+ roll[2] +" |" + "\n/\\/\\/\\/\\/\\"+"\n \n" +("ваши очки: "+playerData.getPoints()),playerData.telegramID,"commands");
                    currStage.setCurrentStage(stages.zero);
                    return false;

                } else {
                    TCI.sendMsg("incorrect input, please try again (slots)",playerData.telegramID,"slots");
                    currStage.setCurrentStage(stages.one);
                    return true;
                }


            }
        };

        public abstract boolean run(stageHolder currStage,TCI TCI,playerData playerData,String[] data);

    }

    private static class stageHolder{
        private stages currentStage = stages.zero;
        public void setCurrentStage(stages stages){
            this.currentStage = stages;
        }
        public stages getCurrentStage() {
            return currentStage;
        }
    }

    private stageHolder currentStage = new stageHolder();

    private TCI TCI;

    public SlotMachineV2(TCI iTCI){
        TCI = iTCI;
    }

    public boolean play(playerData playerData, String[] data) {

        return currentStage.getCurrentStage().run(currentStage,TCI,playerData,data);

    }

}
