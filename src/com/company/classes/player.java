package com.company.classes;

import com.company.classes.playerinnerclasses.GamePattern;

public class player {

    public player(String name) {
        this.Name = name;
        this.points = 1000;
    }

    public Integer points;

    public String Name;

    public GamePattern gameName = new GamePattern();

    public GamePattern krepsParth1 = new GamePattern();
    public GamePattern krepsParth2 = new GamePattern();

}