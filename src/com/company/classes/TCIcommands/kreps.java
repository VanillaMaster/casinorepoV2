package com.company.classes.TCIcommands;

import com.company.classes.Gamse.Sloti.SlotMachine;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

public class kreps implements TCICommands {
    private com.company.classes.TCI TCI;

    private SlotMachine slotMachine;

    public kreps(TCI iTCI){
        TCI = iTCI;
        slotMachine = new SlotMachine(iTCI);
    }


    @Override
    public void execute(playerDataShell playerDataShell,String data) {
        boolean isAdditionalInputRequired = slotMachine.play(playerDataShell.getPlayerData(),data);
        //System.out.println(isAdditionalInputRequired);
        if (isAdditionalInputRequired){
            playerDataShell.addToQueue("/kreps");
        }
    }
}
