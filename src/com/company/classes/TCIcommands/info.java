package com.company.classes.TCIcommands;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

/**
 * Комагда информаци о пользователе
 */
public class info implements TCICommands {

    private com.company.classes.TCI TCI;

    private playerDataShell playerDataShell;

    public info() { }

    public void init(TCI iTCI,playerDataShell iPlayerDataShell) {
        TCI = iTCI;
        playerDataShell = iPlayerDataShell;
    }

    public void execute(String[] data) {
        String outputPoints = "Points: " + playerDataShell.getPlayerData().getPoints().toString();
        String outputID = "ID: " + playerDataShell.getPlayerData().telegramID;
        String outputStatus = "Status: " + playerDataShell.getPlayerData().displayStatus;

        if (data.length == 0) {
            TCI.sendMsg(outputPoints + "\n" + outputID + "\n" + outputStatus, playerDataShell.getPlayerData().telegramID, "non");
        } else {
            TCI.sendMsg("unexpected arguments", playerDataShell.getPlayerData().telegramID, "commands");
        }
    }
}
