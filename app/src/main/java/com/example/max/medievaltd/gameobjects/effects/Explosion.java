package com.example.max.medievaltd.gameobjects.effects;

import android.graphics.Canvas;

import com.example.max.medievaltd.game.GamePanel;
import com.example.max.medievaltd.animation.Animation;

/**
 * Created by max on 30/08/2015.
 */
public class Explosion {
    private int x;
    private int y;
    private int width;
    private int height;
    private int matrixScale;
    private Animation animation=new Animation();


    public Explosion(int x, int y, int w, int h, int mS, int numFrames)
    {
        this.x=x;
        this.y=y;
        this.width=w;
        this.height=h;
        matrixScale=mS;

        animation.setFrames(numFrames);
        animation.setDelay(10);
    }
    public void draw(Canvas canvas)
    {
        if(!animation.isPlayedOnce())
        {
            canvas.drawBitmap(GamePanel.explosionBitmap[animation.getFrame()], x+(int)(matrixScale*1.7), y+(int)(matrixScale*1.25), null);
        }
    }
    public void update()
    {
        if(!animation.isPlayedOnce())
        {
            animation.update();
        }
    }
    public int getHeight()
    {
        return height;
    }
    public boolean readyToRemove(){return animation.isPlayedOnce();}
}
