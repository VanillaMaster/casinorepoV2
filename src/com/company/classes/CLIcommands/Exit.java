package com.company.classes.CLIcommands;

import com.company.classes.legacy.CLI;

/**
 * комманда выхода
 */
public class Exit implements Command {

    public Exit(CLI iCli){ this.cli = iCli; }

    private CLI cli;

    public void execute() {
        cli.Stop();
    }

}
