package com.company.classes.utilits;

public enum KeyboardsList {
    commands,
    slots,
    kreps,
    non;

    public static KeyboardsList get(String s)
    {
        for(KeyboardsList choice:values())
            if ((choice.name()).equals(s))
                return choice;
        return KeyboardsList.non;
    }
}
