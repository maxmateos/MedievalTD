package com.example.max.medievaltd.ui.interactive;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameMenuSign {

    private Bitmap sign;

    public GameMenuSign(Bitmap res) {
        sign = res;
    }

    public void update() {
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(sign, -5, -3, null);
    }

}
