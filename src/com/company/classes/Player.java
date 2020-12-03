package com.company.classes;

import com.company.classes.playerinnerclasses.GamePattern;

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


    public GamePattern krepsParth1 = new GamePattern(0.5);
    public GamePattern krepsParth2 = new GamePattern(0.5);

}