package com.example.max.medievaltd.core;

import android.app.Activity;
import android.app.FragmentManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.example.max.medievaltd.R;
import com.example.max.medievaltd.game.GamePanel;

public class MainActivity extends Activity {

    private GamePanel gp;
    private MediaPlayer mp;
    private RetainedFragment dataFragment;

    private static final String DATA_FRAGMENT_TAG = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        createGamePanel();
        createFragmentManager();
        windowLayoutConfiguration();
        mediaPlayerConfiguration();
    }

    @Override
    public void onDestroy() {

        if (gp != null) {
            dataFragment.setData(gp);
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static Activity getActivity() {
        return getActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {

        super.onPause();
        mp.pause();
        if (gp != null) {
            dataFragment.setData(gp);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (dataFragment != null) {
            resumeFromFragment();
        }
        mp.start();
    }

    private void createGamePanel() {

        if (gp == null) {
            gp = new GamePanel(this);
            setContentView(gp);
        }
    }

    private void createFragmentManager() {

        final FragmentManager fm = getFragmentManager();
        dataFragment = (RetainedFragment) fm.findFragmentByTag(DATA_FRAGMENT_TAG);

        if (dataFragment == null) {
            dataFragment = new RetainedFragment();
            fm.beginTransaction()
                    .add(dataFragment, DATA_FRAGMENT_TAG)
                    .commit();
            dataFragment.setData(gp);
        } else {
            resumeFromFragment();
        }

    }

    private void resumeFromFragment() {
        gp = dataFragment.getData();
    }

    private void windowLayoutConfiguration() {

        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(LayoutParams.LAYOUT_CHANGED);
    }

    private void mediaPlayerConfiguration() {

        mp = MediaPlayer.create(this, R.raw.medievaltdsong);
        mp.setLooping(true);
    }
}
