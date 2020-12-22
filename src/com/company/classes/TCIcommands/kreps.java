package com.company.classes.TCIcommands;

import com.company.classes.Gamse.Kreps.Kreps;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

public class kreps implements TCICommands {
    private com.company.classes.TCI TCI;

    private Kreps Kreps;

    public kreps(TCI iTCI){
        TCI = iTCI;
        Kreps = new Kreps(iTCI);
    }


    @Override
    public void execute(playerDataShell playerDataShell,String data) {
        boolean isAdditionalInputRequired = Kreps.play(playerDataShell.getPlayerData(),data);
        //System.out.println(isAdditionalInputRequired);
        if (isAdditionalInputRequired){
            playerDataShell.addToQueue("/kreps");
        }
    }
}
