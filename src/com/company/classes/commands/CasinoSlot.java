package com.company.classes.commands;

import com.company.classes.Gamse.Kreps.Kreps;
import com.company.classes.Gamse.Sloti.SlotMachine;
import com.company.classes.Player;

public class CasinoSlot implements Command {

    public CasinoSlot(Player iPlayer){
        this.p = iPlayer;
    }

    private Player p;
    SlotMachine k = new SlotMachine();

    public void execute() {
        if (p.isLoggedIn){
            k.play(p);
        } else {
            System.out.println("u should login first");
        }
    }
}