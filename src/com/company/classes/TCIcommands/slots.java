package com.company.classes.TCIcommands;

import com.company.classes.Gamse.Sloti.SlotMachine;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

/**
 * команда запуска Слот машины в telegram
 */
public class slots implements TCICommands {

    private TCI TCI;

    private SlotMachine slotMachine;

    private playerDataShell playerDataShell;

    public slots(TCI iTCI, playerDataShell iPlayerDataShell) {
        TCI = iTCI;
        playerDataShell = iPlayerDataShell;
        slotMachine = new SlotMachine(iTCI);
    }


    public void execute(String[] data) {
        boolean isAdditionalInputRequired = slotMachine.play(playerDataShell.getPlayerData(), data);
        if (isAdditionalInputRequired) {
            playerDataShell.addToQueue("/slots");
        }
    }

}
