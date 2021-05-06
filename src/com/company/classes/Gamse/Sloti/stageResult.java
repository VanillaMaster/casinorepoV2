package com.company.classes.Gamse.Sloti;

import java.util.ArrayList;

class stageResult {
    private boolean isAdditionalInputRequired;
    private ArrayList<String> responseText = new ArrayList<>();
    private SlotMachineV2.stages stage;


    stageResult(boolean isAdditionalInputRequired, String responseText, SlotMachineV2.stages stage){
        this.isAdditionalInputRequired = isAdditionalInputRequired;
        this.responseText.add(responseText);
        this.stage = stage;
    }

    stageResult(boolean isAdditionalInputRequired, SlotMachineV2.stages stage){
        this.isAdditionalInputRequired = isAdditionalInputRequired;
        this.stage = stage;
    }

    public void addResponse(String responseText){
        this.responseText.add(responseText);
    }

    public boolean isAdditionalInputRequired(){
        return isAdditionalInputRequired;
    }

    public String[] getResponseText() {
        return responseText.toArray(new String[0]);
    }

    public SlotMachineV2.stages getStage() {
        return stage;
    }
}
