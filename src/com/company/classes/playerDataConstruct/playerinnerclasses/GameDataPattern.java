package com.company.classes.playerDataConstruct.playerinnerclasses;

/**
 * хранит игровые данные
 */
public class GameDataPattern {
    public GameDataPattern(double defaultWinFactor) {
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
