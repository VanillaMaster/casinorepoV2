package com.company.classes.Gamse;

import com.company.classes.playerDataConstruct.playerData;

public interface TCIGame{
    /**
     * @param p игрок
     * Запускает игру в telegram
     */
boolean play(playerData p,String data);
}
