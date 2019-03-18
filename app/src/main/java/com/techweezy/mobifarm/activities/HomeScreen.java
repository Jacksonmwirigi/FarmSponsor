package com.techweezy.mobifarm.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techweezy.mobifarm.R;

public class HomeScreen extends MainActivity {
    TextView textLInk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_screen);
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_home_screen,frameLayout);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(4).setChecked(true);

        textLInk=(TextView)findViewById(R.id.seekFundLInk);

        SharedPreferences sharedPreferences= getSharedPreferences("userData",MODE_PRIVATE);
        user_role=sharedPreferences.getString("user_role","");

    }

    public  void onTextLinkSelected(View view){
        switch (view.getId()){
            case R.id.seekFundLInk:
                if (user_role.equalsIgnoreCase("Farmer")){
                    startActivity(new Intent(getApplicationContext(),CheckDetails.class));

                }
                break;

        }

    }
}
