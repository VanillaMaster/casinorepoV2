package com.company.classes.Gamse;

import com.company.classes.playerDataConstruct.playerData;

public interface TCIGame{
    /**
     * @param playerData данные игрока
     * Запускает игру в telegram
     */
boolean play(playerData playerData,String[] data);
}
