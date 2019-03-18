package com.techweezy.mobifarm.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.techweezy.mobifarm.R;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    View navHead;
    TextView userMail;
    String user_role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navHead=navigationView.getHeaderView(0);
        userMail=(TextView)navHead.findViewById(R.id.navEmail);

        SharedPreferences sharedPreferences= getSharedPreferences("userData",MODE_PRIVATE);
        String my_email =sharedPreferences.getString("my_email","sample@sample.com");
        userMail.setText(my_email);

        user_role=sharedPreferences.getString("user_role","");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.details) {

            if (user_role.equalsIgnoreCase("farmer") ){
                startActivity(new Intent(getApplicationContext(), CheckDetails.class));
            }
            else {
                Toast.makeText(this, "You Must Be a Farmer to View", Toast.LENGTH_LONG).show();
            }

            // Handle the camera action
        } else if (id == R.id.register) {
            startActivity(new Intent(getApplicationContext(),RegisterScreen.class));

        } else if (id == R.id.requests) {
            if (user_role.equalsIgnoreCase("donor") ){
                startActivity(new Intent(getApplicationContext(), ViewRequests.class));
            }
            else {
                Toast.makeText(this, "You Must Be a Donor to View", Toast.LENGTH_LONG).show();
            }


        } else if (id == R.id.approve) {

        } else if (id == R.id.home) {
            startActivity(new Intent(getApplicationContext(),HomeScreen.class));

        } else if (id == R.id.log_outLink) {

            startActivity(new Intent(this,LogOut.class));

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
