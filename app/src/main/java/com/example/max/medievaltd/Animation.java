package com.example.max.medievaltd;

import android.graphics.Bitmap;



import android.graphics.Bitmap;

/**
 * Created by max on 28/08/2015.
 */
public class Animation {
    private int currentFrame;
    private int framelength;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(int length) {
        framelength=length;
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
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == framelength)
        {
            currentFrame=0;
            playedOnce=true;
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
    public int getFrameLength(){return framelength;}

}
