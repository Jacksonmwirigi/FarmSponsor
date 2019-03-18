package com.techweezy.mobifarm.activities;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.techweezy.mobifarm.R;

public class Aprv extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_apv);
        FrameLayout contentFrame= (FrameLayout)findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_apv,contentFrame);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(3).setChecked(true);

    }
}
