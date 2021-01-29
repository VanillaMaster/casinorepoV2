package com.company.classes.TCIcommands;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

/**
 * коммнада справки
 */
public class help implements TCICommands {

    private static final String helpText = "Введите /slots - для игры в слоты \nВведите /kreps - для игры в крепс \nВведите /info - для всей доступной информации об игроке \n\nTips:\nQ:как получить статус ?\nA: попросить администрацию и возможно вам его выдадут";

    private TCI TCI;

    private playerDataShell playerDataShell;

    public help(TCI iTCI,playerDataShell iPlayerDataShell) {
        TCI = iTCI;
        playerDataShell = iPlayerDataShell;
    }

    public void execute(String[] data) {

        if (data.length == 0) {
            TCI.sendMsg(helpText, playerDataShell.getPlayerData().telegramID, "commands");
        } else {
            TCI.sendMsg("unexpected arguments", playerDataShell.getPlayerData().telegramID, "commands");
        }
    }
}
