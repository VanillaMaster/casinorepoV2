package com.company.classes.TCIcommands;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

import java.util.HashMap;
import java.util.Map;

/**
 * коммнада справки
 */
public class help implements TCICommands {

    private static final String helpText = "Введите /slots - для игры в слоты \nВведите /kreps - для игры в крепс \nВведите /info - для всей доступной информации об игроке\nВведите /help \"command name\" - для дополнительной справки о комманде \n\nTips:\nQ:как получить статус ?\nA: попросить администрацию и возможно вам его выдадут";

    private Map<String, String> helpTexts = new HashMap<>(){
        {
            put("kreps","1 этап игры: Игрок бросает 1 12-гранный кубик. Игрок имеет возможность ставить на pass или dpass с выигрышем х2, если игрок поставил на pass и выиграл, начинается второй этап игры.\n"+
                "Условия победы pass: 4,5,6,8,9,10.\n"+
                "Условия победы dpass: 1,2,3,7,11,12.\n"+
                "2 этап игры: Игрок бросает 2 12-гранныч кубика. Игрок имеет возможность поставить на любое число от 2 до 24 с выигрышем х8.\n"+
                "условие поражения: если на одном из 12-гранных кубиков выпадет число 7 или число равное выпавшему на первом кубике из первого этапа.\n"+
                "условие победы: сумма чисел двух 12-гранных кубиков равна поставленному числу.");
            put("slots","Игрок вводит число ставки. При выпадениее 2 одинаковых чисел выигрыш х2, а при выпадении 3 одинаковых чисел подряд выигрыш х7.\n"+
                "Так-же есть спец комбинации 666 - очки пользователя=0, 777 - выигрышь х100");
            put("help","\"/help\" - комманда выводящая справку");
        }
    };

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
            if (data.length == 1){

                if (helpTexts.containsKey(data[0])) {
                    TCI.sendMsg(helpTexts.get(data[0]), playerDataShell.getPlayerData().telegramID, "commands");
                } else {
                    TCI.sendMsg("unexpected arguments", playerDataShell.getPlayerData().telegramID, "commands");
                }
            } else {
                TCI.sendMsg("unexpected arguments", playerDataShell.getPlayerData().telegramID, "commands");
            }
        }
    }
}
