package com.company.classes.Gamse.Kreps;

class validation {
    public static sOneValidData validateOne(String[] data, Integer points){
        if (data.length!=2){
            return new sOneValidData("incorrect input, please try again (kreps phase1) code#2");
        }
        if (!data[0].matches("(pass|dpass)") || !data[1].matches("[0-9]+")){
            return new sOneValidData("incorrect input, please try again (kreps phase1) code#2\"");
        }
        if (Integer.parseInt(data[1]) > points){
            return new sOneValidData("у вас недостаточно средств для такой ставки, please try again");
        }
        return new sOneValidData(BetOptions.get(data[0]),Integer.parseInt(data[1]),null);
    }
    public static String validateTwo(String[] data,Integer points){
        if (data.length!=2){
            return "incorrect input, please try again (kreps phase2) code#5";
        }
        if (!data[0].matches("[0-9]+") || !data[1].matches("[0-9]+")){
            return "incorrect input, please try again (kreps phase2) code#3";
        }
        if (Integer.parseInt(data[0]) > 24){
            return "incorrect input, please try again (kreps phase2) code#4";
        }
        if (Integer.parseInt(data[1]) > points){
            return "у вас недостаточно средств для такой ставки, please try again";
        }
        return null;
    }
}
