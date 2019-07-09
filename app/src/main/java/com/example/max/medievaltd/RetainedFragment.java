package com.example.max.medievaltd;


import android.app.Fragment;
import android.os.Bundle;


/**
 * Created by Maye on 12/3/2015.
 */

public class RetainedFragment extends Fragment {

    // data object we want to retain
    private GamePanel data;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(GamePanel gp) {
        this.data = gp;
    }

    public GamePanel getData() {
        return data;
    }
}

