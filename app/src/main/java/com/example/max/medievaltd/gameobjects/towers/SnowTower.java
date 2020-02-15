package com.example.max.medievaltd.gameobjects.towers;

import android.graphics.Canvas;

import com.example.max.medievaltd.gameobjects.GameObject;
import com.example.max.medievaltd.gameobjects.bullets.Bullet;
import com.example.max.medievaltd.gameobjects.bullets.SnowTowerBullet;
import com.example.max.medievaltd.gameobjects.monsters.CyrusMonster;
import com.example.max.medievaltd.levelups.levelmanagers.towers.SnowTowerLevelManager;
import com.example.max.medievaltd.levelups.stats.TowerStats;
import com.example.max.medievaltd.sprites.tower.TowerSprites;

public class SnowTower extends Tower {

    public SnowTower(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public Bullet fireBullet(CyrusMonster target) {

        resetFireCooldown();
        return new SnowTowerBullet(this, target);
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
        drawBitmap(canvas, towerSprites.getSnowTowerSprite(level, animation));
    }

    @Override
    public TowerStats getStats() {
        return SnowTowerLevelManager.getStats(level);
    }

}
