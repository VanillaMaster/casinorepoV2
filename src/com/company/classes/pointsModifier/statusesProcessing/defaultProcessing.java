package com.company.classes.pointsModifier.statusesProcessing;

import com.company.classes.playerDataConstruct.playerData;

public class defaultProcessing implements statusProcessing {

    public void execute(playerData target, int amount) {
        target.addPoints(amount);
    }
}
