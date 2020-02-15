package com.example.max.medievaltd.gameobjects.bullets;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.max.medievaltd.levelups.levelmanagers.bullets.ArrowTowerBulletLevelManager;
import com.example.max.medievaltd.levelups.levelmanagers.bullets.CannonTowerBulletLevelManager;
import com.example.max.medievaltd.gameobjects.monsters.CyrusMonster;
import com.example.max.medievaltd.gameobjects.towers.Tower;
import com.example.max.medievaltd.levelups.stats.BulletStats;
import com.example.max.medievaltd.sprites.bullet.BulletSprites;

public class CannonTowerBullet extends Bullet {

    public CannonTowerBullet(Tower parentTower, CyrusMonster target) {
        super(parentTower, target);
    }

    @Override
    public void update() {
        updateBulletTrajectory();
        animation.update();
    }

    @Override
    public void draw(Canvas canvas, BulletSprites bulletSprites) {
        final Bitmap bitmap = bulletSprites.getCannonTowerBulletSprite(level, animation);
        drawBitmap(canvas, bitmap);
    }

    @Override
    public BulletStats getStats(Tower parentTower) {
        return CannonTowerBulletLevelManager.getStats(parentTower.getLevel());
    }
}
