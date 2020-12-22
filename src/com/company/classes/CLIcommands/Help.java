package com.company.classes.CLIcommands;

/**
 * коммнада справки
 */
public class Help implements Command{

    public void execute() {
        System.out.println("\"/help\" - show this info\n\"/exit\" - exit bot/program/idk\n\"/register\" - register new user");
    }
}
