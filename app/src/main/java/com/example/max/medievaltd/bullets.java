package com.example.max.medievaltd;




import android.graphics.Canvas;

public class bullets extends GameObject{


    private int matrixScale;
    private int attack;
    private int speed;
    private Animation animation=new Animation();
    private CyrusMonster target;
    private int effect;
    private boolean hit;
    private  int distance;
    private int type;
    private long lifetime;
    private boolean launch;


    public bullets(int w,int h, int numFrames,int posX,int posY,int mS,int t,int atk,int spd,int fx,CyrusMonster tgt)
    {
        attack=atk;
        speed=spd;
        target=tgt;
        effect=fx;
        hit=false;
        height = h;
        width = w;
        matrixScale=mS;
        x = posX;
        y=posY;
        distance=(int)Math.abs(target.getX()+matrixScale*1.6-x);
        launch=true;
        type=t;
        lifetime=System.nanoTime();


            if(type==3||type==13)
                animation.setFrames(8);
            else
            animation.setFrames(4);
            animation.setDelay(30);






    }
    public boolean getHit(){return hit;}
    public int getEffect(){return effect;}
    public int getAttack(){return attack;}
    public CyrusMonster getTarget() { return target;}
    public int getMatrixScale(){return  matrixScale;}
    public long getLifetime(){return  lifetime;}
    public void setWidth(int w){width=w;}
    public void affectTarget()
    {
        if(effect==11) target.makeSlow(1);
        if(effect==12) target.makeSlow(2);
        if(effect==13) target.makeSlow(3);
        target.giveDamage(attack);

    }
    public Animation getAnimation(){return animation;}

    public void update()
    {


        if(!hit) {
           // System.out.println("x: "+x+" y: "+y+" tX: "+target.getX()+" tY: "+target.getY());
            int bullspeed=speed;
            double tx=target.getX()+matrixScale*1.6,ty=target.getY()+matrixScale*1.4;
            short ax=0,ay=0;
            boolean hitx=false,hity=false;

                if (x <tx) {
                    ax=1;
                    if (x + bullspeed > tx) {
                        x = (int) (tx);
                    }
                    else
                        x += bullspeed;
                }
                if (x > tx) {
                    ax=-1;
                    if (x - bullspeed < tx)
                        x = (int) (tx);
                    else
                        x -= bullspeed;
                }

            if(x == (int) (tx)) {
                hitx = true;
            }
            if(launch&&x!=tx) {
                //if((System.nanoTime()-launchTime)/1000000>100)
                //  launch=false;
                    if (y > ty - matrixScale||distance/2>(int)Math.abs(tx-x)) {
                        ay = -1;
                        y -= bullspeed;
                    } else launch = false;
            }
            else {
                if (y < ty) {
                    ay = 1;
                    if (y + bullspeed > ty)
                        y = (int) (ty);
                    else
                        y += bullspeed;
                }
                if (y > ty) {
                    ay = -1;
                    if (y - bullspeed < ty)
                        y = (int) (ty);
                    else
                        y -= bullspeed;
                }
                if (y == (int) (ty)) {
                    hity = true;

                }
            }
            if (hitx&&hity) {
                hit = true;


            }
            if(type!=3&&type!=13)
            animation.update();
            else
            {
                if(!(hitx&&hity)){
                    int frame = 0;
                    if (ax == -1) {
                        if (ay == -1) frame = 1;
                        if (ay == 0) frame = 0;
                        if (ay == 1) frame = 7;
                    }
                    if (ax == 0) {
                        if (ay == -1) frame = 2;
                        if (ay == 1) frame = 6;
                    }
                    if (ax == 1) {
                        if (ay == -1) frame = 3;
                        if (ay == 0) frame = 4;
                        if (ay == 1) frame = 5;
                    }

                    animation.setFrame(frame);
                }

            }
        }


    }
    public void draw(Canvas canvas)
    {
        int tipo=0;
        switch(type)
        {
            case 1:tipo=0;break;
            case 2:tipo=0;break;
            case 3:tipo=1;break;
            case 11:tipo=2;break;
            case 12:tipo=2;break;
            case 13:tipo=3;break;
            case 21:tipo=4;break;
            case 22:tipo=4;break;
            case 23:tipo=4;break;

        }

        canvas.drawBitmap(GamePanel.balasBitmap[tipo][animation.getFrame()], x+matrixScale*0.4f, y, null);

            //canvas.drawBitmap(spritesheet, x+matrixScale*0.4f, y, null);


    }
}
