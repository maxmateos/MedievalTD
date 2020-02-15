package com.example.max.medievaltd.sprites.bullet;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.example.max.medievaltd.animation.Animation;
import com.example.max.medievaltd.exceptions.InvalidOptionException;
import com.example.max.medievaltd.levelups.levelmanagers.bullets.ArrowTowerBulletLevelManager;
import com.example.max.medievaltd.levelups.levelmanagers.bullets.CannonTowerBulletLevelManager;
import com.example.max.medievaltd.levelups.levelmanagers.bullets.SnowTowerBulletLevelManager;
import com.example.max.medievaltd.sprites.Sprites;

import java.util.List;

public class BulletSprites extends Sprites {

    private List<Bitmap> stoneSprites;
    private List<Bitmap> snowSprites;
    private List<Bitmap> cannonBallSprites;
    private List<Bitmap> arrowSprites;
    private List<Bitmap> snowArrowSprites;

    private static final String INVALID_OPTION_EXCEPTION_MESSAGE = "Invalid BulletBitmap: level '%s', frame '%s'";

    public BulletSprites(Resources resources) {

        this.resources = resources;

        stoneSprites = createSprites(ArrowTowerBulletLevelManager.getStats(0).getSpriteStats());
        arrowSprites = createSprites(ArrowTowerBulletLevelManager.getStats(2).getSpriteStats());
        snowSprites = createSprites(SnowTowerBulletLevelManager.getStats(0).getSpriteStats());
        snowArrowSprites = createSprites(SnowTowerBulletLevelManager.getStats(2).getSpriteStats());
        cannonBallSprites = createSprites(CannonTowerBulletLevelManager.getStats(0).getSpriteStats());
    }

    public Bitmap getArrowTowerBulletSprite(int level, Animation animation) {

        switch (level) {
            case 0:
            case 1: return stoneSprites.get(animation.getFrame());
            case 2: return arrowSprites.get(animation.getFrame());
            default: throw throwInvalidOptionException(level, animation.getFrame());
        }
    }

    public Bitmap getSnowArrowTowerBulletSprite(int level, Animation animation) {

        switch (level) {
            case 0:
            case 1: return snowSprites.get(animation.getFrame());
            case 2: return snowArrowSprites.get(animation.getFrame());
            default: throw throwInvalidOptionException(level, animation.getFrame());
        }
    }

    public Bitmap getCannonTowerBulletSprite(int level, Animation animation) {

        switch (level) {
            case 0:
            case 1:
            case 2: return cannonBallSprites.get(animation.getFrame());
            default: throw throwInvalidOptionException(level, animation.getFrame());
        }
    }

    @Override
    protected InvalidOptionException throwInvalidOptionException(int level, int frame) {
        return new InvalidOptionException(String.format(INVALID_OPTION_EXCEPTION_MESSAGE, level, frame));
    }
}
