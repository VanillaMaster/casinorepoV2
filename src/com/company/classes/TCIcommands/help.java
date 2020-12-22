package com.company.classes.TCIcommands;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

/**
 * коммнада справки
 */
public class help implements TCICommands {

    private static final String helpText = "WIP";

    private TCI TCI;

    public help(TCI iTCI){
        TCI = iTCI;
    }

    public void execute(playerDataShell playerDataShell,String data) {
        //System.out.println("help command: "+playerDataShell.getPlayerData().telegramID);
        TCI.sendMsg(helpText,playerDataShell.getPlayerData().telegramID);
    }
}
