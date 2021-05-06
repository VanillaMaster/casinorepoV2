package com.company.classes.Gamse.Kreps;

enum BetOptions {
    pass(new int[] {1,2,3,7,11,12}),
    dpass(new int[]{4, 5, 6, 8, 9, 10}),
    NaN;

    int[] WinNumbers;
    BetOptions(int[] i) {
        WinNumbers = i;
    }
    int[] getWinNumbers() {
        return WinNumbers;
    }

    BetOptions() {
    }

    public static BetOptions get(String s)
    {
        for(BetOptions choice:values())
            if (choice.name().equals(s))
                return choice;
        return BetOptions.NaN;
    }
}
