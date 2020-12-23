package com.company.classes.Gamse.Kreps;

import com.company.classes.Gamse.TCIGame;
import com.company.classes.NTRandom;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.utilits.TCISmartIO;

import java.util.Random;

/**
 * игра Крепс
 */
public class Kreps implements TCIGame {

    enum betOption {
        pass,
        dpass
    }

    int phaseOneWinRate = 50; //винрейт фазы 1
    int phaseTwoWinRate = 50; //винрейт фазы 2
    int phaseTwoBaseWinRate = 4; //базовый винрейт для фазы 2

    private final int[] dpassWinNumbers = new int[]{1, 2, 3, 7, 11, 12}; //выйгрышные числа для dpass
    private final int[] passWinNumbers = new int[]{4, 5, 6, 8, 9, 10, 11}; //выйгрышные числа для pass

    private final NTRandom RNG = new NTRandom();
    private final TCISmartIO SIO;
    private final Random random = new Random();

    private int pointer = -1;
    private boolean isAdditionalInputRequired = false;

    private int stage = 0;
    //private TCI TCI;

    public Kreps(TCI iTCI) {
        //TCI = iTCI;
        SIO = new TCISmartIO(iTCI);
        stage = 0;
    }


    /**
     * запучкает игру Крепс
     *
     * @param p игрок
     */
    public boolean play(playerData p, String data) {

        isAdditionalInputRequired = false;

        System.out.println("kreps stage: " + stage);

        switch (stage) {
            case 0:
                stage = stageZero(p);
                break;

            case 1:
                stage = stageOne(p, data);
                break;

            case 3:
                stage = stageThree(p);
                break;

            case 4:
                stage = stageFour(p, data);
                break;

            default:
                SIO.outPut(p, "wtf error № " + stage);
                break;
        }

        return isAdditionalInputRequired;

    }

    private int stageZero(playerData p) {
        SIO.outPut(p, "pass или dpass и количество ставки");
        isAdditionalInputRequired = true;
        return 1;
    }

    private int stageOne(playerData p, String input) {

        if (!input.matches("(pass|dpass) [0-9]+")) {
            SIO.outPut(p, "incorrect input, please try again");
            isAdditionalInputRequired = true;
            return 1;
        }
        if (Integer.parseInt(input.split(" ")[1]) > p.points) {
            SIO.outPut(p, "у вас недостаточно средств для такой ставки, please try again");
            isAdditionalInputRequired = true;
            return 1;
        }


        return stageTwo(p, input.split(" ")[0], Integer.parseInt(input.split(" ")[1]));//next stage
    }

    private int stageTwo(playerData p, String inputStatus, int inputBet) {

        p.points -= inputBet;

        int winChance = RNG.roll(p.krepsPart1.winrate(), phaseOneWinRate, 0, 1);
        int roll;

        if (winChance == 0) {
            if (inputStatus.equals((betOption.dpass).toString())) {
                roll = RNG.getRandom(dpassWinNumbers);
            } else {
                roll = RNG.getRandom(passWinNumbers);
            }
        } else {
            if (inputStatus.equals((betOption.dpass).toString())) {
                roll = RNG.getRandom(passWinNumbers);
            } else {
                roll = RNG.getRandom(dpassWinNumbers);
            }
        }

        String rollString = Integer.toString(roll);

        if (inputStatus.equals((betOption.dpass).toString()) && (rollString.matches("1|2|3|7|11|12"))) {
            p.points += (inputBet * 2);
            SIO.hMultiOutput(p, Integer.toString(roll), ("ваши очки: " + p.points));
            p.krepsPart1.addWin(2);
            return 0;
        } else if (inputStatus.equals((betOption.pass).toString()) && (rollString.matches("4|5|6|8|9|10|11"))) {
            p.points += (inputBet * 2);
            SIO.hMultiOutput(p, Integer.toString(roll), ("ваши очки: " + p.points));
            p.krepsPart1.addWin(2);
            pointer = roll;
            return stageThree(p);// next stage <----------------------------------------------------------------
        } else {
            SIO.hMultiOutput(p, Integer.toString(roll), ("ваши очки: " + p.points));
            p.krepsPart1.addLose(1);
            return 0;
        }


    }

    private int stageThree(playerData p) {
        isAdditionalInputRequired = true;
        SIO.outPut(p, "число ставки и количество ставки");
        return 4;
    }

    private int stageFour(playerData p, String input) {

        if (!input.matches("[0-9]+ [0-9]+")) {
            SIO.outPut(p, "incorrect input, please try again");
            isAdditionalInputRequired = true;
            return 4;
        } else if (Integer.parseInt(input.split(" ")[0]) > 24){
            SIO.outPut(p, "incorrect input, please try again");
            isAdditionalInputRequired = true;
            return 4;
        } else if (Integer.parseInt(input.split(" ")[1]) > p.points) {
            SIO.outPut(p, "у вас недостаточно средств для такой ставки, please try again");
            isAdditionalInputRequired = true;
            return 4;
        }

        return stageFive(p, pointer, Integer.parseInt(input.split(" ")[0]), Integer.parseInt(input.split(" ")[1]));

    }

    private int stageFive(playerData p, int pointer, int inputNumber, int inputBet) {

        p.points -= inputBet;

        int firsDice;
        int secondDice;
        boolean keepRolling = true;

        do {

            int preRoll = RNG.rollNumber(p.krepsPart2.winrate(), phaseTwoWinRate, 2, 24, inputNumber, phaseTwoBaseWinRate);

            if (preRoll > 12) {
                secondDice = random.nextInt(25 - preRoll) + preRoll - 12;
                firsDice = preRoll - secondDice;
            } else {
                firsDice = random.nextInt(preRoll - 1) + 1;
                secondDice = preRoll - firsDice;
            }

            if (firsDice == pointer || firsDice == 7 || secondDice == pointer || secondDice == 7) {
                keepRolling = false;
                p.krepsPart2.addLose(1);
            }

            if ((keepRolling) && (inputNumber == (firsDice + secondDice))) {
                p.points += (inputBet * 8);
                keepRolling = false;
                p.krepsPart2.addWin(1);
            }

            SIO.hDoubleOutput(p, Integer.toString(firsDice), Integer.toString(secondDice));

            //try { TimeUnit.SECONDS.sleep(10); } catch (InterruptedException e) { System.out.println("delay error(Kreps)"); }

        } while (keepRolling);

        SIO.outPut(p, "ваши очки: " + p.points);

        return 0;

    }

}
