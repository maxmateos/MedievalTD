package com.example.max.medievaltd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class towerButtons {
    private ArrayList<Bitmap> tButtons;
    private ArrayList<coordinates> coords;
    private Bitmap sign;
    private Bitmap shadow;
    private int selected;
    private int matrixScale;

    public towerButtons(Bitmap res,Bitmap sdw) {
        sign = res;
        shadow=sdw;
        selected=-1;
        coords=new ArrayList<>();
        tButtons=new ArrayList<>();

    }
    public void setSelected(int s){ if(selected!=s) selected=s; else selected=-1;}
    public int getSelected(){return selected;}
    public void add(int x,int y,int mS,Bitmap imagen)
    {
        coords.add(new coordinates(x,y));
        tButtons.add(imagen);
        matrixScale=mS;
    }
    public Rect getRectangle(int pos)
    {
        return new Rect(coords.get(pos).getX()+14,coords.get(pos).getY()+14,coords.get(pos).getX()+140,coords.get(pos).getY()+143);
    }
    public int getSize()
    {
        return tButtons.size();
    }
    public void update()
    {

    }
    public void draw(Canvas canvas)
    {
        for(int i=0;i<tButtons.size();i++)
        {
            if(selected==i)
            canvas.drawBitmap(shadow, coords.get(i).getX(), coords.get(i).getY(), null);
            canvas.drawBitmap(sign, coords.get(i).getX(), coords.get(i).getY(), null);
            canvas.drawBitmap(tButtons.get(i),(int)( coords.get(i).getX()+matrixScale*0.3),(int) (coords.get(i).getY()+matrixScale*0.3),null );
        }
    }

}
