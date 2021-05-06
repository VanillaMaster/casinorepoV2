package com.company.classes.Gamse.Kreps;

class sOneValidData {
    private String error;
    private int inputBet;
    private BetOptions betOption;

    public sOneValidData(String error){
        this.betOption = null;
        this.inputBet = 0;
        this.error = error;
    }

    public sOneValidData(BetOptions betOption, int inputBet, String error){
        this.betOption = betOption;
        this.inputBet = inputBet;
        this.error = error;
    }
    public BetOptions getBetOption() {
        return betOption;
    }
    public int getInputBet() {
        return inputBet;
    }
    public String getError() {
        return error;
    }
}
