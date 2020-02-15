package com.example.max.medievaltd.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.max.medievaltd.game.GamePanel;

/**
 * Created by max on 4/09/2015.
 */
public class Background {

    private Bitmap image;
    private Bitmap sign;

    private int x, y, dx;

    public Background(Bitmap res) {

        image = res;
        dx = GamePanel.MOVESPEED;
    }

    public void update() {

        x += dx;
        if (x < -GamePanel.WIDTH) {
            x = 0;
        }
    }

    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, x, y, null);
        if (x < 0) {
            canvas.drawBitmap(image, x + GamePanel.WIDTH, y, null);
        }

    }
    public void drawSign(Canvas canvas) {

        canvas.drawBitmap(sign, GamePanel.WIDTH / 2 - sign.getWidth() / 2, GamePanel.HEIGHT - sign.getHeight() + 5, null);
    }

    public void setSignImage(Bitmap im) {
        sign = im;
    }

    public void drawTitle(Canvas canvas) {

        final Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(150);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("MEDIEVAL", GamePanel.WIDTH / 2 - 350, 170, paint);
        paint.setTextSize(50);
        canvas.drawText("TOWER DEFENSE", GamePanel.WIDTH / 2 - 481, 210, paint);
    }
}


