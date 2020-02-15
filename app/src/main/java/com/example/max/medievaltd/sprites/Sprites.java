package com.example.max.medievaltd.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.example.max.medievaltd.exceptions.InvalidOptionException;
import com.example.max.medievaltd.levelups.stats.SpriteStats;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Bitmap.createBitmap;
import static android.graphics.BitmapFactory.decodeResource;

public abstract class Sprites {

    protected Resources resources;

    protected List<Bitmap> createSprites(SpriteStats spriteStats) {

        int x, y = 0;
        final Bitmap sprites = decodeResource(resources, spriteStats.getResourceId());
        final List<Bitmap> spriteList = new ArrayList<>();

        for (int i = 0; i < spriteStats.getFrames(); i++) {
            x = i * spriteStats.getWidth();
            spriteList.add(createBitmap(sprites, x, y, spriteStats.getWidth(), spriteStats.getHeight()));
        }

        return spriteList;
    }

    protected abstract InvalidOptionException throwInvalidOptionException(int level, int frame);
}
