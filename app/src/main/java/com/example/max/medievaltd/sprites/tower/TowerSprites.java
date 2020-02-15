package com.example.max.medievaltd.sprites.tower;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.example.max.medievaltd.animation.Animation;
import com.example.max.medievaltd.exceptions.InvalidOptionException;
import com.example.max.medievaltd.levelups.levelmanagers.towers.ArrowTowerLevelManager;
import com.example.max.medievaltd.levelups.levelmanagers.towers.CannonTowerLevelManager;
import com.example.max.medievaltd.levelups.levelmanagers.towers.SnowTowerLevelManager;
import com.example.max.medievaltd.sprites.Sprites;

import java.util.List;

public class TowerSprites extends Sprites {

    private final List<Bitmap> arrowTowerLevel1Bitmaps;
    private final List<Bitmap> arrowTowerLevel2Bitmaps;
    private final List<Bitmap> arrowTowerLevel3Bitmaps;

    private final List<Bitmap> snowTowerLevel1Bitmaps;
    private final List<Bitmap> snowTowerLevel2Bitmaps;
    private final List<Bitmap> snowTowerLevel3Bitmaps;

    private final List<Bitmap> cannonTowerLevel1Bitmaps;
    private final List<Bitmap> cannonTowerLevel2Bitmaps;
    private final List<Bitmap> cannonTowerLevel3Bitmaps;

    private static final String INVALID_OPTION_EXCEPTION_MESSAGE = "Invalid TowerBitmap: level '%s', frame '%s'";

    public TowerSprites(Resources resources) {

        this.resources = resources;

        arrowTowerLevel1Bitmaps = createSprites(ArrowTowerLevelManager.getStats(0).getSpriteStats());
        arrowTowerLevel2Bitmaps = createSprites(ArrowTowerLevelManager.getStats(1).getSpriteStats());
        arrowTowerLevel3Bitmaps = createSprites(ArrowTowerLevelManager.getStats(2).getSpriteStats());

        snowTowerLevel1Bitmaps = createSprites(SnowTowerLevelManager.getStats(0).getSpriteStats());
        snowTowerLevel2Bitmaps = createSprites(SnowTowerLevelManager.getStats(1).getSpriteStats());
        snowTowerLevel3Bitmaps = createSprites(SnowTowerLevelManager.getStats(2).getSpriteStats());

        cannonTowerLevel1Bitmaps = createSprites(CannonTowerLevelManager.getStats(0).getSpriteStats());
        cannonTowerLevel2Bitmaps = createSprites(CannonTowerLevelManager.getStats(1).getSpriteStats());
        cannonTowerLevel3Bitmaps = createSprites(CannonTowerLevelManager.getStats(2).getSpriteStats());

    }

    public Bitmap getArrowTowerSprite(int level, Animation animation) {
        switch (level) {
            case 0: return arrowTowerLevel1Bitmaps.get(animation.getFrame());
            case 1: return arrowTowerLevel2Bitmaps.get(animation.getFrame());
            case 2: return arrowTowerLevel3Bitmaps.get(animation.getFrame());
            default: throw throwInvalidOptionException(level, animation.getFrame());
        }
    }

    public Bitmap getSnowTowerSprite(int level, Animation animation) {
        switch (level) {
            case 0: return snowTowerLevel1Bitmaps.get(animation.getFrame());
            case 1: return snowTowerLevel2Bitmaps.get(animation.getFrame());
            case 2: return snowTowerLevel3Bitmaps.get(animation.getFrame());
            default: throw throwInvalidOptionException(level, animation.getFrame());
        }
    }

    public Bitmap getCannonTowerSprite(int level, Animation animation) {
        switch (level) {
            case 0: return cannonTowerLevel1Bitmaps.get(animation.getFrame());
            case 1: return cannonTowerLevel2Bitmaps.get(animation.getFrame());
            case 2: return cannonTowerLevel3Bitmaps.get(animation.getFrame());
            default: throw throwInvalidOptionException(level, animation.getFrame());
        }
    }

    @Override
    protected InvalidOptionException throwInvalidOptionException(int level, int frame) {
        return new InvalidOptionException(String.format(INVALID_OPTION_EXCEPTION_MESSAGE, level, frame));
    }
}
