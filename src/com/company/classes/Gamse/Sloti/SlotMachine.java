package com.company.classes.Gamse.Sloti;

import com.company.classes.Player;
import com.company.classes.Gamse.Game;
import com.company.classes.NTRandom;
import com.company.classes.Player;
import com.company.classes.utilits.SmartIO;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SlotMachine implements Game{
    private final NTRandom RNG = new NTRandom();
    private final SmartIO SIO = new SmartIO();
    private final Random random = new Random();


    public void play(Player p){SlotMachineIO(p); }


    private void SlotMachineIO(Player p) {
        System.out.println("количество ставки");
        String input;

        do {
            do {
                input = SIO.sInput();
                if (!input.matches("[0-9]+")) {
                    SIO.sOutput("incorrect input, please try again");
                }
            } while (!input.matches("[0-9]+"));

            if (Integer.parseInt(input) > p.points) {
                SIO.sOutput("у вас недостаточно средств для такой ставки, please try again");
            }
        } while (Integer.parseInt(input) > p.points);

        SlotMachineMath(p,Integer.parseInt(input));
    }

    private void SlotMachineMath(Player p, int inputBet) {
        p.points -= inputBet;
        int roll1=RNG.roll(50,50,1,7);
        int roll2=RNG.roll(50,50,1,7);
        int roll3=RNG.roll(50,50,1,7);
        SIO.hTripleOutput(roll1,roll2,roll3);
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
        System.out.println("ваши очки: "+p.points);
    }
}

