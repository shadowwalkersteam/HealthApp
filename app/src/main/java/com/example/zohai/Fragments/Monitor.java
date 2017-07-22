package com.example.zohai.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public int maxHeart = 130;
    public int minHeart = 50;
    public int maxBlood = 130;
    public int minBlood = 75;
    public int maxTemp = 101;
    public int minTemp = 97;
    SharedPreferences shared2;

    public int Heart1 ;
    public int Heart2 ;
    public int Blood1 ;
    public int Blood2;
    public int Temp1 ;
    public int Temp2 ;

    String shared_mobile1;
    String shared_mobile2;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 111;

    private Timer timer = new Timer();
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

        shared2 = getActivity().getSharedPreferences("HealthValues",Context.MODE_PRIVATE);
        Heart1 = Integer.parseInt(shared2.getString("maxheart", String.valueOf(0)));
        Heart2 = Integer.parseInt(shared2.getString("minheart", String.valueOf(0)));
        Blood1 = Integer.parseInt(shared2.getString("maxblood", String.valueOf(0)));
        Blood2 = Integer.parseInt(shared2.getString("minblood", String.valueOf(0)));
        Temp1 = Integer.parseInt(shared2.getString("maxtemp", String.valueOf(0)));
        Temp2 = Integer.parseInt(shared2.getString("mintemp", String.valueOf(0)));

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
            Toast.makeText(getActivity(), "Good! Connected to Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_monitor, container, false);
        heart_rate = (TextView) view.findViewById(R.id.heart);
        blood_pressure = (TextView) view.findViewById(R.id.blood);
        temperature = (TextView) view.findViewById(R.id.temp);

        callAsynchronousTaskTemp();
        callAsynchronousTaskHeart();
        callAsynchronousTaskBlood();

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
        timer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        Myapplication.getInstance().setConnectivityListener(this);
        checkMobile();
    }
    private void checkMobile()
    {
        if(shared_mobile1 == null && shared_mobile2 == null)
        {
            Toast.makeText(getActivity(),"Please enter emergency numbers",Toast.LENGTH_LONG).show();
        }
        else {

            checkValues();
        }
    }

    private void checkValues()
    {
        if (Heart1 != 0 && Heart2 != 0 && Blood1 != 0 && Blood2 != 0 && Temp1 != 0 && Temp2 != 0)
        {
            maxHeart = Heart1;
            maxBlood = Blood1;
            maxTemp = Temp1;
            minHeart = Heart2;
            minBlood = Blood2;
            minTemp = Temp2;

            SMSThread();
        }
        else
        {
            SMSThread();
        }
    }

    private void SMSThread() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(heartdata > maxHeart || heartdata < minHeart )
                {
                    sendSMSMessage();
                }
                else if (bloodata > maxBlood || bloodata < minBlood)
                {
                    sendSMSMessage();
                }
                else if(tempdata > maxTemp || tempdata < minTemp)
                {
                    sendSMSMessage();
                }
                else {
                    Toast.makeText(getActivity(),"Your health is good according to threshold values",Toast.LENGTH_SHORT);
                }
            }
        },300 * 1000);
    }

    private void callAsynchronousTaskTemp() {
        final Handler handler = new Handler();
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
                    Manifest.permission.SEND_SMS))
            {

            }
            else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
                verifyingPermission();

            }
        }
        else
        {
            sendSMS();
        }
    }

    private void verifyingPermission()
    {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {

        }
        else {
            sendSMS();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS();
                }
            }
        }

    }
    private void sendSMS()
    {
        SmsManager sms = SmsManager.getDefault();
        String msg = "Patient needs Attention!! Heart Rate = "+heartdata+" bpm, \nBlood Pressure = "+bloodata+" mmHg, \nBody Temperature = "+tempdata+" F";
        ArrayList<String> BodyParts = sms.divideMessage(msg);
        sms.sendTextMessage(shared_mobile1, null, String.valueOf(BodyParts), null, null);
        sms.sendTextMessage(shared_mobile2,null, String.valueOf(BodyParts),null,null);
    }
}
