package com.company.classes.Gamse.Sloti;

class validate {
   public static   String validateOne(String[] data,Integer points) {
        if (data.length != 1) {
            return "incorrect input, please try again (slots) code#5";
        }
        if (!data[0].matches("[0-9]+")) {
            return "incorrect input, please try again (slots) code#3";
        }
        if (Integer.parseInt(data[0]) > points) {
            return "у вас недостаточно средств для такой ставки, please try again code#4";
        }
        return null;
    }
}
