package com.company.classes.TCIcommands;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerDataShell;

public interface TCICommands {
    //выполняет команду
    void execute(String[] data);

    void init(TCI iTCI, playerDataShell iPlayerDataShell);

}
