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

    private Integer points = -1; // сделать для крепс

    public String telegramID = "";

    public String status = "";

    public String displayStatus = "";

    public GameDataPattern krepsPart1 = new GameDataPattern(0.5);
    public GameDataPattern krepsPart2 = new GameDataPattern(0.5);

    public Integer getPoints() {
        return points;
    }

    public void PointModify(int input, boolean applyStatus){
        if (applyStatus){
            points += input;
        } else {
            points += input;
        }
    }


}
