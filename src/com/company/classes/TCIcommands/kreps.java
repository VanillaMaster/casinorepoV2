package com.company.classes.TCIcommands;

import com.company.classes.Gamse.Kreps.Kreps;
import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;
/**
 * команда запуска Крепс в telegram
 */
public class kreps implements TCICommands {
    private com.company.classes.TCI TCI;

    private Kreps Kreps;

    private playerDataShell playerDataShell;

    public kreps() { }

    public void init(TCI iTCI,playerDataShell iPlayerDataShell) {
        TCI = iTCI;
        playerDataShell = iPlayerDataShell;
        Kreps = new Kreps(iTCI);
    }


    @Override
    public void execute(String[] data) {
        boolean isAdditionalInputRequired = Kreps.play(playerDataShell.getPlayerData(), data);
        if (isAdditionalInputRequired) {
            playerDataShell.addToQueue("/kreps");
        }
    }
}
