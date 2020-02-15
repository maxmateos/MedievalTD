package com.example.max.medievaltd.game;

import android.content.Context;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.max.medievaltd.R;
import com.example.max.medievaltd.exceptions.InvalidOptionException;
import com.example.max.medievaltd.gameobjects.GameObject;
import com.example.max.medievaltd.gameobjects.towers.ArrowTower;
import com.example.max.medievaltd.gameobjects.towers.CannonTower;
import com.example.max.medievaltd.gameobjects.towers.SnowTower;
import com.example.max.medievaltd.sprites.bullet.BulletSprites;
import com.example.max.medievaltd.sprites.tower.TowerSprites;
import com.example.max.medievaltd.core.MainThread;
import com.example.max.medievaltd.game.events.EnemyWaveOrchestrator;
import com.example.max.medievaltd.gameobjects.monsters.CyrusMonster;
import com.example.max.medievaltd.gameobjects.bullets.Bullet;
import com.example.max.medievaltd.gameobjects.effects.Explosion;
import com.example.max.medievaltd.gameobjects.towers.Tower;
import com.example.max.medievaltd.ui.Background;
import com.example.max.medievaltd.ui.interactive.GameMenuSign;
import com.example.max.medievaltd.ui.interactive.TitleButtons;
import com.example.max.medievaltd.ui.interactive.TowerButtons;
import com.example.max.medievaltd.utils.Coordinates;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.max.medievaltd.time.Conversions.millisElapsedSince;
import static com.example.max.medievaltd.time.Conversions.nanosElapsedSince;
import static com.example.max.medievaltd.time.Conversions.secondsElapsedSince;

/**
 * Created by max on 4/09/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Background bg;
    private GameMenuSign gmSign;
    private TowerButtons towBtns;
    public static float WIDTH ; //del bg
    public static float HEIGHT;//del bg
    public static int MOVESPEED; //del bg
    private TitleButtons buttons[];
    private int nBotonActivado;
    private float scaleFactorX;
    private float scaleFactorY;
    private boolean cambiarPantalla;
    private long tiempoDeCambiar=0;
    private char[][] mapa;
    private Resources projectResources;
    public static Bitmap[][] lifeGauges;
    public static Bitmap[][] numbers;
    public static Bitmap[][] towerstats;
    public static Bitmap[][] ajnabisprites;
    public static Bitmap[][] orcsprites;
    public static Bitmap[][] sauronsprites;
    public static Bitmap[][] boarsprites;
    public static TowerSprites towerSprites;
    public static BulletSprites bulletSprites;
    public static Bitmap[] explosionBitmap;
    public static Bitmap[][] titleButtonBitmap;
    public static Bitmap[] cutscenesBitmap;
    Bitmap[] htpBitmap;

    private Bitmap selectCircle;

    private int cutscenesPassed;
    private int[][] storySecuence;

    public int best;
    private int modo;
    private int nuevaPantalla;
    private TitleButtons buttonback[];
    private TitleButtons buttonpropre[];
    private TitleButtons resume;
    private ArrayList<CyrusMonster> monstruo;
    private ArrayList<Tower> torres;
    public ArrayList<Bullet> balas;
    private ArrayList<Explosion> explosiones;
    private ArrayList<Coordinates> rutaNuevos;
    private ArrayList<EnemyWaveOrchestrator> waves;
    private long nextWaveCountDown;
    public static int matrixScale;
    private Tower selectedTower;
    private int popMenu;
    private int score;
    public static int numWave;
    private int life;
    private int money;
    private MediaPlayer mPlayer;
    private float volume;
    private boolean story;
    private boolean cutscene;
    private boolean playing=false;
    private boolean pause;
    private long wavebackup;
    private int storypage;


    public GamePanel(Context context) {

        super(context);
        //add the callback to the surface holder to intercept events
        getHolder().addCallback(this);
        best = 0;

        //make gamePanel focusable so it can handle events
        setFocusable(true);
        projectResources = getResources();

    }
    public void playSounds (String action)
    {

        if(action=="title")
        {
            if(playing) {
                mPlayer.stop();
                mPlayer.release();
            }
            mPlayer = MediaPlayer.create(getContext(), R.raw.medievaltdsong); // in 2nd param u have to pass your desire ringtone
            mPlayer.setLooping(true);
            playing=true;
        }
        if(action=="endless")
        {
            if(playing) {
                mPlayer.stop();
                mPlayer.release();
            }
            mPlayer = MediaPlayer.create(getContext(), R.raw.apolosong); // in 2nd param u have to pass your desire ringtone
            mPlayer.setLooping(true);
            playing=true;
        }
        if(action=="gameover")
        {
            //mPlayer = MediaPlayer.create(getContext(), R.raw.explode); // in 2nd param u have to pass your desire ringtone
        }

        AudioManager am = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                volume=0;
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                volume=0;
                break;

        }

        mPlayer.setVolume(volume, volume);
        //mPlayer.start();

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if(titleButtonBitmap == null) {
            titleButtonBitmap = new Bitmap[5][3];
            for (int i = 0; i < titleButtonBitmap[0].length; i++) {
                titleButtonBitmap[0][i] = Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources, R.drawable.buttons), 0, i * 169, 459, 169);
                titleButtonBitmap[1][i] = Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources, R.drawable.creditsbutton), 0, i * 169, 116, 169);
                titleButtonBitmap[2][i] = Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources, R.drawable.optionsbutton), 0, i * 169, 116, 169);
            }
            titleButtonBitmap[3][0] = Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources, R.drawable.flechaizquierda), 0, 0, 116, 108);
            titleButtonBitmap[4][0] = Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources, R.drawable.flechaderecha), 0, 0, 116, 108);
            startScreenCreated();

        }

        if (thread == null) {
            thread = new MainThread(getHolder(), this);
            thread.setRunning(true);
        }

        thread.start();
    }
    public void startScreenCreated()
    {
        storypage=0;
        cutscene=true;
        MOVESPEED=-5;
        WIDTH=1604;
        HEIGHT=748;
        bg= new Background(BitmapFactory.decodeResource(projectResources, R.drawable.mountainbg2));
        bg.setSignImage(BitmapFactory.decodeResource(projectResources, R.drawable.woodsign));
        towBtns=null;
        buttons=new TitleButtons[4];
        String texto="";
        modo=0;
        tiempoDeCambiar=0;
        nuevaPantalla=0;
        pause=false;





        scaleFactorX=(getWidth())/WIDTH;
        scaleFactorY=getHeight()/HEIGHT;
        for(int i=0;i<buttons.length;i++)
        {
            switch(i)
            {
                case 0: texto="Story Mode";break;
                case 1: texto="Endless Mode";break;
                case 2: texto="How to Play";break;
            }
            if(i>=0&&i<3)
                buttons[i]=new TitleButtons(0,(int)(GamePanel.WIDTH/2-300),250+i*120,(459),169,texto,3);
            if(i==3)
                buttons[i]=new TitleButtons(1,(int)(GamePanel.WIDTH/2+270),270+(i-3)*120,(125),169,"",3);
           // if(i==4)
             //   buttons[i]=new TitleButtons(2,(int)(GamePanel.WIDTH/2+270),360+(i-3)*120,(125),169,"",3);
        }
        nBotonActivado=-1;
        cambiarPantalla=false;
        volume=25;

        playSounds("title");

    }
    public void gameScreenCreado()
    {
        modo=1;

        MOVESPEED=0;
        WIDTH=1604;
        HEIGHT=748;
        scaleFactorX=(getWidth())/WIDTH;
        scaleFactorY=getHeight()/HEIGHT;
        tiempoDeCambiar=0;
        nuevaPantalla=0;
        mapa=new char[6][15];
        matrixScale=80;
        selectedTower=null;
        popMenu=-1;
        buttonback=new TitleButtons[1];
        buttonback[0] = new TitleButtons(0, (int) (GamePanel.WIDTH / 2+20), 400, (459), 169, "Exit", 3);




        nextWaveCountDown=-2;
        nBotonActivado=-1;
        score=0;
        life=10;
        numWave=0;
        money=500000000;
        bg= new Background(BitmapFactory.decodeResource(projectResources, R.drawable.gamebackgroundwoods));
        gmSign=new GameMenuSign(BitmapFactory.decodeResource(projectResources,R.drawable.gamescreenobjects));
        towBtns=new TowerButtons(BitmapFactory.decodeResource(projectResources,R.drawable.towerbuttons),BitmapFactory.decodeResource(projectResources,R.drawable.towerbuttonss));
        double margin=matrixScale*1.1,space=matrixScale*1.7;
        towBtns.add((int)(margin),(int)(matrixScale*7.3),matrixScale,BitmapFactory.decodeResource(projectResources,R.drawable.arrowlvl1));
        towBtns.add((int)(margin+space*1),(int)(matrixScale*7.3),matrixScale,BitmapFactory.decodeResource(projectResources,R.drawable.snowlvl1));
        towBtns.add((int)(margin+space*2),(int)(matrixScale*7.3),matrixScale,BitmapFactory.decodeResource(projectResources,R.drawable.canonlvl1));
        towBtns.add((int)(margin+space*3),(int)(matrixScale*7.3),matrixScale,BitmapFactory.decodeResource(projectResources,R.drawable.upgradeicon));
        towBtns.add((int)(margin+space*4),(int)(matrixScale*7.3),matrixScale,BitmapFactory.decodeResource(projectResources,R.drawable.sellicon));
        numbers=new Bitmap[13][3];
        for(int i=0;i<13;i++) {
            numbers[i][0]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources, R.drawable.fontstylenumbersscore), 37*i, 0, 37, 56);
            numbers[i][1]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources, R.drawable.fontstylenumbersexpense), 37*i, 0, 37, 56);
            numbers[i][2]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources, R.drawable.fontstylenumbersgain), 37*i, 0, 37, 56);
        }

        lifeGauges=new Bitmap[10][2];
        cambiarPantalla=false;
        for(int i=0;i<mapa.length;i++)
        {
            for(int ii=0;ii<mapa[0].length;ii++)
            {
                mapa[i][ii]='o';
            }
        }

        cutscenesBitmap=new Bitmap[13];
        cutscenesBitmap[0]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia0));
        cutscenesBitmap[1]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia1));
        cutscenesBitmap[2]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia2));
        cutscenesBitmap[3]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia3));
        cutscenesBitmap[4]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia4esc1));
        cutscenesBitmap[5]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia5esc1));
        cutscenesBitmap[6]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia7esc2));
        cutscenesBitmap[7]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia8esc2));
        cutscenesBitmap[8]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia6esc1));
        cutscenesBitmap[9]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia9esc2));
        cutscenesBitmap[10]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia10esc3));
        cutscenesBitmap[11]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia11esc3));
        cutscenesBitmap[12]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.historia12esc3));


        cutscenesPassed=0;
        storySecuence=new int[4][2];
        storySecuence[0][0]=0;storySecuence[0][1]=6;
        storySecuence[1][0]=10;storySecuence[1][1]=9;
        storySecuence[2][0]=36;storySecuence[2][1]=12;
        storySecuence[3][0]=50;storySecuence[3][1]=13;




        towerstats=new Bitmap[3][4];
        towerstats[0][0]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.towerstatscreen));
        towerstats[1][0]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.popupsign));
        towerstats[0][1]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.towerstataone));
        towerstats[1][1]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.towerstatatwo));
        towerstats[2][1]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.towerstatathree));
        towerstats[0][2]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.towerstatsone));
        towerstats[1][2]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.towerstatstwo));
        towerstats[2][2]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.towerstatsthree));
        towerstats[0][3]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.towerstatcone));
        towerstats[1][3]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.towerstatctwo));
        towerstats[2][3]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.towerstatcthree));

        towerSprites = new TowerSprites(projectResources);
        bulletSprites = new BulletSprites(projectResources);

        explosionBitmap=new Bitmap[9];
        for(int i=0;i<explosionBitmap.length;i++)
            explosionBitmap[i]=Bitmap.createBitmap(BitmapFactory.decodeResource(projectResources,R.drawable.explosion),i*39,0,39,70);

        mapa[mapa.length/2][0]='O';
        mapa[mapa.length/2][mapa[0].length-1]='F';
        selectCircle=BitmapFactory.decodeResource(projectResources,R.drawable.scircle);
        Bitmap glg=BitmapFactory.decodeResource(projectResources,R.drawable.greenlifegauge);
        Bitmap rlg=BitmapFactory.decodeResource(projectResources,R.drawable.redlifegauge);

            for (int ii = 0; ii < lifeGauges.length; ii++) {
                lifeGauges[ii][0] = Bitmap.createBitmap(glg, 0, 10 * ii, 53, 10);
                lifeGauges[ii][1] = Bitmap.createBitmap(rlg, 0, 10 * ii, 53, 10);
            }



        int numFrames=21,width=56,height=65;
        ajnabisprites=new Bitmap[numFrames][2];
        Bitmap frames=BitmapFactory.decodeResource(projectResources,R.drawable.ajnabisimple);
        for(int i=0;i<numFrames;i++) {
            ajnabisprites[i][0]=Bitmap.createBitmap(frames,i*width,0,width,height);
            ajnabisprites[i][1]=Bitmap.createBitmap(frames,i*width,height,width,height);
        }
        numFrames=24;width=95;height=70;frames=BitmapFactory.decodeResource(projectResources,R.drawable.ogresprites);
        orcsprites=new Bitmap[numFrames][2];
        for(int i=0;i<numFrames;i++) {
            orcsprites[i][0]=Bitmap.createBitmap(frames,i*width,0,width,height);
            orcsprites[i][1]=Bitmap.createBitmap(frames,i*width,height,width,height);
        }
        numFrames=15;width=95;height=70;frames=BitmapFactory.decodeResource(projectResources,R.drawable.jabalisprites);
        boarsprites=new Bitmap[numFrames][2];
        for(int i=0;i<numFrames;i++) {
            boarsprites[i][0]=Bitmap.createBitmap(frames,i*width,0,width,height);
            boarsprites[i][1]=Bitmap.createBitmap(frames,i*width,height,width,height);
        }
        numFrames=23;width=100;height=120;frames=BitmapFactory.decodeResource(projectResources,R.drawable.sauron);
        sauronsprites=new Bitmap[numFrames][2];
        for(int i=0;i<numFrames;i++) {
            sauronsprites[i][0]=Bitmap.createBitmap(frames,i*width,0,width,height);
            sauronsprites[i][1]=Bitmap.createBitmap(frames,i*width,height,width,height);
        }


        monstruo=new ArrayList<>();
        torres=new ArrayList<>();
        balas=new ArrayList<>();
        explosiones=new ArrayList<>();
        waves=new ArrayList<>();
        rutaNuevos=calcularRuta(mapa,monstruo);

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try
            {
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkPointer(float x1,float y1, int width1,int height1, int px, int py) {
        if (px>=x1 && px<=x1+width1 &&py>=y1&&py<=y1+height1)
            return true;
            return false;
    }

    public boolean checkPointer(Rect r,int px,int py) {
        if(px>r.left && px<=r.right&&py>r.top&&py<=r.bottom)
        return true;
        return false;
    }

    public Bitmap callLetter(int type, String letra) {

        if(letra.equals("0")) return numbers[0][type];
        if(letra.equals("1")) return numbers[1][type];
        if(letra.equals("2")) return numbers[2][type];
        if(letra.equals("3")) return numbers[3][type];
        if(letra.equals("4")) return numbers[4][type];
        if(letra.equals("5")) return numbers[5][type];
        if(letra.equals("6")) return numbers[6][type];
        if(letra.equals("7")) return numbers[7][type];
        if(letra.equals("8")) return numbers[8][type];
        if(letra.equals("9")) return numbers[9][type];
        if(letra.equals("$")) return numbers[10][type];
        if(letra.equals("-")) return numbers[11][type];
        if(letra.equals(",")) return numbers[12][type];

        return null;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            {
                if(!cambiarPantalla)
                {
                    if (modo == 0) {
                        if (nBotonActivado == -1)
                            for (int i = 0; i < buttons.length; i++) {
                                if (checkPointer((buttons[i].getX() * scaleFactorX), (buttons[i].getY() * scaleFactorY), buttons[i].getWidth(), buttons[i].getHeight(), (int) event.getX(), (int) event.getY())) {
                                    nBotonActivado = i;
                                    if(nBotonActivado==0)
                                        story=true;
                                    else {
                                        story = false;
                                        cutscene=false;
                                        if(nBotonActivado==1)
                                        playSounds("endless");
                                    }


                                    return true;
                                }
                            }
                    }
                    if (modo == 1) {

                        if(event.getAction()==MotionEvent.ACTION_DOWN) {
                            int x = (int) (event.getX() / scaleFactorX), y = (int) (event.getY() / scaleFactorY);
                            //PAUSE
                            if(!cutscene)
                            {
                            if (checkPointer(new Rect((int) (17.7f * matrixScale), 0, (int) (27.5f * matrixScale), (int) (1.0f * matrixScale)), x, y)) {
                                if (!pause) {
                                    bg.setSignImage(BitmapFactory.decodeResource(projectResources,R.drawable.pausemenu));
                                    buttonback[0] = new TitleButtons(0, (int) (GamePanel.WIDTH / 2+20), 400, (459), 169, "Exit", 3);
                                    resume = new TitleButtons(0, (int) (GamePanel.WIDTH / 2 - 450), 400, (459), 169, "Resume", 3);

                                    pause = true;
                                    wavebackup = nanosElapsedSince(nextWaveCountDown);
                                    return true;
                                }
                            }
                                if (pause&&checkPointer((int) (buttonback[0].getX()), (int) (buttonback[0].getY()), (int) (buttonback[0].getWidth()), (int) (buttonback[0].getHeight()),x,y))
                                {
                                    modo = 0;
                                    nBotonActivado = -1;
                                    cambiarPantalla = false;
                                    startScreenCreated();
                                    return true;
                                }
                                if(pause&&(checkPointer(new Rect((int) (17.7f * matrixScale), 0, (int) (27.5f * matrixScale), (int) (1.0f * matrixScale)), x, y)||checkPointer((int) (resume.getX()), (int) (resume.getY()), (int) (resume.getWidth()), (int) (resume.getHeight()), x,y))){
                                    pause = false;
                                    for(int i=0;i<waves.size();i++)
                                        waves.get(i).unPause();
                                    for(int i=0;i<torres.size();i++)
                                        torres.get(i).unPause();
                                    for(int i=0;i<monstruo.size();i++)
                                        monstruo.get(i).unPause();
                                    return true;
                                }

                            if(!pause) {
                                for (int i = 0; i < towBtns.getSize(); i++) {
                                    if (checkPointer(towBtns.getRectangle(i), x, y)) {
                                        towBtns.setSelected(i);
                                    }

                                }
                            }
                                if (checkPointer(new Rect(16 * matrixScale, (int) (7.7f * matrixScale), 20 * matrixScale, (int) (9.7f * matrixScale)), x, y)) {
                                    if (!story || numWave < 50) {
                                        numWave++;
                                        if(!cutscene&&cutscenesPassed<storySecuence.length&&(numWave==storySecuence[cutscenesPassed][0])) {
                                            cutscene = true;
                                        }
                                        if(!cutscene) {
                                            long bonus = numWave * 2 + 20;
                                            if (bonus > 100)
                                                bonus = 100;
                                            bonus = bonus - secondsElapsedSince(nextWaveCountDown);

                                            if (nextWaveCountDown == 0)
                                                bonus = 100;
                                            if (numWave <= 10)
                                                bonus += 50;
                                            if (nextWaveCountDown == -2)
                                                bonus = 0;
                                            money += bonus;
                                            nextWaveCountDown = 0;
                                            int code = 0;
                                            if (numWave <= 10 && story)
                                                code = numWave;

                                            waves.add(new EnemyWaveOrchestrator(numWave, code));
                                        }
                                    }
                                }


                                x = (int) ((x - matrixScale * 2.7f) / matrixScale);
                                y = ((y - matrixScale) / matrixScale);
                                boolean cambio = false;
                                if (towBtns.getSelected() != -1) {
                                    if (selectedTower != null) {
                                        boolean equis = false, ye = false;
                                        if (x == selectedTower.getX() && y == selectedTower.getY()) {
                                            equis = true;
                                            ye = true;
                                        } else {
                                            if (selectedTower.getX() < mapa[0].length / 2 && (x > selectedTower.getX() && x <= selectedTower.getX() + 5)) {
                                                equis = true;
                                            } else {
                                                if (selectedTower.getX() >= mapa[0].length / 2 && (x < selectedTower.getX() && x >= selectedTower.getX() - 5)) {
                                                    equis = true;
                                                }
                                            }
                                            if (selectedTower.getY() < mapa.length / 2 && y == selectedTower.getY() + 2) {
                                                ye = true;
                                            } else {
                                                if (selectedTower.getY() >= mapa.length / 2 && y == selectedTower.getY()) {
                                                    ye = true;
                                                }
                                            }
                                        }
                                        if (equis && ye) {
                                            if (mapa[selectedTower.getY()][selectedTower.getX()] != 'X' && towBtns.getSelected() < 3) {
                                                cambio = true;
                                            }
                                            if (mapa[selectedTower.getY()][selectedTower.getX()] == 'X' && towBtns.getSelected() >= 3) {
                                                if (towBtns.getSelected() == 4 || (towBtns.getSelected() == 3 && selectedTower.getLevel() < 2))
                                                    cambio = true;
                                            }
                                        }
                                    }
                                }
                                boolean insideBounds = (x >= 0 && x < mapa[0].length && y >= 0 && y < mapa.length);
                                int posTower;

                                if (!cambio) {
                                    posTower = findTower(x, y);
                                    if (posTower != -1) {
                                        selectedTower = torres.get(posTower);
                                    } else {
                                        selectedTower = null;
                                        if (insideBounds) {
                                            final GameObject newTower = new GameObject.Builder()
                                                    .setX(x)
                                                    .setY(y)
                                                    .setMatrixScale(matrixScale)
                                                    .buildGameObject();

                                            switch (towBtns.getSelected()) {
                                                case 0:
                                                    selectedTower = new ArrowTower(newTower);
                                                    break;
                                                case 1:
                                                    selectedTower = new SnowTower(newTower);
                                                    break;
                                                case 2:
                                                    selectedTower = new CannonTower(newTower);
                                                    break;
                                            }
                                        }
                                    }
                                } else {
                                    posTower = findTower(selectedTower.getX(), selectedTower.getY());
                                    insideBounds = (selectedTower.getX() >= 0 && selectedTower.getX() < mapa[0].length && y >= selectedTower.getY() && selectedTower.getY() < mapa.length);
                                }
                                int posx, posy;
                                if (cambio) {
                                    posx = selectedTower.getX();
                                    posy = selectedTower.getY();
                                } else {
                                    posx = x;
                                    posy = y;
                                }
                                if (towBtns.getSelected() != -1 && insideBounds && !ocupadoPorMonstruo(posx, posy)) {
                                    boolean empalmado = false;

                                    if (mapa[posy][posx] == 'X')
                                        empalmado = true;

                                    boolean rollback = false;


                                    if (cambio) {
                                        ArrayList<Coordinates> rNBackup;
                                        ArrayList<CyrusMonster> monsterBackup;
                                        if (!empalmado && (towBtns.getSelected() == 0 || towBtns.getSelected() == 1 || towBtns.getSelected() == 2)) {
                                            monsterBackup = new ArrayList<>();
                                            for (int i = 0; monstruo != null && monstruo.size() > 0 && i < monstruo.size(); i++) {
                                                monsterBackup.add(new CyrusMonster(monstruo.get(i)));
                                            }

                                            rNBackup = new ArrayList<>();
                                            if (mapa[posy][posx] != 'O' && mapa[posy][posx] != 'F') {
                                                mapa[selectedTower.getY()][selectedTower.getX()] = 'X';
                                                rNBackup = calcularRuta(mapa, monsterBackup);
                                            } else
                                                rollback = true;

                                            if (!rollback && rutaNuevos.size() > rNBackup.size()) {
                                                rollback = true;
                                            }


                                            for (int i = 0; i < monsterBackup.size() && i < monstruo.size() && !rollback; i++) {
                                                if (monstruo.get(i).getObjectiveList().size() > monsterBackup.get(i).getObjectiveList().size()) {
                                                    rollback = true;
                                                }

                                            }
                                            if (!rollback) {
                                                rutaNuevos = rNBackup;
                                                monstruo = monsterBackup;
                                            }
                                        }
                                    }

                                    if (!rollback) {

                                        switch (towBtns.getSelected()) {
                                            case 0:
                                                if (!cambio)
                                                    updatePopMenuCode();
                                                if (!empalmado && cambio) {
                                                    if (money >= 100) {
                                                        final GameObject newTower = new GameObject.Builder()
                                                                .setX(selectedTower.getX())
                                                                .setY(selectedTower.getY())
                                                                .setMatrixScale(matrixScale)
                                                                .buildGameObject();
                                                        torres.add(new ArrowTower(newTower));
                                                        money -= 100;
                                                        selectedTower.setWidth(1);
                                                    } else {
                                                        mapa[selectedTower.getY()][selectedTower.getX()] = 'o';
                                                        selectedTower = null;
                                                    }
                                                    towBtns.setSelected(-1);
                                                }
                                                break;
                                            case 1:
                                                if (!cambio)
                                                    updatePopMenuCode();
                                                if (!empalmado && cambio) {
                                                    if (money >= 150) {
                                                        final GameObject newTower = new GameObject.Builder()
                                                                .setX(selectedTower.getX())
                                                                .setY(selectedTower.getY())
                                                                .setMatrixScale(matrixScale)
                                                                .buildGameObject();
                                                        torres.add(new SnowTower(newTower));
                                                        money -= 150;
                                                        selectedTower.setWidth(1);
                                                    } else {
                                                        mapa[selectedTower.getY()][selectedTower.getX()] = 'o';
                                                        selectedTower = null;
                                                    }
                                                    towBtns.setSelected(-1);
                                                }
                                                break;
                                            case 2:
                                                if (!cambio)
                                                    updatePopMenuCode();
                                                if (!empalmado && cambio) {
                                                    if (money >= 300) {
                                                        final GameObject newTower = new GameObject.Builder()
                                                                .setX(selectedTower.getX())
                                                                .setY(selectedTower.getY())
                                                                .setMatrixScale(matrixScale)
                                                                .buildGameObject();
                                                        torres.add(new CannonTower(newTower));
                                                        money -= 300;
                                                        selectedTower.setWidth(1);
                                                    } else {
                                                        mapa[selectedTower.getY()][selectedTower.getX()] = 'o';
                                                        selectedTower = null;
                                                    }
                                                    towBtns.setSelected(-1);
                                                }
                                                break;
                                            case 3:
                                                if (empalmado) {
                                                    selectedTower = torres.get(posTower);
                                                    updatePopMenuCode();
                                                    if (cambio) {
                                                        int before = money;
                                                        towBtns.setSelected(-1);
                                                        if (selectedTower.getType().equals(ArrowTower.class)) {
                                                            if (selectedTower.getLevel() == 0 && money >= 400)
                                                                money -= 400;
                                                            if (selectedTower.getLevel() == 1 && money >= 1000)
                                                                money -= 1000;
                                                        }
                                                        if (selectedTower.getType().equals(SnowTower.class)) {
                                                            if (selectedTower.getLevel() == 0 && money >= 600)
                                                                money -= 600;
                                                            if (selectedTower.getLevel() == 1 && money >= 1500)
                                                                money -= 1500;
                                                        }
                                                        if (selectedTower.getType().equals(CannonTower.class)) {
                                                            if (selectedTower.getLevel() == 0 && money >= 800)
                                                                money -= 800;
                                                            if (selectedTower.getLevel() == 1 && money >= 2000)
                                                                money -= 2000;
                                                        }
                                                        if (before != money)
                                                            torres.get(posTower).raiseLevel();
                                                        selectedTower = torres.get(posTower);
                                                    }
                                                }
                                                break;
                                            case 4:
                                                if (empalmado) {
                                                    selectedTower = torres.get(posTower);
                                                    updatePopMenuCode();
                                                    if (cambio) {
                                                        if (selectedTower.getType().equals(ArrowTower.class)) {
                                                            if (selectedTower.getLevel() == 0)
                                                                money += 100 / 3;
                                                            if (selectedTower.getLevel() == 1)
                                                                money += 400 / 3;
                                                            if (selectedTower.getLevel() == 2)
                                                                money += 1000 / 3;
                                                        }
                                                        if (selectedTower.getType().equals(SnowTower.class)) {
                                                            if (selectedTower.getLevel() == 0)
                                                                money += 150 / 3;
                                                            if (selectedTower.getLevel() == 1)
                                                                money += 600 / 3;
                                                            if (selectedTower.getLevel() == 2)
                                                                money += 1500 / 3;
                                                        }
                                                        if (selectedTower.getType().equals(CannonTower.class)) {
                                                            if (selectedTower.getLevel() == 0)
                                                                money += 300 / 3;
                                                            if (selectedTower.getLevel() == 1)
                                                                money += 800 / 3;
                                                            if (selectedTower.getLevel() == 2)
                                                                money += 2000 / 3;
                                                        }
                                                        torres.remove(posTower);
                                                        mapa[selectedTower.getY()][selectedTower.getX()] = 'o';
                                                        towBtns.setSelected(-1);
                                                        selectedTower = null;
                                                        rutaNuevos = calcularRuta(mapa, monstruo);
                                                    }
                                                }
                                                break;

                                        }

                                    } else {
                                        if (mapa[selectedTower.getY()][selectedTower.getX()] != 'O' && mapa[selectedTower.getY()][selectedTower.getX()] != 'F')
                                            mapa[selectedTower.getY()][selectedTower.getX()] = 'o';
                                    }


                                }
                            }
                            else {
                                storypage++;
                            }
                                return true;

                            }
                            if (event.getAction() == MotionEvent.ACTION_UP) {

                                return true;
                            }

                        //cambiarPantalla = true;
                        return true;
                    }
                    if(modo==2) {//HOW TO PLAY
                        if (checkPointer((int) (buttonback[0].getX() * scaleFactorX), (int) (buttonback[0].getY() * scaleFactorY), (int) (buttonback[0].getWidth() * scaleFactorX), (int) (buttonback[0].getHeight() * scaleFactorY), (int) event.getX(), (int) event.getY())) {
                            nBotonActivado = 0;
                            return true;
                        }
                        if (checkPointer((int) (buttonpropre[0].getX() * scaleFactorX), (int) (buttonpropre[0].getY() * scaleFactorY), (int) (buttonpropre[0].getWidth() * scaleFactorX), (int) (buttonpropre[0].getHeight() * scaleFactorY), (int) event.getX(), (int) event.getY())) {
                            if(nBotonActivado==-6) {
                                nBotonActivado = -1;

                            }
                            else
                                nBotonActivado --;

                            return true;
                        }
                        if (checkPointer((int) (buttonpropre[1].getX() * scaleFactorX), (int) (buttonpropre[1].getY() * scaleFactorY), (int) (buttonpropre[1].getWidth() * scaleFactorX), (int) (buttonpropre[1].getHeight() * scaleFactorY), (int) event.getX(), (int) event.getY())) {
                            if(nBotonActivado==-1)
                                nBotonActivado = -6;
                            else
                                nBotonActivado++;

                            return true;
                        }
                    }
                    if(modo== 3){//CREDITS
                        if (checkPointer((int) (buttonback[0].getX() * scaleFactorX), (int) (buttonback[0].getY() * scaleFactorY), (int) (buttonback[0].getWidth() * scaleFactorX), (int) (buttonback[0].getHeight() * scaleFactorY), (int) event.getX(), (int) event.getY())) {
                            nBotonActivado = 0;
                            return true;
                        }
                    }
                    if(modo==4) {//SETTINGS
                        if (checkPointer((int) (buttonback[0].getX() * scaleFactorX), (int) (buttonback[0].getY() * scaleFactorY), (int) (buttonback[0].getWidth() * scaleFactorX), (int) (buttonback[0].getHeight() * scaleFactorY), (int) event.getX(), (int) event.getY())) {
                            nBotonActivado = 0;
                            return true;
                        }
                    }
                    if(modo==5){//GAME OVER
                        if (checkPointer((int) (buttonback[0].getX() * scaleFactorX), (int) (buttonback[0].getY() * scaleFactorY), (int) (buttonback[0].getWidth() * scaleFactorX), (int) (buttonback[0].getHeight() * scaleFactorY), (int) event.getX(), (int) event.getY())) {
                            nBotonActivado = 0;
                            return true;
                        }
                    }
                }
            }

        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {

            return true;
        }
        return super.onTouchEvent(event);
    }

    public void updatePopMenuCode() {

        final int tenth;
        if (selectedTower.getType().equals(ArrowTower.class)) {
            tenth = 0;
        } else if (selectedTower.getType().equals(SnowTower.class)) {
            tenth = 10;
        } else if  (selectedTower.getType().equals(CannonTower.class)) {
            tenth = 20;
        } else {
            throw new InvalidOptionException("Could not calculate popMenu for type: " + selectedTower.getType());
        }

        popMenu = tenth + selectedTower.getLevel();
    }

    public boolean ocupadoPorMonstruo(int puntoX,int puntoY)
    {
        for(int i=0;monstruo!=null&&monstruo.size()>0&&i<monstruo.size();i++)
        {
            if(monstruo.get(i).getActual().getX()==puntoX &&monstruo.get(i).getActual().getY()==puntoY)
                return true;
        }

        return false;
    }
    public int findTower(int x,int y)
    {
        for(int i = 0;i<torres.size();i++)
        {
           if(torres.get(i).getX()==x &&torres.get(i).getY()==y)
                return i;
        }
        return -1;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = true;
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        {
            volume+=5;
            if(volume>50) volume=50;
            mPlayer.setVolume(volume,volume);
        }
        else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            volume-=5;
            if(volume<0)volume=0;

            mPlayer.setVolume(volume,volume);
        }
        return result;
    }

    public  ArrayList<Coordinates> calcularRuta(char[][]mapa, ArrayList<CyrusMonster> monster)
    {
        int[][]paths=convertirAMatrizAdj(mapa);

        Coordinates origen=null, fin=null;
        for(int i=0;i<mapa.length;i++)
        {
            for(int ii=0;ii<mapa[0].length;ii++)
            {
                if(mapa[i][ii]=='O')
                    origen=new Coordinates(ii,i);
                if(mapa[i][ii]=='F')
                    fin=new Coordinates(ii,i);
            }
        }
        ArrayList<Coordinates> caminos=new ArrayList<>();
        caminos.add(origen);
        char[][] solucion=new char[mapa.length][mapa[0].length];
        for(int i=0;i<mapa.length;i++)
        {
            for(int ii=0;ii<mapa[0].length;ii++)
            {
                solucion[i][ii]=mapa[i][ii];
            }
        }

        caminos=convertirPaths(paths,caminos,fin.getY()*mapa[0].length+fin.getX(),mapa[0].length);
        /*for(int i=0;i<caminos.size();i++)
        {
            solucion[caminos.get(i).getY()][caminos.get(i).getX()]='0';
        }*/

        if(monster!=null&&monster.size()>0)
        for(int i=0;i<monster.size();i++)
        {

                ArrayList<Coordinates> m = new ArrayList<>();
                m.add(new Coordinates(monster.get(i).getActual().getX(), monster.get(i).getActual().getY()));
                if (!monster.get(i).noObjList())
                    monster.get(i).setObjectiveList(convertirPaths(paths, m, fin.getY() * mapa[0].length + fin.getX(), mapa[0].length));

        }
        return caminos;
    }
    public static ArrayList<Coordinates> convertirPaths(int[][]paths, ArrayList<Coordinates> camino, int nFinal, int ancho)
    {
        int nNodo=camino.get(camino.size()-1).getY()*ancho+camino.get(camino.size()-1).getX(), nPaso=nFinal;
        while(paths[nNodo][nPaso]!=-1&&paths[nNodo][nPaso]!=999)
        {
            nPaso=paths[nNodo][nPaso];
        }
        camino.add(new Coordinates(nPaso%ancho,(nPaso-nPaso%ancho)/ancho));
        if(nPaso!=nFinal)
        {

            camino=convertirPaths(paths,camino,nFinal,ancho);
        }

        return camino;
    }
    public static int[][]convertirAMatrizAdj(char[][]mapa)
    {
        int[][]mAdj=new int[mapa.length*mapa[0].length][mapa.length*mapa[0].length];

        for(int i=0;i<mAdj.length;i++)
        {
            for(int ii=0;ii<mAdj[0].length;ii++)
            {

                mAdj[i][ii]=999;

            }
        }
        for(int i=0;i<mapa.length;i++)
        {
            for(int ii=0;ii<mapa[0].length;ii++)
            {
                int nodoActual=i*mapa[0].length+ii;
                //LEFT
                if(ii>0)
                {
                    mAdj[nodoActual][(i)*mapa[0].length+ii-1]=1;
                }
                //UP
                if(i>0)
                {
                    mAdj[nodoActual][(i-1)*mapa[0].length+ii]=1;
                }
                //RIGHT
                if(ii<mapa[0].length-1)
                {
                    mAdj[nodoActual][(i)*mapa[0].length+ii+1]=1;
                }
                //DOWN
                if(i<mapa.length-1)
                {
                    mAdj[nodoActual][(i+1)*mapa[0].length+ii]=1;
                }





            }
        }

        for(int iii=0;iii<mapa.length;iii++)
        {
            for(int ii=0;ii<mapa[0].length;ii++)
            {
                if(mapa[iii][ii]=='X')
                    for(int i=0;i<mapa.length*mapa[0].length;i++)
                    {
                        mAdj[i][iii*mapa[0].length+ii]=999;
                        mAdj[iii*mapa[0].length+ii][i]=999;
                    }
            }
        }
        for(int i=0;i<mapa.length*mapa[0].length;i++)
            mAdj[i][i]=0;


        int[][] paths =FloydWarshall(mAdj);

        return paths;
    }
    public static int[][] FloydWarshall(int[][] M)
    {
        int[][] P=new int[M.length][M.length];

        for(int k=0;k<M.length;k++)
        {
            for(int i=0;i<M.length;i++)
            {    for(int j=0;j<M.length;j++)
            {
                if(M[i][k]+M[k][j]<M[i][j])
                {
                    M[i][j]=M[i][k]+M[k][j];
                    P[i][j]=k;
                }
            }
            }
        }
        for(int i=0;i<M.length;i++)
        {
            for(int ii=0;ii<M[0].length;ii++)
            {
                if(i==ii|| M[i][ii]==1)
                    P[i][ii]=-1;
                if(M[i][ii]==999)
                    P[i][ii]=999;
            }
        }

        return P;

    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            if (modo == 0)
                drawStartScreen(canvas);
            if (modo == 1)
                drawGameScreen(canvas);
            if(modo==2)
                drawHTPScreen(canvas);
            if(modo==3)
                drawCreditsScreen(canvas);
            if(modo==4)
                drawSettingsScreen(canvas);
            if(modo==5)
                drawGameOverScreen(canvas);

            canvas.restoreToCount(savedState);
        }
    }

    public void drawStartScreen(Canvas canvas)
    {
        bg.drawSign(canvas);
        bg.drawTitle(canvas);
            for(int i=0;i< buttons.length;i++) {
                buttons[i].draw(canvas);
            }
    }
    public void drawGameScreen(Canvas canvas)
    {
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(matrixScale);




        for(int i=0;i<mapa.length;i++) {
            for(int ii=0;ii<mapa[0].length;ii++) {
                canvas.drawText(mapa[i][ii]+"", ii*matrixScale+matrixScale*2.7f, i*matrixScale+matrixScale*2, paint);
            }
        }
        if(selectedTower!=null&&selectedTower.getX()>=0 && selectedTower.getY()>=0&&selectedTower.getX()<mapa[0].length&&selectedTower.getY()<mapa.length) {
            float posx, posy;
            posy = selectedTower.getY() * matrixScale + matrixScale*1f;

            posx = selectedTower.getX() * matrixScale + matrixScale * 2.2f;
            canvas.drawBitmap(selectCircle, posx, posy, null);
        }
        for(int i=mapa[0].length-1;i>=0;i--)
        {
            for(int ii=0;ii<mapa.length;ii++)
            {
                for(int iii=0;iii<torres.size();iii++)
                    if(torres.get(iii).getX()==i&&torres.get(iii).getY()==ii)
                        torres.get(iii).draw(canvas, towerSprites);
                for(int iii=0;iii<monstruo.size();iii++)
                    if(monstruo.get(iii).getActual().getX()==i &&monstruo.get(iii).getActual().getY()==ii) {
                        monstruo.get(iii).draw(canvas);
                        float percentage=(float)monstruo.get(iii).getHitpoints()/(float)monstruo.get(iii).getMaxHitpoints()*10.0f;
                        int color;
                        if(percentage>0&&percentage<=10) {
                            if (percentage > 2.5)
                                color=0;
                            else
                                color=1;
                                if(percentage<1)
                                    percentage=1;
                            monstruo.get(iii).drawLifeGauge(lifeGauges[10 - (int) (percentage)][color], canvas);
                        }
                    }
            }
        }
        for(int i=0;i<explosiones.size();i++)
        {
            explosiones.get(i).draw(canvas);
        }

        balas.forEach(bullet -> bullet.draw(canvas, bulletSprites));


        /*for(int i=monstruo.size()-1;i>=0;i--)
        monstruo.get(i).draw(canvas);

        for(int i=0;i<torres.size();i++)
            torres.get(i).draw(canvas);*/

        gmSign.draw(canvas);
        towBtns.draw(canvas);
        canvas.drawBitmap(towerstats[0][0], (int) (matrixScale * 10.8), (int) (matrixScale * 7.2), null);
        String mtext="",s=score+"";
        for(int i=0;i<(s).length();i++) {
            canvas.drawBitmap(callLetter(0, (s).charAt(i) + ""), 820 + i * 20, 5, null);
        }
        s=money+"";
        for(int i=0;i<(s).length();i++)
        {
            mtext=(s).charAt((s).length()-1-i)+mtext;
            if((i+1)%3==0&&i+1<(s).length())
                mtext=','+mtext;
        }
        mtext="$"+mtext;
        for(int i=0;i<mtext.length();i++) {
            canvas.drawBitmap(callLetter(0,mtext.charAt(i)+""), 87 + i * 20, 10, null);
        }
        s=life+"";
        for(int i=0;i<(s).length();i++)
        {
            canvas.drawBitmap(callLetter(0,(s).charAt(i)+""), 1379 + i * 20, 586, null);
        }

        s=numWave+"";
        for(int i=0;i<(s).length();i++)
        {
            canvas.drawBitmap(callLetter(0,(s).charAt(i)+""), 1485 + i * 20, 587, null);
        }
        if(!story||numWave<50) {
            int recorrer = numWave * 2 + 20;
            if (recorrer > 100)
                recorrer = 100;
            long calc = (recorrer - secondsElapsedSince(nextWaveCountDown));
            recorrer = 7;
            if (calc < 0)
                s = 0 + "";

            else {

                if (calc >= 10 && calc < 100)
                    recorrer = -2;
                if (calc > 100)
                    recorrer = -11;

                s = calc + "";

            }
            for (int i = 0; i < (s).length(); i++) {
                canvas.drawBitmap(callLetter(0, (s).charAt(i) + ""), 1400 + recorrer + i * 20, 125, null);
            }
        }

        if(selectedTower!=null)
        {
            //fija
            final int towerType;
            if (selectedTower.getType().equals(ArrowTower.class)) {
                towerType = 1;
            } else if (selectedTower.getType().equals(SnowTower.class)) {
                towerType = 2;
            } else if (selectedTower.getType().equals(CannonTower.class)) {
                towerType = 3;
            } else {
                throw new InvalidOptionException("Could not calculate popMenu for type: " + selectedTower.getType());
            }
            canvas.drawBitmap(towerstats[selectedTower.getLevel()][towerType],(int) (matrixScale * 10.8), (int) (matrixScale * 7.2), null);

            if(selectedTower.getX()>=0 && selectedTower.getY()>=0&&selectedTower.getX()<mapa[0].length&&selectedTower.getY()<mapa.length&&(towBtns.getSelected()==3 ||towBtns.getSelected()==4||selectedTower.getWidth()==0))
            {
                float posx,posy;
                if(selectedTower.getY()<mapa.length/2)
                    posy=selectedTower.getY()*matrixScale+matrixScale;
                else
                    posy=selectedTower.getY()*matrixScale-matrixScale;
                if(selectedTower.getX()<mapa[0].length/2)
                    posx=selectedTower.getX()*matrixScale+matrixScale*3.6f;
                else
                    posx=selectedTower.getX()*matrixScale+matrixScale*2.7f-6f*matrixScale;
                int popCode=popMenu;
                if(towBtns.getSelected()==3)
                    popCode++;
                if((popCode>=0&&popCode<=2)||(popCode>=10&&popCode<=12)||(popCode>=20&&popCode<=22))
                    canvas.drawBitmap(towerstats[1][0], posx, posy + 2 * matrixScale, null);
                int price=0;
                switch(popCode)
                {
                    case 1: canvas.drawBitmap(towerstats[0][1],posx,posy,null);price=100;break;
                    case 2: canvas.drawBitmap(towerstats[1][1],posx,posy,null);price=400;break;
                    case 3: canvas.drawBitmap(towerstats[2][1],posx,posy,null);price=1000;break;
                    case 11: canvas.drawBitmap(towerstats[0][2],posx,posy,null);price=150;break;
                    case 12: canvas.drawBitmap(towerstats[1][2],posx,posy,null);price=600;break;
                    case 13: canvas.drawBitmap(towerstats[2][2],posx,posy,null);price=1500;break;
                    case 21: canvas.drawBitmap(towerstats[0][3],posx,posy,null);price=300;break;
                    case 22: canvas.drawBitmap(towerstats[1][3],posx,posy,null);price=800;break;
                    case 23: canvas.drawBitmap(towerstats[2][3],posx,posy,null);price=2000;break;
                }
                if((popCode>=0&&popCode<=2)||(popCode>=10&&popCode<=12)||(popCode>=20&&popCode<=22)) {
                    String ptext = "";
                    int color = 1;
                    if (towBtns.getSelected() == 4) {
                        color = 2;
                        price=(price)/3;
                    }
                    s = price + "";
                    for (int i = 0; i < (s).length(); i++) {
                        ptext = (s).charAt((s).length() - 1 - i) + ptext;
                        if ((i + 1) % 3 == 0 && i + 1 < (s).length())
                            ptext = ',' + ptext;
                    }

                    for (int i = 0; i < ptext.length(); i++) {
                        canvas.drawBitmap(callLetter(color, ptext.charAt(i) + ""), (posx + matrixScale * 1.5f) + i * 20, posy + 2.23f * matrixScale, null);
                    }
                }


            }
        }
        else
        {
            switch(towBtns.getSelected())
            {
                case 0: canvas.drawBitmap(towerstats[0][1], (int) (matrixScale * 10.8), (int) (matrixScale * 7.2), null);break;
                case 1: canvas.drawBitmap(towerstats[0][2], (int) (matrixScale * 10.8), (int) (matrixScale * 7.2), null);break;
                case 2: canvas.drawBitmap(towerstats[0][3], (int) (matrixScale * 10.8), (int) (matrixScale * 7.2), null);break;
            }
        }

        if(cutscene&&cutscenesPassed<storySecuence.length&&(numWave==storySecuence[cutscenesPassed][0]&&storypage<storySecuence[cutscenesPassed][1])) {
           canvas.drawBitmap(cutscenesBitmap[storypage], 0, 0, null);
        }


        if(pause)
        {
            bg.drawSign(canvas);
            buttonback[0].draw(canvas);
            resume.draw(canvas);
        }

        //canvas.drawRect(16f*matrixScale,7.7f*matrixScale,20f*matrixScale,9.7f*matrixScale,paint);
    }
    public void update()
    {
        bg.update();
            if (modo == 0)
                updateStartScreen();
            if(modo==1)
                updateGameScreen();
            if (modo == 2)
                updateHTPScreen();
            if (modo == 3)
                updateCreditsScreen();
            if (modo == 4)
                updateSettingsScreen();
            if (modo == 5)
                updateGameOverScreen();

    }
    public void updateStartScreen()
    {

        if(nBotonActivado!=-1) {


            buttons[nBotonActivado].update();
            if (buttons[nBotonActivado].getPlayedOnce()) {
                if (!cambiarPantalla) {
                    cambiarPantalla = true;
                    tiempoDeCambiar = System.nanoTime();
                }

                nuevaPantalla = nBotonActivado + 1;
                nBotonActivado = -1;

            }
        }
        if(tiempoDeCambiar!=0) {
            if ((millisElapsedSince(tiempoDeCambiar) > 150 )) {
                nuevaPantalla(nuevaPantalla);
            }
        }

    }
    public void updateGameScreen()
    {
        if(cutscene) {
            if(cutscenesPassed<storySecuence.length&&(numWave==storySecuence[cutscenesPassed][0]&&storypage==storySecuence[cutscenesPassed][1])) {
                cutscene = false;
                cutscenesPassed++;
            }
        }
        else
        if(!pause) {
            if (cambiarPantalla && tiempoDeCambiar == 0) {
                //cambiarPantalla=false;
                tiempoDeCambiar = System.nanoTime();
            }
            gmSign.update();
            towBtns.update();
            for (int i = 0; i < waves.size(); i++) {
                int monstercode = waves.get(i).update();
                if (monstercode != -1) {
                    switch (monstercode) {
                        case 0:
                            monstruo.add(new CyrusMonster(0, 56, 65, 21, 2, rutaNuevos, 7 * waves.get(i).getNumWave(), matrixScale));
                            break;
                        case 1:
                            monstruo.add(new CyrusMonster(1, 95, 70, 24, 2, rutaNuevos, 7 * waves.get(i).getNumWave(), matrixScale));
                            break;
                        case 2:
                            monstruo.add(new CyrusMonster(2, 95, 70, 15, 2, rutaNuevos, 7 * waves.get(i).getNumWave(), matrixScale));
                            break;
                        case 3:
                            monstruo.add(new CyrusMonster(3, 100, 120, 23, 2, rutaNuevos, 7 * waves.get(i).getNumWave(), matrixScale));
                            break;
                    }
                }
            }
            for (int i = 0; i < waves.size(); i++) {
                if (waves.get(i).concluded()) {
                    waves.remove(i);
                    i--;
                }
            }
            if (!story || numWave < 50) {
                if (waves.size() == 0 && nextWaveCountDown == 0)
                    nextWaveCountDown = System.nanoTime();
                int criteria = numWave * 2 + 20;
                if (criteria > 100) {
                    criteria = 100;
                }

                if (nextWaveCountDown > 0 && secondsElapsedSince(nextWaveCountDown) > criteria) {
                    nextWaveCountDown = 0;
                    numWave++;
                    if(!cutscene&&cutscenesPassed<storySecuence.length&&(numWave==storySecuence[cutscenesPassed][0])) {
                        cutscene = true;

                    }
                    if(!cutscene) {
                        int code = 0;
                        if (numWave <= 10 && story)
                            code = numWave;

                        waves.add(new EnemyWaveOrchestrator(numWave, code));
                    }
                }
            }
            for (int i = 0; i < torres.size(); i++) {
                torres.get(i).update();
                CyrusMonster m = torres.get(i).scanForTarget(monstruo);
                if (m != null) {
                    Bullet bala;
                    bala = torres.get(i).fireBullet(m);
                    if (bala != null) balas.add(bala);
                }
            }

            for (int i = 0; i < monstruo.size(); i++) {
                if (monstruo.get(i).getHitpoints() > 0)
                    monstruo.get(i).update();
                else {

                    explosiones.add(new Explosion(monstruo.get(i).getX(), monstruo.get(i).getY(), 39, 70, matrixScale, 9));
                    switch (monstruo.get(i).getType()) {
                        case 0:
                            money += 15;
                            break;
                        case 1:
                            money += 20;
                            break;
                        case 2:
                            money += 25;
                            break;
                        case 3:
                            money += 1000;
                            break;
                    }
                    monstruo.remove(i);

                    score += life * numWave + numWave * 5;
                    //volver
                    i--;
                }

            }
            for (int i = 0; i < balas.size(); i++) {
                if (!balas.get(i).getHit())
                    balas.get(i).update();
                else {

                    if (balas.get(i).getEffect() < 20)
                        balas.get(i).affectTarget();
                    else {
                        int radius = 1;
                        Coordinates epicenter = balas.get(i).getTarget().getActual();

                        for (int ii = 0; ii < monstruo.size(); ii++)
                            if (Math.abs(monstruo.get(ii).getActual().getX() - epicenter.getX()) <= radius && Math.abs(monstruo.get(ii).getActual().getY() - epicenter.getY()) <= radius)
                                monstruo.get(ii).giveDamage(balas.get(i).getAttack());

                    }

                    balas.remove(i);
                    i--;
                    continue;
                }
                if (secondsElapsedSince(balas.get(i).getLifetime()) > 10) {
                    balas.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < explosiones.size(); i++) {
                if (!explosiones.get(i).readyToRemove())
                    explosiones.get(i).update();
                else {
                    explosiones.remove(i);
                    i--;
                }

            }


            for (int i = monstruo.size() - 1; i >= 0; i--) {
                if (monstruo.get(i).getX() > mapa[0].length * matrixScale + matrixScale * 2.5) {
                    monstruo.remove(i);
                    life--;
// NO GAME OVER
//                    if (life == 0) {
//                        modo = 5;
//                        writeHighScore();
//                        nBotonActivado = -1;
//                        cambiarPantalla = false;
//                        nuevaPantalla(6);
//
//                    }
                }


            }

            nuevaPantalla = 0;

            if (tiempoDeCambiar != 0) {

                if (millisElapsedSince(tiempoDeCambiar) > 150) {
                    nuevaPantalla(nuevaPantalla);
                }
            }
        }
        else{
            if(!story||numWave<50) {
                int recorrer = numWave * 2 + 20;
                if (recorrer > 100)
                    recorrer = 100;
                long calc = (recorrer - secondsElapsedSince(nextWaveCountDown));
                if (calc > 0) {
                    nextWaveCountDown = nanosElapsedSince(wavebackup);
                }

            }





//volver
            if(nBotonActivado!=-1) {
                buttonback[nBotonActivado].update();
                if (buttonback[nBotonActivado].getPlayedOnce()) {
                    if (!cambiarPantalla) {
                        cambiarPantalla = true;
                        tiempoDeCambiar = System.nanoTime();
                    }

                    nuevaPantalla = nBotonActivado;

                    nBotonActivado = -1;
                }
            }
            if(tiempoDeCambiar!=0) {
                if ((millisElapsedSince(tiempoDeCambiar) > 150 )) {
                    nuevaPantalla(nuevaPantalla);
                }
            }
        }


    }


    public void nuevaPantalla(int tipo) {
        if (tipo==0) {

            startScreenCreated();
        }
        if (tipo==1) {
            gameScreenCreado();
        }
        if (tipo==2)
        {
            gameScreenCreado();
        }
        if (tipo==3) {
            htpScreenCreated();
        }
        if (tipo==4)
        {
            creditsScreenCreated();
        }
        if (tipo==5)
        {
            settingsScreenCreated();
        }
        if(tipo==6)
            gameoverScreenCreated();

    }
    public void htpScreenCreated()
    {
        modo=2;
        MOVESPEED=-5;
        WIDTH=1604;
        HEIGHT=748;
        buttonback=new TitleButtons[1];
        buttonpropre=new TitleButtons[2];
        String texto="Back";
        tiempoDeCambiar=0;
        nuevaPantalla=0;
        scaleFactorX=(getWidth())/WIDTH;
        scaleFactorY=getHeight()/HEIGHT;
        buttonback[0] = new TitleButtons(0, (int) (GamePanel.WIDTH / 2 - 250), 530, (459), 169, texto, 3);
        buttonpropre[0]=new TitleButtons(3, (int) (GamePanel.WIDTH / 2 - 370), 560, (116), 169, "", 3);
        buttonpropre[1]=new TitleButtons(4, (int) (GamePanel.WIDTH / 2 + 200), 560, (116), 169, "", 3);
        htpBitmap=new Bitmap[6];
        htpBitmap[0]=BitmapFactory.decodeResource(projectResources, R.drawable.hordas);
        htpBitmap[1]=BitmapFactory.decodeResource(projectResources, R.drawable.towers);
        htpBitmap[2]=BitmapFactory.decodeResource(projectResources, R.drawable.instruc3);
        htpBitmap[3]=BitmapFactory.decodeResource(projectResources, R.drawable.instruc2);
        htpBitmap[4]=BitmapFactory.decodeResource(projectResources, R.drawable.instruc1);
        htpBitmap[5]=BitmapFactory.decodeResource(projectResources, R.drawable.objective);


            nBotonActivado=-1;
        cambiarPantalla=false;

    }
    public void creditsScreenCreated()
    {
        modo=3;
        MOVESPEED=-5;
        WIDTH=1604;
        HEIGHT=748;
        //bg= new Background(BitmapFactory.decodeResource(projectResources, R.drawable.mountainbg2));
        bg.setSignImage(BitmapFactory.decodeResource(projectResources, R.drawable.credits));
        buttonback=new TitleButtons[1];
        String texto="Back";
        tiempoDeCambiar=0;
        nuevaPantalla=0;
        scaleFactorX=(getWidth())/WIDTH;
        scaleFactorY=getHeight()/HEIGHT;
        buttonback[0] = new TitleButtons(0, (int) (GamePanel.WIDTH / 2 - 250), 530, (459), 169, texto, 3);
        nBotonActivado=-1;
        cambiarPantalla=false;
    }
    public void settingsScreenCreated()
    {
        modo=4;
        MOVESPEED=-5;
        WIDTH=1604;
        HEIGHT=748;
        //bg= new Background(BitmapFactory.decodeResource(projectResources, R.drawable.mountainbg2));
        bg.setSignImage(BitmapFactory.decodeResource(projectResources, R.drawable.woodsign));
        String texto="Back";
        tiempoDeCambiar=0;
        nuevaPantalla=0;
        scaleFactorX=(getWidth())/WIDTH;
        scaleFactorY=getHeight()/HEIGHT;
        buttonback[0] = new TitleButtons(0, (int) (GamePanel.WIDTH / 2 - 250), (int)GamePanel.HEIGHT/2+100, (459), 169, texto, 3);
        nBotonActivado=-1;
        cambiarPantalla=false;
    }
    public void updateHTPScreen()
    {


        if(nBotonActivado==-2)
            bg.setSignImage(htpBitmap[0]);
        if(nBotonActivado==-3)
            bg.setSignImage(htpBitmap[1]);
        if(nBotonActivado==-4)
            bg.setSignImage(htpBitmap[2]);
        if(nBotonActivado==-5)
            bg.setSignImage(htpBitmap[3]);
        if(nBotonActivado==-6)
            bg.setSignImage(htpBitmap[4]);
        if(nBotonActivado==-1)
            bg.setSignImage(htpBitmap[5]);
        if(nBotonActivado>-1) {

            buttonback[nBotonActivado].update();
            if (buttonback[nBotonActivado].getPlayedOnce()) {
                if (!cambiarPantalla) {
                    cambiarPantalla = true;
                    tiempoDeCambiar = System.nanoTime();
                }

                nuevaPantalla = nBotonActivado;

                nBotonActivado = -1;
            }
        }
        if(tiempoDeCambiar!=0) {
            if ((millisElapsedSince(tiempoDeCambiar) > 150 )) {
                nuevaPantalla(nuevaPantalla);
            }
        }

    }
    public void updateCreditsScreen()
    {

        if(nBotonActivado!=-1) {


            buttonback[nBotonActivado].update();
            if (buttonback[nBotonActivado].getPlayedOnce()) {
                if (!cambiarPantalla) {
                    cambiarPantalla = true;
                    tiempoDeCambiar = System.nanoTime();
                }

                nuevaPantalla = nBotonActivado;

                nBotonActivado = -1;

            }
        }
        if(tiempoDeCambiar!=0) {
            if ((millisElapsedSince(tiempoDeCambiar) > 150 )) {
                nuevaPantalla(nuevaPantalla);
            }
        }

    }
    public void updateSettingsScreen()
    {

        if(nBotonActivado!=-1) {


            buttonback[nBotonActivado].update();
            if (buttonback[nBotonActivado].getPlayedOnce()) {
                if (!cambiarPantalla) {
                    cambiarPantalla = true;
                    tiempoDeCambiar = System.nanoTime();
                }

                nuevaPantalla = nBotonActivado;

                nBotonActivado = -1;

            }
        }
        if(tiempoDeCambiar!=0) {
            if ((millisElapsedSince(tiempoDeCambiar) > 150 )) {
                nuevaPantalla(nuevaPantalla);
            }
        }

    }

    public void drawHTPScreen(Canvas canvas)
    {
        bg.drawSign(canvas);
        bg.drawTitle(canvas);
        buttonback[0].draw(canvas);
        buttonpropre[0].draw(canvas);
        buttonpropre[1].draw(canvas);
    }
    public void drawCreditsScreen(Canvas canvas)
    {
        bg.drawSign(canvas);
        bg.drawTitle(canvas);
        buttonback[0].draw(canvas);
    }
    public void drawSettingsScreen(Canvas canvas)
    {
        bg.drawSign(canvas);
        bg.drawTitle(canvas);
        buttonback[0].draw(canvas);
    }
    public void updateGameOverScreen() {

        if (nBotonActivado != -1) {


            buttonback[nBotonActivado].update();
            if (buttonback[nBotonActivado].getPlayedOnce()) {
                if (!cambiarPantalla) {
                    cambiarPantalla = true;
                    tiempoDeCambiar = System.nanoTime();
                }

                nuevaPantalla = nBotonActivado;

                nBotonActivado = -1;

            }
        }
        if (tiempoDeCambiar != 0) {
            if ((millisElapsedSince(tiempoDeCambiar) > 150)) {
                nuevaPantalla(nuevaPantalla);
            }
        }
    }

    public void gameoverScreenCreated() {
        modo=5;
        MOVESPEED=0;
        WIDTH=1604;
        HEIGHT=748;
        bg= new Background(BitmapFactory.decodeResource(projectResources, R.drawable.gamebackgroundwoods));
        bg.setSignImage(BitmapFactory.decodeResource(projectResources, R.drawable.gameover   ));
        String texto="Back";
        tiempoDeCambiar=0;
        nuevaPantalla=0;
        scaleFactorX=(getWidth())/WIDTH;
        scaleFactorY=getHeight()/HEIGHT;
        buttonback[0] = new TitleButtons(0, (int) (GamePanel.WIDTH / 2 - 250), 530, (459), 169, texto, 3);
        nBotonActivado=-1;
        cambiarPantalla=false;
    }
    public void drawGameOverScreen(Canvas canvas)
    {
        bg.drawSign(canvas);
        bg.drawTitle(canvas);
        buttonback[0].draw(canvas);
    }

    public void writeHighScore() {
        String filename = "HighScore";
        FileOutputStream outputStream;
        try {
            outputStream = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(("" + best).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void loadHighScore()
    {
        int ch;
        StringBuffer fileContent = new StringBuffer("");
        FileInputStream fis;
        try {
            fis = getContext().openFileInput( "HighScore");

            while( (ch = fis.read()) != -1)
                fileContent.append((char)ch);

        } catch (Exception e) {
            e.printStackTrace();
        }

        best =Integer.parseInt( new String(fileContent));

    }
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

}
