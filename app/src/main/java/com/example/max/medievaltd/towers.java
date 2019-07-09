package com.example.max.medievaltd;


import android.graphics.Canvas;

import java.util.ArrayList;

public class towers extends GameObject{


    private int matrixScale;
    private int type;
    private int level;



    private long lastScan;
    public towers(towers tow)
    {

        matrixScale=tow.getMatrixScale();
        type=tow.getType();
        level=tow.getLevel();
        height = tow.getHeight();
        width = tow.getWidth();
        x = tow.getX();
        y=tow.getY();

    }


    public towers(int w,int h, int numFrames,int posX,int posY,int mS,int t,int lvl)
    {
        type=t;
        level=lvl;
        height = h;
        width = w;
        matrixScale=mS;
        x = posX;
        y=posY;
        lastScan=Integer.MAX_VALUE;



                /*Bitmap[]image = new Bitmap[numFrames];
        for (int i = 0; i < image.length; i++) {
            image[i]= Bitmap.createBitmap(spritesheet, i*width,  0, width, height);

        }
        animation.setFrames(image);
        animation.setFrame(0);
        animation.setDelay(80);*/

    }

    public void unPause(){lastScan=System.nanoTime();}
    public int getMatrixScale(){return  matrixScale;}
    public int getType(){return type;}
    public void setWidth(int w){width=w;}
    public int getLevel(){return level;}

    public CyrusMonster readyToFire(ArrayList<CyrusMonster> monsterArrayList)
    {
        int dangerest=9000,pos=-1;
        int frequency=-1, towerRange=-1;
        switch(type)
        {
            case 0:
                if(level==1) {frequency=300; towerRange=2;}
                if(level==2) {frequency=400; towerRange=2;}
                if(level==3) {frequency=500; towerRange=3;}
                break;
            case 1:
                if(level==1) {frequency=300; towerRange=2;}
                if(level==2) {frequency=600; towerRange=2;}
                if(level==3) {frequency=900; towerRange=2;}
                break;
            case 2:
                if(level==1) {frequency=50; towerRange=2;}
                if(level==2) {frequency=50; towerRange=3;}
                if(level==3) {frequency=50; towerRange=4;}
                break;
        }
        if((System.nanoTime()-lastScan)/1000000>1000-frequency) {

            for (int i = 0; i < monsterArrayList.size();i++)
            {
                if(!monsterArrayList.get(i).getPrimero())
                    if (Math.abs(monsterArrayList.get(i).getActual().getX()-x)<=towerRange&&Math.abs(monsterArrayList.get(i).getActual().getY()-y)<=towerRange)
                    {
                        if(dangerest>monsterArrayList.get(i).getObjectiveList().size()) {
                            dangerest = monsterArrayList.get(i).getObjectiveList().size();
                            pos=i;
                        }
                    }

            }
            if(pos!=-1) {

                return monsterArrayList.get(pos);
            }
        }
        return null;
    }

    public bullets fireBullet(CyrusMonster target)
    {
        int attack=-1,bltSpd=-1,effect=-1;
        int w=-1,h=-1,numFrames=-1;
        if(type==0)
        {
            if(level==1){ attack=2; bltSpd=30; effect=0; w=27; h=25; numFrames=4;}
            if(level==2){ attack=8; bltSpd=30; effect=0; w=27; h=25; numFrames=4;}
            if(level==3){ attack=16; bltSpd=40; effect=0; w=50; h=49; numFrames=8;}
        }
        if(type==1)
        {
            if(level==1){ attack=0; bltSpd=30; effect=11; w=27; h=25; numFrames=4;}
            if(level==2){ attack=2; bltSpd=30; effect=12; w=27; h=25; numFrames=4;}
            if(level==3){ attack=2; bltSpd=40; effect=13; w=50; h=49; numFrames=8;}
        }
        if(type==2)
        {
            w=40; h=41; numFrames=4;
            if(level==1){ attack=2; bltSpd=30; effect=21;}
            if(level==2){ attack=3; bltSpd=30; effect=22;}
            if(level==3){ attack=4; bltSpd=30; effect=23;}
        }
                lastScan=System.nanoTime();
bltSpd=15;

                return new bullets(w,h,numFrames,(int)(x*matrixScale+matrixScale*2.2f),(int)(y*matrixScale+matrixScale*1f),matrixScale,type*10+level,attack,bltSpd,effect,target);
    }
    public boolean raiseLevel()
    {
        if(level<3)
        {
            level++;
            return true;
        }return false;
    }
    public void update()
    {

    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(GamePanel.towerBitmaps[type][level-1], transposeX(x,matrixScale), transposeY(y, matrixScale), null);

    }
}
