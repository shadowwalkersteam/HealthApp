package com.example.zohai.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zohai.healthapp.ConnectivityReceiver;
import com.example.zohai.healthapp.Myapplication;
import com.example.zohai.healthapp.R;
import com.example.zohai.healthapp.UbidotsClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Monitor extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {
    SharedPreferences sharedPreferences;
    String Datasource;
    public TextView heart_rate;
    public TextView blood_pressure;
    public TextView temperature;

    public int heartdata;
    public int bloodata;
    public int tempdata;


    String shared_mobile1;
    String shared_mobile2;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    private Timer timer;
    private TimerTask doAsynchronousTask;

    private String API_KEY = "9XWFrsh83kQWtNvBB6oU1F6zufzS8B";

    private String heartVarId = "heart-rate";
    private String bloodVarId = "blood-pressure";
    private String tempVarId = "temperature";


    public static Monitor newInstance() {
        Monitor fragment = new Monitor();
        return fragment;
    }


    public Monitor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Datasource = sharedPreferences.getString("DataID", null);

        SharedPreferences sp = getActivity().getSharedPreferences("myContact",Context.MODE_PRIVATE);
        shared_mobile1 = sp.getString("phone1",null);
        shared_mobile2 = sp.getString("phone2",null);

        checkConnection();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showToast(isConnected);
    }

    private void showToast(boolean isConnected) {
        if (!isConnected) {
            Toast.makeText(getActivity(), "Sorry! Not connected to internet", Toast.LENGTH_LONG).show();

        } else
            Toast.makeText(getActivity(), "Good! Connected to Internet", Toast.LENGTH_LONG).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor, container, false);
        heart_rate = (TextView) view.findViewById(R.id.heart);
        blood_pressure = (TextView) view.findViewById(R.id.blood);
        temperature = (TextView) view.findViewById(R.id.temp);

        callAsynchronousTaskHeart();
        callAsynchronousTaskBlood();
        callAsynchronousTaskTemp();

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Myapplication.getInstance().setConnectivityListener(this);

    }

    private void callAsynchronousTaskTemp() {
        final Handler handler = new Handler();
        timer = new Timer();
        doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                (new UbidotsClient()).handleUbidots(Datasource, tempVarId, API_KEY, new UbidotsClient.UbiListener() {
                    @Override
                    public void onDataReady(List<UbidotsClient.Value> result) {
                        for (int i = 0; i < result.size(); i++) {
                            float obj = result.get(i).value;
                            tempdata = Math.round(obj);
                        }
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        temperature.setText(String.valueOf(tempdata));
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000);

    }

    private void callAsynchronousTaskBlood() {
        final Handler handler = new Handler();
        timer = new Timer();
        doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                (new UbidotsClient()).handleUbidots(Datasource, bloodVarId, API_KEY, new UbidotsClient.UbiListener() {
                    @Override
                    public void onDataReady(List<UbidotsClient.Value> result) {
                        for (int i = 0; i < result.size(); i++) {
                            float obj = result.get(i).value;
                            bloodata = Math.round(obj);
                        }
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        blood_pressure.setText(String.valueOf(bloodata));
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000);
    }

    public void callAsynchronousTaskHeart() {
        final Handler handler = new Handler();
        timer = new Timer();
        doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                (new UbidotsClient()).handleUbidots(Datasource, heartVarId, API_KEY, new UbidotsClient.UbiListener() {
                    @Override
                    public void onDataReady(List<UbidotsClient.Value> result) {
                        for (int i = 0; i < result.size(); i++) {
                            float obj = result.get(i).value;
                            heartdata = Math.round(obj);
                        }
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        heart_rate.setText(String.valueOf(heartdata));
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToast(isConnected);
    }

    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        if (heartdata >= 110 || bloodata >= 125 || tempdata >= 101) {
            SmsManager sms = SmsManager.getDefault();
            String msg = "Patient needs Attention!! Heart Rate = "+heartdata+" bpm, \nBlood Pressure = "+bloodata+" mmHg, \nBody Temperature = "+tempdata+" F";
            ArrayList<String> BodyParts = sms.divideMessage(msg);
            sms.sendTextMessage(shared_mobile1, null, String.valueOf(BodyParts), null, null);
            sms.sendTextMessage(shared_mobile2,null, String.valueOf(BodyParts),null,null);
        }
    }
}
