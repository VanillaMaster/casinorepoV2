package com.company.classes.playerinnerclasses;

public class GamePattern {
    public GamePattern(double defaultWinFactor) {
        winFactor = defaultWinFactor;
    }

    public double winFactor;

    public int winrate() {
        return (int)(winFactor*100);
    }

    public void addWin(int amount) {
        winFactor+=(0.01*amount);
    }

    public void addLose(int amount) {
        winFactor-=(0.01*amount);
    }


}
