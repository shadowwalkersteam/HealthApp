package com.example.zohai.healthapp;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OfflineOnline extends AppCompatActivity {

    private TextView Online;
    private TextView Offline;

    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_online);

        Online = (TextView) findViewById(R.id.online);
        Offline = (TextView) findViewById(R.id.offline);

        Online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(OfflineOnline.this,DoctorPatient.class);
                startActivity(it);
            }
        });

        Offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(OfflineOnline.this,OfflineMode.class);
                startActivity(it);
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (exit)
        {
            this.finishAffinity();

        }
        else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }
}
