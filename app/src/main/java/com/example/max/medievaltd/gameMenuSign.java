package com.example.max.medievaltd;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class gameMenuSign {
    private Bitmap[] towerButtons;
    private Bitmap sign;

    public gameMenuSign(Bitmap res) {
        sign = res;
    }

    public void update()
    {

    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(sign, -5, -3, null);
    }

}
