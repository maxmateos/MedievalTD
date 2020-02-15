package com.example.max.medievaltd.levelups.stats;

public class BulletStats extends DrawableObjectStats{

    private int attack;
    private int speed;
    private int effect;

    public BulletStats(int attack, int speed, int effect, SpriteStats spriteStats) {
        this.attack = attack;
        this.speed = speed;
        this.effect = effect;
        this.spriteStats = spriteStats;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }
}