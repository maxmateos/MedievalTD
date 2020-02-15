package com.example.max.medievaltd.gameobjects.bullets;




import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.max.medievaltd.animation.Animation;
import com.example.max.medievaltd.gameobjects.monsters.CyrusMonster;
import com.example.max.medievaltd.gameobjects.GameObject;
import com.example.max.medievaltd.gameobjects.towers.Tower;
import com.example.max.medievaltd.levelups.stats.BulletStats;
import com.example.max.medievaltd.levelups.stats.SpriteStats;
import com.example.max.medievaltd.levelups.stats.TowerStats;
import com.example.max.medievaltd.sprites.bullet.BulletSprites;

public abstract class Bullet extends GameObject {

    protected int level;
    private int attack;
    private int speed;
    private int effect;
    private CyrusMonster target;

    private boolean hit;
    private int distance;
    private long lifetime;
    private boolean launch;

    private static final int FRAME_DELAY = 30;

    public Bullet(Tower parentTower, CyrusMonster target) {

        this.matrixScale = parentTower.getMatrixScale();
        this.level = parentTower.getLevel();
        setStats(getStats(parentTower));

        this.x = transposeX(parentTower.getX(), matrixScale) + parentTower.getWidth() / 2 - width / 2;
        this.y = transposeY(parentTower.getY(), matrixScale) + parentTower.getHeight() / 2 - height / 2;
        this.target = target;
        this.hit = false;
        this.distance = (int) Math.abs(target.getX() + matrixScale * 1.6 - x);
        this.launch = true;
        this.lifetime = System.nanoTime();

    }
    public boolean getHit(){return hit;}
    public int getEffect(){return effect;}
    public int getAttack(){return attack;}
    public CyrusMonster getTarget() { return target;}
    public long getLifetime(){return  lifetime;}
    public void setWidth(int w){width=w;}
    public void affectTarget() {
        if(effect==11) target.makeSlow(1);
        if(effect==12) target.makeSlow(2);
        if(effect==13) target.makeSlow(3);
        target.giveDamage(attack);

    }

    private void setStats(BulletStats bulletStats) {

        this.attack = bulletStats.getAttack();
        this.speed = 15; //bulletStats.getSpeed();
        this.effect = bulletStats.getEffect();

        final SpriteStats spriteStats = bulletStats.getSpriteStats();
        this.width = spriteStats.getWidth();
        this.height = spriteStats.getHeight();
        setAnimationFromFrames(spriteStats.getFrames());
    }

    private void setAnimationFromFrames(int frames) {
        animation = new Animation();
        animation.setFrames(frames);
        animation.setDelay(FRAME_DELAY);
    }

    public Animation getAnimation(){return animation;}

    public abstract void update();
    public abstract void draw(Canvas canvas, BulletSprites bulletSprites);

    protected void updateBulletTrajectory() {

        final int tx = target.getX() - width / 2 + target.getWidth() / 2;
        final int ty = target.getY() - height / 2 + target.getHeight() / 2;

        if (x < tx) {
            x = (x + speed > tx) ? tx : x + speed;
        } else if (x > tx) {
            x = (x - speed < tx) ? tx : x - speed;
        }


        if (y < ty) {
            y = (y + speed > ty) ? ty : y + speed;
        } else if  (y > ty) {
            y = (y - speed < ty) ? ty : y - speed;
        }


        if (x == tx && y == ty  && false) {
            hit = true;
        }
    }

    protected void selectArrowFrameForDirection() {

        short ax=0,ay=0;
        double tx=target.getX()+matrixScale*1.6,ty=target.getY()+matrixScale*1.4;

        if (x <tx) {
            ax=1;

        }
        if (x > tx) {
            ax=-1;

        }

        if(launch&&x!=tx) {
            if (y > ty - matrixScale||distance/2>(int)Math.abs(tx-x)) {
                ay = -1;
            } else launch = false;
        }
        else {
            if (y < ty) {
                ay = 1;
            }
            if (y > ty) {
                ay = -1;
            }
        }

        if (!hit) {
            int frame = 0;
            if (ax == -1) {
                if (ay == -1) frame = 1;
                if (ay == 0) frame = 0;
                if (ay == 1) frame = 7;
            }
            if (ax == 0) {
                if (ay == -1) frame = 2;
                if (ay == 1) frame = 6;
            }
            if (ax == 1) {
                if (ay == -1) frame = 3;
                if (ay == 0) frame = 4;
                if (ay == 1) frame = 5;
            }

            animation.setFrame(frame);
        }
    }

    protected void drawBitmap(Canvas canvas, Bitmap sprite) {
        canvas.drawBitmap(sprite, x, y, null);
    }

    public abstract BulletStats getStats(Tower parentTower);

}
