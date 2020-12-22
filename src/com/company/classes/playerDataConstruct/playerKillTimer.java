package com.company.classes.playerDataConstruct;

import java.util.HashMap;
import java.util.TimerTask;

public class playerKillTimer extends TimerTask {

    private HashMap map;
    private playerDataShell shell;

    public playerKillTimer(HashMap iMap,playerDataShell iShell){
        map = iMap;
        shell = iShell;
    }

    @Override
    public void run() {
        System.out.println("timer proc");
        shell.currentLifeSpan--;
        if (shell.currentLifeSpan>=0){
            // работает ли ?
            map.remove(shell.getPlayerData().telegramID);
        }
    }
}
