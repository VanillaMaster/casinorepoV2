package com.company.classes.pointsModifier.statusesProcessing;

import com.company.classes.playerDataConstruct.playerData;

public interface statusProcessing {
    //применяет эффект статуса
    public void execute(playerData target, int amount);

}
