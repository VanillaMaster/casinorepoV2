package com.company.classes.TCIcommands;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

/**
 * Debug комманда, выводит текущую продолжительность жизни (не обнавляя её)
 */
public class lifespan implements TCICommands {

    private com.company.classes.TCI TCI;

    public lifespan(TCI iTCI) {
        TCI = iTCI;
    }

    public void execute(playerDataShell playerDataShell, String data) {
        String outputLifeSpan = "LifeSpan: " + playerDataShell.getCurrentLifeSpan();
        TCI.sendMsg(outputLifeSpan, playerDataShell.getUserID(),"non");
    }
}
