package com.company.classes.Gamse.Kreps;

import com.company.classes.Gamse.TCIGame;
import com.company.classes.NTRandom;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.pointsModifier.pointsModifier;
import com.google.common.primitives.Ints;

import java.util.Random;

public class KrepsV2 implements TCIGame {

    //===========================================================================================================
    private static final int phaseOneWinRate = 50; //винрейт фазы 1
    private static final int phaseTwoWinRate = 50; //винрейт фазы 2
    private static final int phaseTwoBaseWinRate = 4; //базовый винрейт для фазы 2

    private static final int loseNumber=7;//число проигрыша 2 стадии

    private static final int[] dpassWinNumbers = new int[] {1,2,3,7,11,12}; //выйгрышные числа для dpass
    private static final int[] passWinNumbers = new int[] {4,5,6,8,9,10}; //выйгрышные числа для pass
    //===========================================================================================================


    enum betOptions {
        pass("pass"),
        dpass("dpass"),
        NaN(null);

        private final String string;

        betOptions(String string) {
            this.string = string;
        }

        public static betOptions get(String s)
        {
            for(betOptions choice:values())
                if (choice.name().equals(s))
                    return choice;
            return betOptions.NaN;
        }

    }



    private enum stages {

        zero{
            public boolean run(stageHolder currStage,TCI TCI,playerData playerData,String[] data) {
                currStage.setCurrentStage(stages.one);

                TCI.sendMsg("pass или dpass и количество ставки",playerData.telegramID,"kreps");

                return true;
            }
        },
        one{

            private final pointsModifier modifier = new pointsModifier();
            private final NTRandom RNG = new NTRandom();

            public boolean run(stageHolder currStage,TCI TCI,playerData playerData,String[] data) {
                if (data.length == 2) {

                    if (!data[0].matches("(pass|dpass)") || !data[1].matches("[0-9]+")) {
                        TCI.sendMsg("incorrect input, please try again (kreps phase1) code#1",playerData.telegramID,"kreps");
                        currStage.setCurrentStage(stages.one);
                        return true;
                    }
                    if (Integer.parseInt(data[1]) > playerData.getPoints()) {
                        TCI.sendMsg("у вас недостаточно средств для такой ставки, please try again",playerData.telegramID,"kreps");
                        currStage.setCurrentStage(stages.one);
                        return true;
                    }

                    //=================

                    int inputBet = Integer.parseInt(data[1]);
                    betOptions betOption = betOptions.get(data[0]);

                    modifier.remove(playerData,inputBet,false);

                    int winChance = RNG.roll(playerData.krepsPart1.winrate(), phaseOneWinRate, 0, 1); //ролл 0 или 1 как эвивалент победе поражению в зависимоти от винрейта игрока
                    int roll;

                    if (winChance == 0) {
                        if (data[0].equals((betOptions.dpass).toString())){
                            roll = RNG.getRandom(dpassWinNumbers);
                        } else {
                            roll = RNG.getRandom(passWinNumbers);
                        }
                    } else {
                        if (data[0].equals((betOptions.dpass).toString())){
                            roll = RNG.getRandom(passWinNumbers);
                        } else {
                            roll = RNG.getRandom(dpassWinNumbers);
                        }
                    }

                    if (betOption.equals(betOptions.dpass) && (Ints.contains(dpassWinNumbers,roll))) {
                        modifier.add(playerData,inputBet * 2,false);

                        TCI.sendMsg((roll+"\n\n"+"ваши очки: "+playerData.getPoints()),playerData.telegramID,"commands");

                        playerData.krepsPart1.addWin(2);

                        currStage.setCurrentStage(stages.zero);
                        return false;

                    } else if (betOption.equals(betOptions.pass) && ((Ints.contains(passWinNumbers,roll)))) {
                        modifier.add(playerData,inputBet * 2,false);
                        TCI.sendMsg((roll+"\n\n"+ "ваши очки: "+playerData.getPoints()),playerData.telegramID,"non");

                        playerData.krepsPart1.addWin(2);

                        currStage.setPointer(roll);

                        TCI.sendMsg("число ставки и количество ставки",playerData.telegramID,"non");
                        currStage.setCurrentStage(stages.two);
                        return true;

                    } else {
                        TCI.sendMsg(Integer.toString(roll)+"\n\n"+("ваши очки: "+playerData.getPoints()),playerData.telegramID,"commands");

                        playerData.krepsPart1.addLose(1);
                        currStage.setCurrentStage(stages.zero);
                        return false;
                    }
                    //=================


                } else {
                    TCI.sendMsg("incorrect input, please try again (kreps phase1) code#2",playerData.telegramID,"kreps");
                    currStage.setCurrentStage(stages.one);
                    return true;
                }
            }
        },
        two{

            private final pointsModifier modifier = new pointsModifier();
            private final NTRandom RNG = new NTRandom();
            private final Random random = new Random();

            public boolean run(stageHolder currStage,TCI TCI,playerData playerData,String[] data) {

                if(data.length == 2) {

                    if (!data[0].matches("[0-9]+") || !data[1].matches("[0-9]+")) {
                        TCI.sendMsg("incorrect input, please try again (kreps phase2) code#3",playerData.telegramID,"non");
                        currStage.setCurrentStage(stages.two);
                        return true;
                    } else if (Integer.parseInt(data[0]) > 24) {
                        TCI.sendMsg("incorrect input, please try again (kreps phase2) code#4",playerData.telegramID,"non");
                        currStage.setCurrentStage(stages.two);
                        return true;
                    } else if (Integer.parseInt(data[1]) > playerData.getPoints()) {
                        TCI.sendMsg("у вас недостаточно средств для такой ставки, please try again",playerData.telegramID,"non");
                        currStage.setCurrentStage(stages.two);
                        return true;
                    }

                    modifier.remove(playerData,Integer.parseInt(data[1]),false);

                    boolean keepRolling = true;

                    do {

                        int preRoll = RNG.rollNumber(playerData.krepsPart2.winrate(), phaseTwoWinRate, 2, 24, Integer.parseInt(data[0]), phaseTwoBaseWinRate);

                        int firsDice, secondDice;

                        if (preRoll > 12) {
                            secondDice = random.nextInt(25 - preRoll) + preRoll - 12;
                            firsDice = preRoll - secondDice;
                        } else {
                            firsDice = random.nextInt(preRoll - 1) + 1;
                            secondDice = preRoll - firsDice;
                        }

                        if (firsDice == currStage.getPointer() || firsDice == loseNumber || secondDice == currStage.getPointer() || secondDice == loseNumber) {
                            keepRolling = false;
                            playerData.krepsPart2.addLose(1);
                        }

                        if ((keepRolling) && (Integer.parseInt(data[0]) == (firsDice + secondDice))) {
                            modifier.add(playerData,Integer.parseInt(data[1]) * 8,false);
                            keepRolling = false;
                            playerData.krepsPart2.addWin(1);
                        }

                        TCI.sendMsg((firsDice+"\n"+secondDice),playerData.telegramID,"non");


                    } while (keepRolling);

                    currStage.setCurrentStage(stages.zero);
                    TCI.sendMsg(("ваши очки: "+playerData.getPoints()),playerData.telegramID,"commands");
                    return false;

                    //====

                } else {
                    TCI.sendMsg("incorrect input, please try again (kreps phase2) code#5",playerData.telegramID,"non");
                    currStage.setCurrentStage(stages.two);
                    return true;
                }

            }
        };


        public abstract boolean run(stageHolder currStage,TCI TCI,playerData playerData,String[] data);

    }

    private stageHolder currentStage = new stageHolder();

    public boolean play(playerData playerData, String[] data) {
        return currentStage.getCurrentStage().run(currentStage,TCI,playerData,data);
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



}
