package com.company.classes.playerDataConstruct;

import com.company.classes.playerinnerclasses.GameDataPattern;

import java.time.format.DateTimeFormatter;
import java.util.Random;

// содержит информацию об игроке
public class playerData {

    private DateTimeFormatter formatter(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    private Random random(){
        return new Random();
    }

    public playerData(String ID) {
        telegramID = ID;
        points = 1000;
    }

    public playerData() {
    }

    private Integer points = -1;

    public String telegramID = "";

    public String status = "";

    public String displayStatus = "";

    private String date = formatter().format(java.time.LocalDateTime.now());


    public GameDataPattern krepsPart1 = new GameDataPattern(0.5);
    public GameDataPattern krepsPart2 = new GameDataPattern(0.5);

    public Integer getPoints() {
        return points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //параша, переделать в сущьности (потом)
    public void PointModify(int input, boolean applyStatus){
        if (!applyStatus){
            points += input;
        } else {

            switch (status) {

                case ("vip"):
                    //System.out.println("vip status");
                    if (random().nextInt(2) == 1){
                        points += (input*2);
                        //System.out.println("vip doubling");
                    } else {
                        points += input;
                    }
                break;

                case ("default"):
                    points += input;
                break;

                default:
                    points += input;
                    System.out.println("unexpected status");
                break;

            }

        }
    }


}
