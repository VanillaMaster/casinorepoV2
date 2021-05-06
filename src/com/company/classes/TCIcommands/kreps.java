package com.company.classes.TCIcommands;

import com.company.classes.Gamse.Kreps.krepsV2;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;
/**
 * команда запуска Крепс в telegram
 */
public class kreps implements TCICommands {
    private com.company.classes.TCI TCI;

    private krepsV2 Kreps;

    private playerDataShell playerDataShell;

    public kreps() { }

    public void init(TCI iTCI,playerDataShell iPlayerDataShell) {
        TCI = iTCI;
        playerDataShell = iPlayerDataShell;
        Kreps = new krepsV2(iTCI);
    }


    @Override
    public void execute(String[] data) {
        boolean isAdditionalInputRequired = Kreps.play(playerDataShell.getPlayerData(), data);
        if (isAdditionalInputRequired) {
            playerDataShell.addToQueue("/kreps");
        }
    }
}
