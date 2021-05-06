package com.company.classes.Gamse.Sloti;


import com.company.classes.Gamse.TCIGame;
import com.company.classes.NTRandom;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.pointsModifier.pointsModifier;
import com.company.classes.utilits.KeyboardsList;

public class SlotMachineV2 implements TCIGame {

    protected enum stages {
        zero(KeyboardsList.commands){
            public stageResult run(stageHolder currStage, playerData playerData, String[] data) {
                return new stageResult(true,"количество ставки",stages.one);

            }
        },
        one(KeyboardsList.slots){

            private final pointsModifier modifier = new pointsModifier();
            private final NTRandom RNG = new NTRandom();

            public stageResult run(stageHolder currStage, playerData playerData, String[] data) {

                String isInValid = validate.validateOne(data,playerData.getPoints());

                if (isInValid!= null){
                    return new stageResult(true,isInValid, stages.one);
                }

                int inputBet = Integer.parseInt(data[0]);

                modifier.remove(playerData,inputBet,false);

                //docs: https://imgur.com/a/bqpI9Gv

                int preRoll = RNG.roll(50,50,1,(729+1));

                int[] roll = new int[3];
                roll[0] = RNG.nextInt(9)+1;
                roll[1] = RNG.nextInt(8)+1;
                if (roll[1] == roll[0])
                    roll[1]++;
                roll[2] = RNG.nextInt(7)+1;
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

                return new stageResult(false,"\\/\\/\\/\\/\\/ \n"+"| "+roll[0]+" | "+roll[1]+" | "+ roll[2] +" |" + "\n/\\/\\/\\/\\/\\"+"\n \n" +("ваши очки: "+playerData.getPoints()),stages.zero);
            }
        };

        private final KeyboardsList s;

        stages(KeyboardsList s){this.s = s;};

        public KeyboardsList getKeyboard(){
            return this.s;
        }

        public abstract stageResult run(stageHolder currStage, playerData playerData, String[] data);

    };

    private stageHolder currentStage = new stageHolder();

    private TCI TCI;

    public SlotMachineV2(TCI iTCI){
        TCI = iTCI;
    }

    public boolean play(playerData playerData, String[] data) {

        stageResult result = currentStage.getCurrentStage().run(currentStage,playerData,data);

        currentStage.setCurrentStage(result.getStage());

        String[] messages = result.getResponseText();

        for (int i = 0; i < messages.length; i++) {
            TCI.sendMsg(messages[i],playerData.telegramID,currentStage.getCurrentStage().getKeyboard());
        }

        return result.isAdditionalInputRequired();

    }

}
