package com.example.zohai.healthapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OfflineOnline extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private TextView Online;
    private TextView Offline;

    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_online);

        Online = (TextView) findViewById(R.id.online);
        Offline = (TextView) findViewById(R.id.offline);

        // Manually checking internet connection
        checkConnection();

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
                Intent it = new Intent(OfflineOnline.this,BluetoothDeviceList.class);
                startActivity(it);
            }
        });

    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showToast(isConnected);
    }

    // Showing the status in Snackbar
    private void showToast(boolean isConnected) {
        String message = null;
        int color = 0;
        if (!isConnected) {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Good! Connected to Internet",Toast.LENGTH_LONG).show();
        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.online),message,Snackbar.LENGTH_INDEFINITE);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.DarkColor));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

    // register connection status listener
        Myapplication.getInstance().setConnectivityListener(this);
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

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToast(isConnected);
    }
}
