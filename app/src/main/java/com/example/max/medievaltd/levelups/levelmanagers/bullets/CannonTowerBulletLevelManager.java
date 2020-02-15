package com.example.max.medievaltd.levelups.levelmanagers.bullets;

import com.example.max.medievaltd.R;
import com.example.max.medievaltd.exceptions.InvalidOptionException;
import com.example.max.medievaltd.levelups.stats.BulletStats;
import com.example.max.medievaltd.levelups.stats.SpriteStats;

import java.util.ArrayList;
import java.util.List;

public class CannonTowerBulletLevelManager {

    private static final int ATTACK_LEVEL_1 = 2;
    private static final int SPEED_LEVEL_1 = 30;
    private static final int EFFECT_LEVEL_1 = 21;
    private static final int WIDTH_LEVEL_1 = 40;
    private static final int HEIGHT_LEVEL_1 = 41;
    private static final int FRAMES_LEVEL_1 = 4;
    private static final int RESOURCE_ID_LEVEL_1 = R.drawable.canonball;

    private static final int ATTACK_LEVEL_2 = 3;
    private static final int SPEED_LEVEL_2 = 30;
    private static final int EFFECT_LEVEL_2 = 22;
    private static final int WIDTH_LEVEL_2 = 40;
    private static final int HEIGHT_LEVEL_2 = 41;
    private static final int FRAMES_LEVEL_2 = 4;
    private static final int RESOURCE_ID_LEVEL_2 = R.drawable.canonball;

    private static final int ATTACK_LEVEL_3 = 4;
    private static final int SPEED_LEVEL_3 = 30;
    private static final int EFFECT_LEVEL_3 = 23;
    private static final int WIDTH_LEVEL_3 = 40;
    private static final int HEIGHT_LEVEL_3 = 41;
    private static final int FRAMES_LEVEL_3 = 4;
    private static final int RESOURCE_ID_LEVEL_3 = R.drawable.canonball;

    private static final List<BulletStats> levels = new ArrayList<>();
    private static final String INVALID_OPTION_EXCEPTION_MESSAGE = "Invalid option for bullet level: ";

    static {

        final SpriteStats spriteStats1 = new SpriteStats(WIDTH_LEVEL_1, HEIGHT_LEVEL_1, FRAMES_LEVEL_1, RESOURCE_ID_LEVEL_1);
        final BulletStats bulletStats1 = new BulletStats(ATTACK_LEVEL_1, SPEED_LEVEL_1, EFFECT_LEVEL_1, spriteStats1);
        levels.add(bulletStats1);

        final SpriteStats spriteStats2 = new SpriteStats(WIDTH_LEVEL_2, HEIGHT_LEVEL_2, FRAMES_LEVEL_2, RESOURCE_ID_LEVEL_2);
        final BulletStats bulletStats2 = new BulletStats(ATTACK_LEVEL_2, SPEED_LEVEL_2, EFFECT_LEVEL_2, spriteStats2);
        levels.add(bulletStats2);

        final SpriteStats spriteStats3 = new SpriteStats(WIDTH_LEVEL_3, HEIGHT_LEVEL_3, FRAMES_LEVEL_3, RESOURCE_ID_LEVEL_3);
        final BulletStats bulletStats3 = new BulletStats(ATTACK_LEVEL_3, SPEED_LEVEL_3, EFFECT_LEVEL_3, spriteStats3);
        levels.add(bulletStats3);
    }

    public static BulletStats getStats(int level) {
        if (level < levels.size()) {
            return levels.get(level);
        }
        throw new InvalidOptionException(INVALID_OPTION_EXCEPTION_MESSAGE + level);
    }
}
