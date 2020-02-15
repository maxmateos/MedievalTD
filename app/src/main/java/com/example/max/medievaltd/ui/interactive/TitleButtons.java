package com.example.max.medievaltd.ui.interactive;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;


import com.example.max.medievaltd.game.GamePanel;
import com.example.max.medievaltd.animation.Animation;

/**
 * Created by max on 4/09/2015.
 */
public class TitleButtons {

    private Animation animation=new Animation();
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean playOnce;
    private int type;
    private String text;

    public TitleButtons(int t, int x, int y, int w, int h, String text, int numFrames)
    {
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        this.text=text;
        type=t;

        if(type==3||type==4)
            animation.setFrames(1);
        else
        animation.setFrames(numFrames);
        animation.setDelay(100);
        playOnce=false;


    }

    public void update()
    {
        if(!animation.isPlayedOnce()) {
            animation.update();
        }
    }
    public void draw(Canvas canvas)
    {
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        try
        {
            canvas.drawBitmap(GamePanel.titleButtonBitmap[type][animation.getFrame()], x, y, null);
            canvas.drawText(text, x + 100, y + 100+animation.getFrame()*2, paint);


        }catch(Exception e){}

    }

    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    public boolean getPlayedOnce()
    {
        if(animation.isPlayedOnce()) {
        animation.setPlayedOnce(false);

            return true;
        }
        return false;
    }


}
