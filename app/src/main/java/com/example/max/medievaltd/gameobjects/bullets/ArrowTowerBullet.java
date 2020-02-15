package com.example.max.medievaltd.gameobjects.bullets;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.max.medievaltd.levelups.levelmanagers.bullets.ArrowTowerBulletLevelManager;
import com.example.max.medievaltd.gameobjects.monsters.CyrusMonster;
import com.example.max.medievaltd.gameobjects.towers.Tower;
import com.example.max.medievaltd.levelups.stats.BulletStats;
import com.example.max.medievaltd.sprites.bullet.BulletSprites;

public class ArrowTowerBullet extends Bullet {

    public ArrowTowerBullet(Tower parentTower, CyrusMonster target) {
        super(parentTower, target);
    }

    @Override
    public void update() {
        updateBulletTrajectory();
        if (level > 1) {
            selectArrowFrameForDirection();
        } else {
            animation.update();
        }
    }

    @Override
    public void draw(Canvas canvas, BulletSprites bulletSprites) {
        final Bitmap bitmap = bulletSprites.getArrowTowerBulletSprite(level, animation);
        drawBitmap(canvas, bitmap);
    }

    @Override
    public BulletStats getStats(Tower parentTower) {
        return ArrowTowerBulletLevelManager.getStats(parentTower.getLevel());
    }
}
