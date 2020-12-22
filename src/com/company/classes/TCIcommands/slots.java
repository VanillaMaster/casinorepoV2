package com.company.classes.TCIcommands;

import com.company.classes.Gamse.Sloti.SlotMachine;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

public class slots implements TCICommands {

    private TCI TCI;

    private SlotMachine slotMachine;

    public slots(TCI iTCI){
        TCI = iTCI;
        slotMachine = new SlotMachine(iTCI);
    }


    @Override
    public void execute(playerDataShell playerDataShell,String data) {
        System.out.println("stying play slots");
        boolean isAdditionalInputRequired = slotMachine.play(playerDataShell.getPlayerData(),data);
        //System.out.println(isAdditionalInputRequired);
        if (isAdditionalInputRequired){
            System.out.println("slot if proc");
            playerDataShell.addToQueue("slots");
        }
    }

}
