package com.company.classes;

import com.company.classes.playerinnerclasses.GamePattern;

public class player {

    public player(String name){
        this.Name = name;
    }

    public String Name;

    public GamePattern gameName = new GamePattern();

}