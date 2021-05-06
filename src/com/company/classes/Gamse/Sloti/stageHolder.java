package com.company.classes.Gamse.Sloti;

class stageHolder {
    private slotMachineV2.stages currentStage = slotMachineV2.stages.zero;
    public void setCurrentStage(slotMachineV2.stages stages){
        this.currentStage = stages;
    }
    public slotMachineV2.stages getCurrentStage() {
        return currentStage;
    }
}
