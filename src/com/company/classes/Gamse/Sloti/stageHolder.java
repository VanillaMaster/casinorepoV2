package com.company.classes.Gamse.Sloti;

class stageHolder {
    private SlotMachineV2.stages currentStage = SlotMachineV2.stages.zero;
    public void setCurrentStage(SlotMachineV2.stages stages){
        this.currentStage = stages;
    }
    public SlotMachineV2.stages getCurrentStage() {
        return currentStage;
    }
}
