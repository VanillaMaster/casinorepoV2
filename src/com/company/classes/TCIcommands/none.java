package com.company.classes.TCIcommands;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

public class none implements TCICommands{

    public void execute(String[] data) {
        TCI.sendMsg("unknown command, \"/help\" for command list",playerDataShell.getPlayerData().telegramID,"non");
    }

    private TCI TCI;

    private playerDataShell playerDataShell;

    public none() { }

    public void init(TCI iTCI,playerDataShell iPlayerDataShell) {
        TCI = iTCI;
        playerDataShell = iPlayerDataShell;
    }
}
