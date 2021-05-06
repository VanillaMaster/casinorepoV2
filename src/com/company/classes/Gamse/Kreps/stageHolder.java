package com.company.classes.Gamse.Kreps;

class stageHolder {
    private KrepsV2.stages currentStage = KrepsV2.stages.zero;

    private int pointer;

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public int getPointer() {
        return pointer;
    }

    public void setCurrentStage(KrepsV2.stages stages){
        this.currentStage = stages;
    }
    public KrepsV2.stages getCurrentStage() {
        return currentStage;
    }
}
