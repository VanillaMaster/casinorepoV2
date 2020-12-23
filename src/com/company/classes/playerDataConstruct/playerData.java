package com.company.classes.playerDataConstruct;

import com.company.classes.playerinnerclasses.GameDataPattern;
// содержит информацию об игроке
public class playerData {

    public playerData(String ID) {
        telegramID = ID;
        points = 1000;
    }

    public playerData() {
    }

    public Integer points = -1;

    //public String Name = "";

    public String telegramID = "";

    public GameDataPattern krepsPart1 = new GameDataPattern(0.5);
    public GameDataPattern krepsPart2 = new GameDataPattern(0.5);


}
