package com.example.max.medievaltd;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by max on 10/09/2015.
 */
public class CyrusMonster extends GameObject {

    private ArrayList<coordinates> objectiveList;
    private AnimateCharacter animation = new AnimateCharacter();
    private coordinates actual;
    private int speed;
    private int slow;
    private long slowTime;
    private int hitpoints;
    private int maxHitpoints;
    private int matrixScale;
    private boolean change;
    private boolean primero;
    private int type;
    public CyrusMonster(CyrusMonster mon)
    {
        objectiveList=new ArrayList<>();
        for(int i=0;i<mon.objectiveList.size();i++) {
            objectiveList.add(new coordinates(mon.getObjectiveList().get(i).getX(), mon.getObjectiveList().get(i).getY()));
        }
        animation=mon.getAnimation();
        actual=new coordinates(mon.getActual().getX(),mon.getActual().getY());
        speed=mon.getSpeed();
        slow=mon.getSlow();
        slowTime=mon.getSlowTime();
        type=mon.getType();
        hitpoints=mon.getHitpoints();
        maxHitpoints=mon.getMaxHitpoints();
        matrixScale=mon.getMatrixScale();
        change=mon.getChange();
        primero=mon.getPrimero();
        x=mon.getX();
        y=mon.getY();
        dx=mon.getDx();
        dy=mon.getDy();
        height=mon.getHeight();
        width=mon.getWidth();
    }
    //public Bitmap getSpritesheet(){return spritesheet;}
    public AnimateCharacter getAnimation(){return animation;}
    public int getSpeed(){return speed;}
    public int getSlow(){return slow;}
    public long getSlowTime(){return slowTime;}
    public int getType(){return type;}
    public int getHitpoints(){return hitpoints;}
    public int getMaxHitpoints(){return maxHitpoints;}
    public int getMatrixScale(){return  matrixScale;}
    public boolean getChange(){return change;}
    public boolean getPrimero(){return primero;}
    public int getFrame(){return animation.getFrame();}
    public int getCurrentAction(){return animation.getCurrentAction();}
    public void unPause(){slowTime=System.nanoTime();}
    public CyrusMonster(int tp, int w, int h, int numFrames,int numActions,ArrayList<coordinates> oL,int hp,int mS) {
        //0 ajnabi
        //1 ogre
        //2 boar
        //3 sauron
        type=tp;
        speed=5;
        if(type==1) {
            hp *= 2;
            speed=2;

        }
        if(type==2)
        {
            hp/=2;
            speed*=2;
        }
        if(type==3)
        {
            speed=1;
            hp*=20;

        }
        dy = 0;
        height = h;
        width = w;
        objectiveList=new ArrayList<coordinates>();
        for(int i=0;i<oL.size();i++)
            objectiveList.add(new coordinates(oL.get(i).getX(),oL.get(i).getY()));

        hitpoints=hp;
        maxHitpoints=hp;

        slow=1;

        change=false;
        slowTime=0;
        matrixScale=mS;
        actual=objectiveList.get(0);
        objectiveList.remove(0);
        x = -3*matrixScale;
        y = matrixScale*actual.getY();
        primero=true;


        animation.setFrames(numFrames);
        animation.setFrame(0);
        animation.setCurrentAction(0);
        animation.setDelay(120-speed*10);


    }
    public void giveDamage(int damage)
    {
        hitpoints-=damage;
    }
    public void makeSlow(int magnitude)
    {
        slowTime=System.nanoTime();
        slow=magnitude+1;
    }


    public void update() {


        animation.update();

        if(slow!=1&&(System.nanoTime()-slowTime)/1000000>1000)
        {
            slow--;
            slowTime=System.nanoTime();
        }
       /* if(x<GamePanel.WIDTH/4) {
            left = false;
            animation.setCurrentAction(3);
        }
        if(x>GamePanel.WIDTH*3/4) {
            left = true;
            animation.setCurrentAction(1);
        }
        if(y<GamePanel.HEIGHT/4) {
            up = true;

        }
        if(y>GamePanel.HEIGHT*3/4) {
            up = false;

        }*/
        if(x>=actual.getX()*matrixScale+matrixScale-30)
            primero=false;
        int finalspeed=speed/slow;
if(!noObjList()&&!primero) {
    int obx=objectiveList.get(0).getX()*matrixScale+matrixScale-30,oby=objectiveList.get(0).getY()*matrixScale;
    dx=0;
    dy=0;

    if(finalspeed==0)
        finalspeed++;
    if (x <obx ) {
        if (x + finalspeed > obx) {
            dx = 0;
        }
        else
            dx = finalspeed;
    }
    if(x>obx) {
        if (x - finalspeed < obx)
            dx = 0;
        else
            dx = -finalspeed;
    }
    if (y < oby) {
        if (y + finalspeed > oby)
            dy = 0;
        else
            dy = finalspeed;
    }
    if (y > oby) {
        if (y - finalspeed < oby)
            dy = 0;
        else
            dy = -finalspeed;
    }
    if (y == oby)
        dy = 0;

    if (dx >= 0)
        setRight();
    else
        setLeft();
    if (x ==obx) {
        dx = 0;
        setRight();
    }


    if(dx!=0)
        x += dx;
    else
        x= obx;
    if(dy!=0)
        y += dy;
    else
        y=oby;

    if(!change)
    objectiveReached();
    change=false;
}
        else {
        setRight();
       x = x + finalspeed;
}
    }
    public ArrayList<coordinates> getObjectiveList()
    {
        return objectiveList;
    }
    public void setObjectiveList(ArrayList<coordinates> o)
    {
        coordinates oldObj=null;
        if(objectiveList.size()>0) {
            oldObj = objectiveList.get(0);
        }
        objectiveList=o;
        actual=objectiveList.get(0);
       if(oldObj!=null&&objectiveList.size()>0&&(objectiveList.get(1).getX()==oldObj.getX()&&objectiveList.get(1).getY()==oldObj.getY()))
           objectiveList.remove(0);

        change=true;
       /* if(dx==0)
        x = matrixScale*actual.getX()+matrixScale-10;
        if(dy==0)
        y = matrixScale*actual.getY();*/
    }
    public coordinates getActual()
    {
        return actual;
    }
    public boolean noObjList()
    {
        if(objectiveList.size()==0)
            return true;
        return false;
    }

    public void setLeft()
    {
        animation.setCurrentAction(0);
    }
    public void setRight()
    {
        animation.setCurrentAction(1);
    }
    public void printObjectives()
    {
        for(int i=0;i<objectiveList.size();i++)
        {
            System.out.println("X: "+objectiveList.get(i).getX()+" Y: "+objectiveList.get(i).getY());
        }
    }
    public void objectiveReached()
    {
        boolean equises=false, yes=false;

        if(actual.getX()<objectiveList.get(0).getX())
        {
            // ->
            if(!change&&x>=(objectiveList.get(0).getX()*matrixScale+matrixScale-30))
                equises=true;

        }
        else
        {
            // <-
            if(!change&&x <=objectiveList.get(0).getX()*matrixScale+matrixScale-30)
                equises=true;
        }
        if(actual.getY()<objectiveList.get(0).getY())
        {

            // v
            if(!change&&y>=objectiveList.get(0).getY()*matrixScale)
                yes=true;
        }
        else
        {
            // ^
            if(!change&&y<=objectiveList.get(0).getY()*matrixScale)
                yes=true;
        }
        if(!change&&yes&&equises) {
            actual = objectiveList.get(0);
            objectiveList.remove(0);
        }
        //System.out.println("x: " + x + " y: "+y+" sigX: "+(objectiveList.get(0).getX()*matrixScale+matrixScale-10)+" sigY: "+objectiveList.get(0).getY()*matrixScale);
    }
    public void draw(Canvas canvas) {
        int posx=0,posy=0;

        if(type<2)
        {
            posx=x+(int)(matrixScale*1.7);
            posy=y+(int)(matrixScale*1.25);
        }
        if(type==2)
        {
            posx= x+(int)(matrixScale*1.5);
            posy=y+(int)(matrixScale*1.25);
        }
        if(type==3) {
            posx=x + (int) (matrixScale * 1.6);
            posy=y + (int) (matrixScale * 0.5);
        }
        switch(type) {
            case 0:canvas.drawBitmap(GamePanel.ajnabisprites[animation.getFrame()][animation.getCurrentAction()], posx, posy, null);break;
            case 1:canvas.drawBitmap(GamePanel.orcsprites[animation.getFrame()][animation.getCurrentAction()], posx, posy, null);break;
            case 2:canvas.drawBitmap(GamePanel.boarsprites[animation.getFrame()][animation.getCurrentAction()], posx, posy, null);break;
            case 3:canvas.drawBitmap(GamePanel.sauronsprites[animation.getFrame()][animation.getCurrentAction()], posx, posy, null);break;
        }

    }
    public void drawLifeGauge(Bitmap im,Canvas canvas)
    {
        if(type<2)
            canvas.drawBitmap(im, x + (int) (matrixScale * 1.7), y + (int) (matrixScale * 1.2), null);
        if(type==2)
            canvas.drawBitmap(im, x + (int) (matrixScale * 1.8), y + (int) (matrixScale * 1.2), null);
        if(type==3)
            canvas.drawBitmap(im, x + (int) (matrixScale * 2), y + (int) (matrixScale * 0.4), null);
    }
}
