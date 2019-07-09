package com.example.max.medievaltd;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    GamePanel gp;
    MediaPlayer mp;
    private RetainedFragment dataFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //fullscreen
        if(gp==null) {
            gp = new GamePanel(this);

            setContentView(gp);
        }
        FragmentManager fm=getFragmentManager();
        dataFragment=(RetainedFragment) fm.findFragmentByTag("data");
        if(dataFragment==null)
        {
            dataFragment=new RetainedFragment();
            fm.beginTransaction().add(dataFragment,"data").commit();
            dataFragment.setData(gp);
        }
        else
            gp=dataFragment.getData();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.LAYOUT_CHANGED);

        mp= MediaPlayer.create(this, R.raw.medievaltdsong);
        mp.setLooping(true);
    }


    @Override
    public void onDestroy() {
        if(gp!=null)
        dataFragment.setData(gp);
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
    public static Activity getActivity()
    {
        return getActivity();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //gp.musicPause();
        mp.pause();
        if(gp!=null)
        dataFragment.setData(gp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(dataFragment.getData()!=null)
            gp=dataFragment.getData();
        //gp.musicResume();
        mp.start();
    }
}
