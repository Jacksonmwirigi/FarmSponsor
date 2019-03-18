package com.techweezy.mobifarm.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.techweezy.mobifarm.R;
import com.techweezy.mobifarm.db.DbHelper;

public class LogOut extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_log_out);
        FrameLayout contentWindow=(FrameLayout)findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_log_out,contentWindow);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(6).setChecked(true);

        SharedPreferences sharedPreferences=getSharedPreferences("userData",MODE_PRIVATE);

        DbHelper dbHelper=new DbHelper(this);
        dbHelper.close();
        detachLoggedSessions();

    }
    public  void detachLoggedSessions(){
        startActivity(new Intent(this,LoginScreen.class));
    }
}
