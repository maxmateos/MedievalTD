package com.example.max.medievaltd.core;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.max.medievaltd.game.GamePanel;
import com.example.max.medievaltd.time.Conversions;

import static com.example.max.medievaltd.time.Conversions.MILLIS_TO_NANOS_FACTOR;
import static com.example.max.medievaltd.time.Conversions.SECONDS_TO_MILLIS_FACTOR;
import static com.example.max.medievaltd.time.Conversions.convertNanosToMillis;
import static com.example.max.medievaltd.time.Conversions.millisElapsedSince;

/**
 * Created by max on 4/09/2015.
 */
public class MainThread extends Thread {

    private final SurfaceHolder surfaceHolder;
    private final GamePanel gamePanel;

    private Canvas canvas;
    private boolean running;

    private static final int FPS = 30;
    private static final long TARGET_TIME_MILLIS = SECONDS_TO_MILLIS_FACTOR / FPS;

    private long iterationStartTime;
    private long totalMillisWaited;
    private int frameCount;

    private double averageFPS;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {

        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
        this.totalMillisWaited = 0;
        this.frameCount = 0;
    }

    @Override
    public void run() {

        while (running) {
            iterationStartTime = System.nanoTime();
            updateAndDrawCanvas();
            adjustFrameRate();
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public static int getFPS() {
        return FPS;
    }

    private void updateAndDrawCanvas() {

        canvas = null;

        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                gamePanel.update();
                gamePanel.draw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }


    }
    private void adjustFrameRate() {

        try {
            sleep(calculateMillisToWait());
        } catch (Exception e) {
            e.printStackTrace();
        }

        totalMillisWaited += millisElapsedSince(iterationStartTime);
        frameCount++;

        if (frameCount == FPS) {
            averageFPS = (totalMillisWaited / frameCount) / SECONDS_TO_MILLIS_FACTOR;
            frameCount = 0;
            totalMillisWaited = 0;

        }
    }

    private long calculateMillisToWait() {

        final long iterationTimeMillis = millisElapsedSince(iterationStartTime);
        return TARGET_TIME_MILLIS - iterationTimeMillis;
    }


}
