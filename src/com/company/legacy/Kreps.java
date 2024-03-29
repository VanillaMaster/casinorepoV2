package com.company.legacy;

import com.company.classes.Gamse.TCIGame;
import com.company.classes.NTRandom;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.pointsModifier.pointsModifier;
import com.google.common.primitives.Ints;

import java.util.Random;

/**
 * игра Крепс
 */
public class Kreps implements TCIGame {

    enum BetOption {
        pass,
        dpass
    }
    //int firsDice;
    //int secondDice;

    private final int phaseOneWinRate = 50; //винрейт фазы 1
    private final int phaseTwoWinRate = 50; //винрейт фазы 2
    private final int phaseTwoBaseWinRate = 4; //базовый винрейт для фазы 2

    private final int loseNumber=7;//число проигрыша 2 стадии

    private final int[] dpassWinNumbers = new int[] {1,2,3,7,11,12}; //выйгрышные числа для dpass
    private final int[] passWinNumbers = new int[] {4,5,6,8,9,10}; //выйгрышные числа для pass

    private final NTRandom RNG = new NTRandom();
    private final TCISmartIO SIO;
    private final Random random = new Random();
    private final pointsModifier modifier = new pointsModifier();

    private int pointer = -1;
    private boolean isAdditionalInputRequired = false;

    private int stage = 0;

    public Kreps(TCI iTCI){
        SIO = new TCISmartIO(iTCI);
        stage = 0;
    }



    /**
     * запучкает игру Крепс
     * @param playerData игрок
     */
    public boolean play(playerData playerData, String[] data){

        isAdditionalInputRequired = false;

        switch (stage){
            case 0:
                stage = stageZero(playerData);
                break;

            case 1:
                stage = stageOne(playerData,data);
                break;

            case 3:
                stage = stageThree(playerData);
                break;

            case 4:
                stage = stageFour(playerData,data);
                break;

            default:
                SIO.outPut(playerData,"wtf error № "+stage,"commands");
                break;
        }

        return isAdditionalInputRequired;

    }

    private int stageZero(playerData playerData){
        SIO.outPut(playerData,"pass или dpass и количество ставки","kreps");
        isAdditionalInputRequired = true;
        return 1;
    }

    private int stageOne(playerData playerData, String[] input){

        if (input.length == 2) {

            if (!input[0].matches("(pass|dpass)") || !input[1].matches("[0-9]+")) {
                SIO.outPut(playerData, "incorrect input, please try again (kreps phase1)","kreps");
                isAdditionalInputRequired = true;
                return 1;
            }
            if (Integer.parseInt(input[1]) > playerData.getPoints()) {
                SIO.outPut(playerData, "у вас недостаточно средств для такой ставки, please try again","kreps");
                isAdditionalInputRequired = true;
                return 1;
            }

            BetOption inputBet= BetOption.valueOf(input[0]);
            return stageTwo(playerData, inputBet, Integer.parseInt(input[1]));//next stage

        } else {
            SIO.outPut(playerData, "incorrect input, please try again (kreps phase1)","kreps");
            isAdditionalInputRequired = true;
            return 1;
        }
    }

    private int stageTwo(playerData playerData,BetOption inputStatus, int inputBet){

        modifier.remove(playerData,inputBet,false);
        //playerData.PointModify(-inputBet,false);

        int winChance = RNG.roll(playerData.krepsPart1.winrate(), phaseOneWinRate, 0, 1); //ролл 0 или 1 как эвивалент победе поражению в зависимоти от винрейта игрока
        int roll;

        if (winChance == 0) {
            if (inputStatus.equals((BetOption.dpass))){
                roll = RNG.getRandom(dpassWinNumbers);
            } else {
                roll = RNG.getRandom(passWinNumbers);
            }
        } else {
            if (inputStatus.equals((BetOption.dpass))){
                roll = RNG.getRandom(passWinNumbers);
            } else {
                roll = RNG.getRandom(dpassWinNumbers);
            }
        }

        if (inputStatus.equals((BetOption.dpass)) && (Ints.contains(dpassWinNumbers, roll))) {
            modifier.add(playerData,inputBet * 2,false);
            SIO.hMultiOutput(playerData,Integer.toString(roll), ("ваши очки: "+playerData.getPoints()),"commands");
            playerData.krepsPart1.addWin(2);
            return 0;
        } else if (inputStatus.equals((BetOption.pass)) && (Ints.contains(passWinNumbers, roll))) {
            modifier.add(playerData,inputBet * 2,false);
            SIO.hMultiOutput(playerData,Integer.toString(roll), ("ваши очки: "+playerData.getPoints()),"non");
            playerData.krepsPart1.addWin(2);
            pointer = roll;
            return stageThree(playerData);// next stage <----------------------------------------------------------------
        } else {
            SIO.hMultiOutput(playerData,Integer.toString(roll), ("ваши очки: "+playerData.getPoints()),"commands");
            playerData.krepsPart1.addLose(1);
            return 0;
        }


    }

    private int stageThree(playerData playerData){
        isAdditionalInputRequired = true;
        SIO.outPut(playerData,"число ставки и количество ставки","non");
        return 4;
    }

    private int stageFour(playerData playerData,String[] input){

        if(input.length == 2) {

            if (!input[0].matches("[0-9]+") || !input[1].matches("[0-9]+")) {
                SIO.outPut(playerData, "incorrect input, please try again (kreps phase2)","non");
                isAdditionalInputRequired = true;
                return 4;
            } else if (Integer.parseInt(input[0]) > 24) {
                SIO.outPut(playerData, "incorrect input, please try again (kreps phase2)","non");
                isAdditionalInputRequired = true;
                return 4;
            } else if (Integer.parseInt(input[1]) > playerData.getPoints()) {
                SIO.outPut(playerData, "у вас недостаточно средств для такой ставки, please try again","non");
                isAdditionalInputRequired = true;
                return 4;
            }
            return stageFive(playerData, pointer, Integer.parseInt(input[0]), Integer.parseInt(input[1]));
        } else {
            SIO.outPut(playerData, "incorrect input, please try again (kreps phase2)","non");
            isAdditionalInputRequired = true;
            return 4;
        }

    }

    private int stageFive(playerData playerData,int pointer,int inputNumber,int inputBet){

        modifier.remove(playerData,inputBet,false);
        //playerData.PointModify(-inputBet, false);

        boolean keepRolling = true;

        do {

            int preRoll = RNG.rollNumber(playerData.krepsPart2.winrate(), phaseTwoWinRate, 2, 24, inputNumber, phaseTwoBaseWinRate);

            //GeneratorOfNumber(preRoll);

            int firsDice, secondDice;

            if (preRoll > 12) {
                secondDice = random.nextInt(25 - preRoll) + preRoll - 12;
                firsDice = preRoll - secondDice;
            } else {
                firsDice = random.nextInt(preRoll - 1) + 1;
                secondDice = preRoll - firsDice;
            }

            if (firsDice == pointer || firsDice == loseNumber || secondDice == pointer || secondDice == loseNumber) {
                keepRolling = false;
                playerData.krepsPart2.addLose(1);
            }

            if ((keepRolling) && (inputNumber == (firsDice + secondDice))) {
                modifier.add(playerData,inputBet * 8,false);
                //playerData.PointModify(+inputBet * 8, false);
                keepRolling = false;
                playerData.krepsPart2.addWin(1);
            }

            SIO.hDoubleOutput(playerData,Integer.toString(firsDice),Integer.toString(secondDice));

            //try { TimeUnit.SECONDS.sleep(10); } catch (InterruptedException e) { System.out.println("delay error(Kreps)"); }

        } while (keepRolling);

        SIO.outPut(playerData,"ваши очки: "+playerData.getPoints(),"commands");

        return 0;

    }
    /*
    //генерирует два квази рандомных числа из квизи рандомного числа
    private void GeneratorOfNumber(int preRoll){
        if (preRoll > 12) {
            secondDice = random.nextInt(25 - preRoll) + preRoll - 12;
            firsDice = preRoll - secondDice;
        } else {
            firsDice = random.nextInt(preRoll - 1) + 1;
            secondDice = preRoll - firsDice;
        }
    }
    */

}
