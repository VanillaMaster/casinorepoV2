package com.company.classes.playerDataConstruct;

import com.company.classes.TCI;

import java.util.Timer;
import java.util.TimerTask;

public class playerKillTimer extends TimerTask {

    private TCI TCI;
    private String UserID;
    private playerDataShell Shell;
    private Timer timer;

    public playerKillTimer(TCI iTCI, String ID, playerDataShell IShell, Timer ITimer) {
        TCI = iTCI;
        UserID = ID;
        Shell = IShell;
        timer = ITimer;
    }

    @Override
    public void run() {

        Shell.LifeSpanCycle();

        if (Shell.getCurrentLifeSpan() <= 0) {
            System.out.println(("time to die: " + UserID));

            timer.cancel();
            timer.purge();

            TCI.removePlayer(UserID);

        }

    }
}
