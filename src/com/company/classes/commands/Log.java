package com.company.classes.commands;

import com.company.classes.Player;
import com.google.gson.Gson;

public class Log implements Command{

    public Log(Player iPlayer){ this.p = iPlayer; }

    private Player p;
    private final Gson gson = new Gson();

    public void execute() {
        System.out.println(gson.toJson(p));
    }

}
