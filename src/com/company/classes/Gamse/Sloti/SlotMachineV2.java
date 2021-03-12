package com.company.classes.Gamse.Sloti;

import com.company.classes.Gamse.TCIGame;
import com.company.classes.NTRandom;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.pointsModifier.pointsModifier;

public class SlotMachineV2 implements TCIGame {

    private enum test {
        TEST("asd");

        test(String patternContent) { }
    }

    private final NTRandom RNG = new NTRandom();
    //private final Random random = new Random();
    private final pointsModifier modifier = new pointsModifier();

    private boolean isAdditionalInputRequired = false;

    private int stage;

    private TCI TCI;

    public SlotMachineV2(TCI iTCI){
        TCI = iTCI;
        stage = 0;
    }

    public boolean play(playerData playerData, String[] data) {
        return false;
    }


    private boolean stageZero(playerData playerData){
        TCI.sendMsg("количество ставки",playerData.telegramID,"slots");
        return true;
    }

}
