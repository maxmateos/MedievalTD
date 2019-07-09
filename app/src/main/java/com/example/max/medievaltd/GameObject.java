package com.example.max.medievaltd;

import android.graphics.Rect;

/**
 * Created by max on 10/09/2015.
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;
    public void setX(int x)
    {
        this.x=x;
    }
    public void setY(int y)
    {
        this.y=y;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getDx()
    {
        return dx;
    }
    public int getDy()
    {
        return dy;
    }
    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }
    public Rect getRectangle()
    {
        return new Rect(x,y,x+width,y+height);
    }
    public int transposeX(int xi, int matrixScale){return xi*matrixScale+(int)(matrixScale*2.3f);}
    public int transposeY(int yi,int matrixScale){ return (int)(yi*matrixScale+matrixScale);}
}
