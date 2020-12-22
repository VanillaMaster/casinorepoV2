package com.company.classes.legacy;

import com.company.classes.playerinnerclasses.GameDataPattern;

/**
 * хранит данные: имени, количестве поинтов и игре
 */
public class Player {

    public Player(String name) {
        this.Name = name;
        this.points = 1000;
    }

    public Player(){}

    public void assemble(Integer iPoints,String iName, GameDataPattern iKrepsPart1,GameDataPattern iKrepsPart2){
        points = iPoints;
        Name = iName;
        krepsPart1 = iKrepsPart1;
        krepsPart2 = iKrepsPart2;
        isLoggedIn = true;
    }

    public Integer points = -1;

    public String Name = "";

    public boolean isLoggedIn = false;

    public GameDataPattern krepsPart1 = new GameDataPattern(0.5);
    public GameDataPattern krepsPart2 = new GameDataPattern(0.5);

}