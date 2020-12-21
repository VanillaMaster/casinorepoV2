package com.company.classes.commands;

import com.company.classes.Player;
import com.company.classes.Gamse.Kreps.Kreps;
import com.company.classes.Gamse.Sloti.SlotMachine;

public class Info implements Command{
    public Info(Player iPlayer){
        this.p = iPlayer;
    }
    private Player p;
    public void execute() {
        if(p.isLoggedIn){
            System.out.println("Name "+p.Name);
            System.out.println("Points "+p.points);
        }else {
            System.out.println("u should login first");
        }
    }
}