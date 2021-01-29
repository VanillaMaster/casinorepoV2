package com.company.classes.TCIcommands;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

/**
 * Debug комманда, выводит текущую продолжительность жизни (не обнавляя её)
 */
public class lifespan implements TCICommands {

    private TCI TCI;

    private playerDataShell playerDataShell;

    public lifespan(TCI iTCI,playerDataShell iPlayerDataShell) {
        TCI = iTCI;
        playerDataShell = iPlayerDataShell;
    }

    public void execute(String[] data) {
        String outputLifeSpan = "LifeSpan: " + playerDataShell.getCurrentLifeSpan();

        if (data.length == 0) {
            TCI.sendMsg(outputLifeSpan, playerDataShell.getUserID(),"non");
        } else {
            TCI.sendMsg("unexpected arguments", playerDataShell.getPlayerData().telegramID, "commands");
        }
    }
}
