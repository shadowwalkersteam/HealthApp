package com.example.zohai.healthapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zohai.Fragments.Home;
import com.example.zohai.Fragments.Monitor;
import com.example.zohai.Fragments.Profile;
import com.example.zohai.Fragments.Records;

public class Dashboard extends AppCompatActivity {

    private TextView mTextMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
//                        mTextMessage.setText(R.string.title_home);
                        selectedFragment = Home.newInstance();
                        return true;
                    case R.id.navigation_monitor:
//                        mTextMessage.setText(R.string.title_monitor);
                        selectedFragment = Monitor.newInstance();
                        return true;
                    case R.id.navigation_records:
//                        mTextMessage.setText(R.string.title_records);
                        selectedFragment = Records.newInstance();
                        return true;
                    case R.id.navigation_profile:
//                        mTextMessage.setText(R.string.title_profile);
                        selectedFragment = Profile.newInstance();
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, selectedFragment);
                transaction.commit();
                return true;
            }

        };

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, Home.newInstance());
        transaction.commit();
    }

}
