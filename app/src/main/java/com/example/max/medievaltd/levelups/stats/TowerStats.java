package com.example.max.medievaltd.levelups.stats;

public class TowerStats extends DrawableObjectStats {

    private int frequency;
    private int towerRange;

    public TowerStats(int frequency, int towerRange, SpriteStats spriteStats) {
        this.frequency = frequency;
        this.towerRange = towerRange;
        this.spriteStats = spriteStats;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getTowerRange() {
        return towerRange;
    }

    public void setTowerRange(int towerRange) {
        this.towerRange = towerRange;
    }
}