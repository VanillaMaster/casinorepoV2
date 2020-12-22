package com.company.classes.TCIcommands;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

/**
 * коммнада справки
 */
public class help implements TCICommands {

    private static final String helpText = "Введите /slots - для игры в слоты \nВведите /kreps - для игры в крепс \nВведите /info - для всей доступной информации об игроке ";
    private TCI TCI;

    public help(TCI iTCI){
        TCI = iTCI;
    }

    public void execute(playerDataShell playerDataShell,String data) {
        TCI.sendMsg(helpText,playerDataShell.getPlayerData().telegramID);
    }
}
