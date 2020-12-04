package com.company.classes.commands;

import com.company.classes.CLI;

public class Exit implements Command {

    public Exit(CLI iCli){ this.cli = iCli; }

    private CLI cli;

    public void execute() {
        cli.Stop();
    }

}
