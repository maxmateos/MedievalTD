package com.example.max.medievaltd.animation;

import static com.example.max.medievaltd.time.Conversions.MILLIS_TO_NANOS_FACTOR;
import static com.example.max.medievaltd.time.Conversions.millisElapsedSince;

/**
 * Created by max on 28/08/2015.
 */
public class Animation {
    protected int currentFrame;
    protected long startTime;
    protected long delay;
    protected boolean playedOnce;
    protected int frameLength;

    public void setFrames(int length) {
        frameLength =length;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    public void setDelay(long d) {
        delay = d;
    }

    public void setFrame(int i) {
        currentFrame = i;
    }

    public void update() {

        long elapsed = millisElapsedSince(startTime);
        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == frameLength) {
            currentFrame = 0;
            playedOnce = true;
        }

    }

    public int getFrame()
    {
        return currentFrame;
    }
    public boolean isPlayedOnce()
    {
        return playedOnce;
    }
    public void setPlayedOnce(boolean b)
    {
        playedOnce=b;
    }
    public int getFrameLength(){return frameLength;}

}
