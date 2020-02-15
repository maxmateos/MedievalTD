package com.example.max.medievaltd.levelups.levelmanagers.towers;

import com.example.max.medievaltd.R;
import com.example.max.medievaltd.exceptions.InvalidOptionException;
import com.example.max.medievaltd.levelups.stats.SpriteStats;
import com.example.max.medievaltd.levelups.stats.TowerStats;

import java.util.ArrayList;
import java.util.List;

public class CannonTowerLevelManager {

    private static final int FREQUENCY_LEVEL_1 = 50;
    private static final int RANGE_LEVEL_1 = 2;
    private static final int WIDTH_LEVEL_1 = 100;
    private static final int HEIGHT_LEVEL_1 = 107;
    private static final int FRAMES_LEVEL_1 = 1;
    private static final int RESOURCE_ID_LEVEL_1 = R.drawable.canonlvl1;

    private static final int FREQUENCY_LEVEL_2 = 50;
    private static final int RANGE_LEVEL_2 = 3;
    private static final int WIDTH_LEVEL_2 = 100;
    private static final int HEIGHT_LEVEL_2 = 107;
    private static final int FRAMES_LEVEL_2 = 1;
    private static final int RESOURCE_ID_LEVEL_2 = R.drawable.canonlvl2;

    private static final int FREQUENCY_LEVEL_3 = 50;
    private static final int RANGE_LEVEL_3 = 4;
    private static final int WIDTH_LEVEL_3 = 100;
    private static final int HEIGHT_LEVEL_3 = 107;
    private static final int FRAMES_LEVEL_3 = 1;
    private static final int RESOURCE_ID_LEVEL_3 = R.drawable.canonlvl3;

    private static final List<TowerStats> levels = new ArrayList<>();
    private static final String INVALID_OPTION_EXCEPTION_MESSAGE = "Invalid option for level: ";

    static {

        final SpriteStats spriteStats1 = new SpriteStats(WIDTH_LEVEL_1, HEIGHT_LEVEL_1, FRAMES_LEVEL_1, RESOURCE_ID_LEVEL_1);
        final TowerStats towerStats1 = new TowerStats(FREQUENCY_LEVEL_1, RANGE_LEVEL_1, spriteStats1);
        levels.add(towerStats1);

        final SpriteStats spriteStats2 = new SpriteStats(WIDTH_LEVEL_2, HEIGHT_LEVEL_2, FRAMES_LEVEL_2, RESOURCE_ID_LEVEL_2);
        final TowerStats towerStats2 = new TowerStats(FREQUENCY_LEVEL_2, RANGE_LEVEL_2, spriteStats2);
        levels.add(towerStats2);

        final SpriteStats spriteStats3 = new SpriteStats(WIDTH_LEVEL_3, HEIGHT_LEVEL_3, FRAMES_LEVEL_3, RESOURCE_ID_LEVEL_3);
        final TowerStats towerStats3 = new TowerStats(FREQUENCY_LEVEL_3, RANGE_LEVEL_3, spriteStats3);
        levels.add(towerStats3);
    }

    public static TowerStats getStats(int level) {
        if (level < levels.size()) {
            return levels.get(level);
        }
        throw new InvalidOptionException(INVALID_OPTION_EXCEPTION_MESSAGE + level);
    }
}
