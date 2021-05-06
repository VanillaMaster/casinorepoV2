package com.company.classes.Gamse.Kreps;

import com.company.classes.Gamse.TCIGame;
import com.company.classes.NTRandom;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.pointsModifier.pointsModifier;
import com.company.classes.utilits.KeyboardsList;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Random;

public class KrepsV2 implements TCIGame {

    //===========================================================================================================
    private static final int phaseOneWinRate = 50; //винрейт фазы 1
    private static final int phaseTwoWinRate = 50; //винрейт фазы 2
    private static final int phaseTwoBaseWinRate = 4; //базовый винрейт для фазы 2

    private static final int loseNumber=7;//число проигрыша 2 стадии
    //===========================================================================================================

    enum BetOptions {
        pass(new int[] {1,2,3,7,11,12}),
        dpass(new int[]{4, 5, 6, 8, 9, 10}),
        NaN;

        int[] WinNumbers;
        BetOptions(int[] i) {
            WinNumbers = i;
        }
        int[] getWinNumbers() {
            return WinNumbers;
        }

        BetOptions() {
        }

        public static BetOptions get(String s)
        {
            for(BetOptions choice:values())
                if (choice.name().equals(s))
                    return choice;
            return BetOptions.NaN;
        }

    }


    private enum stages {

        zero(KeyboardsList.commands){
            public stageResult run(stageHolder currStage, playerData playerData,String[] data) {

                return new stageResult(true,"pass или dpass и количество ставки",stages.one);

            }
        },
        one(KeyboardsList.kreps){

            private final pointsModifier modifier = new pointsModifier();
            private final NTRandom RNG = new NTRandom();

            private SOneValidData validate(String[] data,Integer points){
                if (data.length!=2){
                    return new SOneValidData("incorrect input, please try again (kreps phase1) code#2");
                }
                if (!data[0].matches("(pass|dpass)") || !data[1].matches("[0-9]+")){
                    return new SOneValidData("incorrect input, please try again (kreps phase1) code#2\"");
                }
                if (Integer.parseInt(data[1]) > points){
                    return new SOneValidData("у вас недостаточно средств для такой ставки, please try again");
                }
                return new SOneValidData(BetOptions.get(data[0]),Integer.parseInt(data[1]),null);
            }

            private Integer getRoll(BetOptions betOption,playerData playerData){
                //ролл 0 или 1 как эвивалент победе поражению в зависимоти от винрейта игрока
                int winIdentifier  = RNG.roll(playerData.krepsPart1.winrate(), phaseOneWinRate, 0, 1);

                if (winIdentifier  == 0) {
                    if (betOption.equals((BetOptions.dpass))){
                        return RNG.getRandom(BetOptions.dpass.getWinNumbers());
                    } else {
                        return RNG.getRandom(BetOptions.pass.getWinNumbers());
                    }
                } else {
                    if (betOption.equals((BetOptions.dpass))){
                        return RNG.getRandom(BetOptions.pass.getWinNumbers());
                    } else {
                        return RNG.getRandom(BetOptions.dpass.getWinNumbers());
                    }
                }
            }

            public stageResult run(stageHolder currStage, playerData playerData,String[] data) {

                SOneValidData validData = validate(data,playerData.getPoints());

                if (validData.getError() != null){
                    return new stageResult(true,validData.getError(),stages.one);
                }

                //=================

                int inputBet = validData.getInputBet();
                BetOptions betOption = validData.getBetOption();

                modifier.remove(playerData,inputBet,false);

                int roll = getRoll(betOption,playerData);

                if (betOption.equals(BetOptions.dpass) && (Ints.contains(betOption.getWinNumbers(),roll))) {
                    modifier.add(playerData,inputBet * 2,false);

                    playerData.krepsPart1.addWin(2);

                    return new stageResult(false,(roll+"\n\n"+"ваши очки: "+playerData.getPoints()),stages.zero);
                    //return false;

                } else if (betOption.equals(BetOptions.pass) && ((Ints.contains(betOption.getWinNumbers(),roll)))) {
                    modifier.add(playerData,inputBet * 2,false);

                    stageResult result = new stageResult(true,(roll+"\n\n"+ "ваши очки: "+playerData.getPoints()),stages.two);

                    playerData.krepsPart1.addWin(2);

                    currStage.setPointer(roll);

                    result.addResponse("число ставки и количество ставки");

                    return result;

                } else {
                    playerData.krepsPart1.addLose(1);
                    return new stageResult(false,(Integer.toString(roll)+"\n\n"+("ваши очки: "+playerData.getPoints())),stages.zero);
                }
                //=================
            }
        },
        two(KeyboardsList.non){

            private final pointsModifier modifier = new pointsModifier();
            private final NTRandom RNG = new NTRandom();
            private final Random random = new Random();

            private String validate(String[] data,Integer points){
                if (data.length!=2){
                    return "incorrect input, please try again (kreps phase2) code#5";
                }
                if (!data[0].matches("[0-9]+") || !data[1].matches("[0-9]+")){
                    return "incorrect input, please try again (kreps phase2) code#3";
                }
                if (Integer.parseInt(data[0]) > 24){
                    return "incorrect input, please try again (kreps phase2) code#4";
                }
                if (Integer.parseInt(data[1]) > points){
                    return "у вас недостаточно средств для такой ставки, please try again";
                }
                return null;
            }

            public stageResult run(stageHolder currStage, playerData playerData,String[] data) {

                String isInValid = validate(data,playerData.getPoints());

                if (isInValid!= null){
                    return new stageResult(true,isInValid,stages.two);
                }
                int inputNumber = Integer.parseInt(data[0]);
                int inputBet = Integer.parseInt(data[1]);

                modifier.remove(playerData,inputBet,false);

                boolean keepRolling = true;

                stageResult result = new stageResult(false,stages.zero);

                do {

                    int preRoll = RNG.rollNumber(playerData.krepsPart2.winrate(), phaseTwoWinRate, 2, 24, inputNumber, phaseTwoBaseWinRate);

                    int[] dice =RNG.twoNumberGenerator(preRoll);

                    if (dice[0] == currStage.getPointer() || dice[0] == loseNumber || dice[1] == currStage.getPointer() || dice[1] == loseNumber) {
                        keepRolling = false;
                        playerData.krepsPart2.addLose(1);
                    }

                    if ((keepRolling) && (inputNumber == (dice[0] + dice[1]))) {
                        modifier.add(playerData,inputBet * 8,false);
                        keepRolling = false;
                        playerData.krepsPart2.addWin(1);
                    }

                    result.addResponse((dice[0]+"\n"+dice[1]));


                } while (keepRolling);

                result.addResponse(("ваши очки: "+playerData.getPoints()));
                return result;

            }
        };

        private final KeyboardsList s;

        stages(KeyboardsList s){this.s = s;};

        public KeyboardsList getKeyboard(){
            return this.s;
        }

        public abstract stageResult run(stageHolder currStage,playerData playerData,String[] data);

    }

    private stageHolder currentStage = new stageHolder();

    public boolean play(playerData playerData, String[] data) {

        stageResult result = currentStage.getCurrentStage().run(currentStage,playerData,data);

        currentStage.setCurrentStage(result.getStage());

        String[] messages = result.getResponseText();

        for (int i = 0; i < messages.length; i++) {
            TCI.sendMsg(messages[i],playerData.telegramID,currentStage.getCurrentStage().getKeyboard());
        }

        return result.isAdditionalInputRequired();
    }

    private TCI TCI;

    public KrepsV2(TCI iTCI){
        TCI = iTCI;
    }

    private class stageHolder{
        private stages currentStage = stages.zero;

        private int pointer;

        public void setPointer(int pointer) {
            this.pointer = pointer;
        }

        public int getPointer() {
            return pointer;
        }

        public void setCurrentStage(stages stages){
            this.currentStage = stages;
        }
        public stages getCurrentStage() {
            return currentStage;
        }
    }

    private static class stageResult{
        private boolean isAdditionalInputRequired;
        private ArrayList<String> responseText = new ArrayList<>();
        private stages stage;


        stageResult(boolean isAdditionalInputRequired,String responseText,stages stage){
            this.isAdditionalInputRequired = isAdditionalInputRequired;
            this.responseText.add(responseText);
            this.stage = stage;
        }

        stageResult(boolean isAdditionalInputRequired,stages stage){
            this.isAdditionalInputRequired = isAdditionalInputRequired;
            this.stage = stage;
        }

        public void addResponse(String responseText){
            this.responseText.add(responseText);
        }

        public boolean isAdditionalInputRequired(){
            return isAdditionalInputRequired;
        }

        public String[] getResponseText() {
            return responseText.toArray(new String[0]);
        }

        public stages getStage() {
            return stage;
        }
    }

    private static class SOneValidData{

        private String error;
        private int inputBet;
        private BetOptions betOption;

        public SOneValidData(String error){
            this.betOption = null;
            this.inputBet = 0;
            this.error = error;
        }

        public SOneValidData(BetOptions betOption,int inputBet,String error){
            this.betOption = betOption;
            this.inputBet = inputBet;
            this.error = error;
        }
        public BetOptions getBetOption() {
            return betOption;
        }
        public int getInputBet() {
            return inputBet;
        }
        public String getError() {
            return error;
        }
    }

}
