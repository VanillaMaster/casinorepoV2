package com.company.classes.playerinnerclasses;

public class GamePattern {
    public GamePattern(){
        pointer = 0;
        winrateTimeLine = new int[200];
        for (int i=0; i < winrateTimeLine.length; i+=2)
        {
            winrateTimeLine[i]=1;
        }
        for (int i=1; i < winrateTimeLine.length; i+=2)
        {
            winrateTimeLine[i]=0;
        }
    }
    public int winrate(){
        int tmpWinrate = 0;
        for (int i=0; i < winrateTimeLine.length; i++)
        {
            tmpWinrate+=winrateTimeLine[i];
        }

        return tmpWinrate/2;
    }

    private int[] winrateTimeLine;
    private int pointer = 0;

    public void addWin(){
        winrateTimeLine[shiftPointer()] = 1;
    }

    public void addLose(){
        winrateTimeLine[shiftPointer()] = 0;
    }

    private int shiftPointer(){
        pointer ++;
        if (pointer >= 200) { pointer = 0;}
        return pointer;
    }

}
