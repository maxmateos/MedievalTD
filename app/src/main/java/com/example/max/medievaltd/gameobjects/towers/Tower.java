package com.example.max.medievaltd.gameobjects.towers;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.max.medievaltd.animation.Animation;
import com.example.max.medievaltd.gameobjects.bullets.Bullet;
import com.example.max.medievaltd.gameobjects.monsters.CyrusMonster;
import com.example.max.medievaltd.gameobjects.GameObject;
import com.example.max.medievaltd.levelups.stats.SpriteStats;
import com.example.max.medievaltd.levelups.stats.TowerStats;
import com.example.max.medievaltd.sprites.tower.TowerSprites;
import com.example.max.medievaltd.utils.Coordinates;

import java.util.ArrayList;

import static com.example.max.medievaltd.time.Conversions.SECONDS_TO_MILLIS_FACTOR;
import static com.example.max.medievaltd.time.Conversions.millisElapsedSince;

public abstract class Tower extends GameObject {

    protected int level;
    private int cooldownMillis;
    private int range;

    protected long lastFired;

    private static final int FRAME_DELAY = 30;

    public Tower(GameObject gameObject) {

        super(gameObject);
        level = 0;
        lastFired = Integer.MAX_VALUE;
        setStats(getStats());
    }

    private void setStats(TowerStats towerStats) {

        this.range = towerStats.getTowerRange();
        this.cooldownMillis = SECONDS_TO_MILLIS_FACTOR - towerStats.getFrequency();

        final SpriteStats spriteStats = towerStats.getSpriteStats();
        this.width = spriteStats.getWidth();
        this.height = spriteStats.getHeight();
        setAnimationFromFrames(spriteStats.getFrames());
    }

    private void setAnimationFromFrames(int frames) {

        animation = new Animation();
        animation.setFrames(frames);
        animation.setDelay(FRAME_DELAY);
    }

    public void unPause() {
        lastFired = System.nanoTime();
    }

    public int getMatrixScale() {
        return matrixScale;
    }

    public void setWidth(int w) {
        width = w;
    }

    public int getLevel() {
        return level;
    }

    public CyrusMonster scanForTarget(ArrayList<CyrusMonster> monsterArrayList) {

        CyrusMonster objective = null;

        if (isReadyToFire()) {
            int closestDistanceToBase = 9000;
            for (CyrusMonster monster : monsterArrayList) {
                if (!monster.isBeforeFirstMapSquare() && isMonsterInRange(monster)) {
                    if (closestDistanceToBase > monster.getObjectiveList().size()) {
                        closestDistanceToBase = monster.getObjectiveList().size();
                        objective = monster;
                    }
                }

            }
        }

        return objective;
    }

    private boolean isReadyToFire() {
        return millisElapsedSince(lastFired) > cooldownMillis;
    }

    private boolean isMonsterInRange(CyrusMonster monster) {

        final Coordinates coords = monster.getActual();
        return Math.abs(coords.getX() - x) <= range && Math.abs(coords.getY() - y) <= range;
    }

    public abstract Bullet fireBullet(CyrusMonster target);
    protected void resetFireCooldown() {
        lastFired = System.nanoTime();
    }

    public boolean raiseLevel() {

        if (level < 3) {
            level++;
            lastFired = Integer.MAX_VALUE;
            setStats(getStats());
            return true;
        }
        return false;
    }

    protected void drawBitmap(Canvas canvas, Bitmap sprite) {
        canvas.drawBitmap(sprite, transposeX(x, matrixScale), transposeY(y, matrixScale), null);
    }

    public abstract Class getType();
    public abstract TowerStats getStats();
    public abstract void update();
    public abstract void draw(Canvas canvas, TowerSprites towerSprites);
}
