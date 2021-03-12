package com.company.classes.TCIcommands;

import com.company.classes.Gamse.Sloti.SlotMachineV2;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

/**
 * команда запуска Слот машины в telegram
 */
public class slots implements TCICommands {

    private TCI TCI;

    private SlotMachineV2 slotMachine;

    private playerDataShell playerDataShell;

    public slots() { }

    public void init(TCI iTCI,playerDataShell iPlayerDataShell) {
        TCI = iTCI;
        playerDataShell = iPlayerDataShell;
        slotMachine = new SlotMachineV2(iTCI);
    }


    public void execute(String[] data) {
        boolean isAdditionalInputRequired = slotMachine.play(playerDataShell.getPlayerData(), data);
        if (isAdditionalInputRequired) {
            playerDataShell.addToQueue("/slots");
        }
    }

}
