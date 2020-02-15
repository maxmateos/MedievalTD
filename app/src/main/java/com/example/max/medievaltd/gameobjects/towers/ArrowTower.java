package com.example.max.medievaltd.gameobjects.towers;

import android.graphics.Canvas;

import com.example.max.medievaltd.gameobjects.GameObject;
import com.example.max.medievaltd.gameobjects.bullets.ArrowTowerBullet;
import com.example.max.medievaltd.gameobjects.bullets.Bullet;
import com.example.max.medievaltd.gameobjects.monsters.CyrusMonster;
import com.example.max.medievaltd.levelups.levelmanagers.towers.ArrowTowerLevelManager;
import com.example.max.medievaltd.levelups.stats.TowerStats;
import com.example.max.medievaltd.sprites.tower.TowerSprites;

public class ArrowTower extends Tower {

    public ArrowTower(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public Bullet fireBullet(CyrusMonster target) {

        resetFireCooldown();
        return new ArrowTowerBullet(this, target);
    }

    @Override
    public Class getType() {
        return this.getClass();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas, TowerSprites towerSprites) {
        drawBitmap(canvas, towerSprites.getArrowTowerSprite(level, animation));
    }

    @Override
    public TowerStats getStats() {
        return ArrowTowerLevelManager.getStats(level);
    }

}
