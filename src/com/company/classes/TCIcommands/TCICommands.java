package com.company.classes.TCIcommands;

import com.company.classes.playerDataConstruct.playerDataShell;

public interface TCICommands {
    //выполняет команду
    void execute(playerDataShell playerDataShell, String data);

}
