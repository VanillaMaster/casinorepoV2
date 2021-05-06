package com.company.classes.Gamse.Kreps;

class stageHolder {
    private krepsV2.stages currentStage = krepsV2.stages.zero;

    private int pointer;

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public int getPointer() {
        return pointer;
    }

    public void setCurrentStage(krepsV2.stages stages){
        this.currentStage = stages;
    }
    public krepsV2.stages getCurrentStage() {
        return currentStage;
    }
}
