package com.company.classes.TCIcommands;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

/**
 * Комагда информаци о пользователе
 */
public class info implements TCICommands {

    private com.company.classes.TCI TCI;

    public info(TCI iTCI) {
        TCI = iTCI;
    }

    public void execute(playerDataShell playerDataShell, String data) {
        String outputPoints = "Points: " + playerDataShell.getPlayerData().getPoints().toString();
        String outputID = "ID: " + playerDataShell.getPlayerData().telegramID;
        String outputStatus = "Status: " + playerDataShell.getPlayerData().displayStatus;
        TCI.sendMsg(outputPoints + "\n" + outputID + "\n" + outputStatus, playerDataShell.getPlayerData().telegramID,"non");
    }
}
