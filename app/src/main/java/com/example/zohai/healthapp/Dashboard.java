package com.example.zohai.healthapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zohai.Fragments.Home;
import com.example.zohai.Fragments.Monitor;
import com.example.zohai.Fragments.Profile;
import com.example.zohai.Fragments.Records;

public class Dashboard extends AppCompatActivity {

    private TextView mTextMessage;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        initToolBar();


        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = Home.newInstance();
                        break;
                    case R.id.navigation_monitor:
                         selectedFragment = Monitor.newInstance();
                        break;
                    case R.id.navigation_records:
                        selectedFragment = Records.newInstance();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = Profile.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, selectedFragment);
                transaction.commit();
                return true;
            }

        };
        BottomNavigationView btmnavigation = (BottomNavigationView) findViewById(R.id.navigation);
        btmnavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        btmnavigation.setTranslucentNavigationEnabled(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, Home.newInstance());
        transaction.commit();
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(R.string.tool_bar_title);
        setSupportActionBar(toolbar);
    }

}
