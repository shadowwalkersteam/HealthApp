     package com.example.zohai.healthapp.DoctorPanel;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.zohai.Fragments.DoctorPanel.DoctorMonitor;
import com.example.zohai.Fragments.DoctorPanel.DoctorProfile;
import com.example.zohai.Fragments.DoctorPanel.DoctorRecord;
import com.example.zohai.Fragments.DoctorPanel.IDmonitor;
import com.example.zohai.Fragments.DoctorPanel.IDprofile;
import com.example.zohai.Fragments.DoctorPanel.IDrecord;
import com.example.zohai.healthapp.R;

public class IdDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_monitor:
                        selectedFragment = IDmonitor.newInstance();
                        break;
                    case R.id.navigation_records:
                        selectedFragment = IDrecord.newInstance();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = IDprofile.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, selectedFragment);
                transaction.commit();
                return true;
            }
        };
        BottomNavigationView btmnavigation = (BottomNavigationView) findViewById(R.id.navigation2);
        btmnavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, IDmonitor.newInstance());
        transaction.commit();
    }
}
