package com.example.max.medievaltd.core;


import android.app.Fragment;
import android.os.Bundle;

import com.example.max.medievaltd.game.GamePanel;


/**
 * Created by Maye on 12/3/2015.
 */

public class RetainedFragment extends Fragment {

    private GamePanel data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setData(GamePanel gp) {
        this.data = gp;
    }

    public GamePanel getData() {
        return data;
    }
}

