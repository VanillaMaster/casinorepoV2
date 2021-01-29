package com.company.classes.playerDataConstruct;

import com.company.classes.playerinnerclasses.GameDataPattern;

import java.time.format.DateTimeFormatter;

// содержит информацию об игроке
public class playerData {

    public playerData(String ID) {
        telegramID = ID;
        points = 1000;
    }

    private Integer points = -1;

    public String telegramID = "";

    public String status = "default";

    public String displayStatus = "очередняра";

    private String date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(java.time.LocalDateTime.now());


    public GameDataPattern krepsPart1 = new GameDataPattern(0.5);
    public GameDataPattern krepsPart2 = new GameDataPattern(0.5);

    public Integer getPoints() { return points; }

    public String getStatus() {
        return status;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void addPoints(Integer points) {
        this.points += points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
