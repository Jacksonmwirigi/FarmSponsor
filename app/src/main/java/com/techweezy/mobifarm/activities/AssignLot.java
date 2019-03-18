package com.techweezy.mobifarm.activities;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.techweezy.mobifarm.R;

public class AssignLot extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_assign_lot);
        FrameLayout contentLayout=(FrameLayout)findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_assign_lot,contentLayout);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(5).setChecked(true);



    }
}
