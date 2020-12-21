package com.company.classes.commands;

import com.company.classes.Gamse.Kreps.Kreps;
import com.company.classes.Player;

public class CasinoKreps implements Command {

    public CasinoKreps(Player iPlayer){
        this.p = iPlayer;
    }

    private Player p;
    Kreps k = new Kreps();

    public void execute() {
        if (p.isLoggedIn){
            k.play(p);
        } else {
            System.out.println("u should login first");
        }
    }
}
