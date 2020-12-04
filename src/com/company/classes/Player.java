package com.company.classes;

import com.company.classes.playerinnerclasses.GameDataPattern;

/**
 * хранит данные: имени, количестве поинтов и игре
 */
public class Player {

    public Player(String name) {
        this.Name = name;
        this.points = 1000;
    }

    public Integer points;

    public String Name;


    public GameDataPattern krepsParth1 = new GameDataPattern(0.5);
    public GameDataPattern krepsPart2 = new GameDataPattern(0.5);

}