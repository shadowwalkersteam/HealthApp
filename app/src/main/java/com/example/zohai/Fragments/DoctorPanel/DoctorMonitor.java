package com.example.zohai.Fragments.DoctorPanel;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zohai.Fragments.Monitor;
import com.example.zohai.healthapp.ConnectivityReceiver;
import com.example.zohai.healthapp.DoctorPanel.UbidotsClientDoctor;
import com.example.zohai.healthapp.Myapplication;
import com.example.zohai.healthapp.R;
import com.example.zohai.healthapp.UbidotsClient;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DoctorMonitor extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {

    SharedPreferences sharedPreferences;
    String Datasource;
    public TextView heart_rate;
    public TextView blood_pressure;
    public TextView temperature;

    public int heartdata;
    public int bloodata;
    public int tempdata;

    private Timer timer = new Timer();
    private TimerTask doAsynchronousTask;

    private String API_KEY = "9XWFrsh83kQWtNvBB6oU1F6zufzS8B";

    private String heartVarId = "heartrate";
    private String bloodVarId = "bloodpressure";
    private String tempVarId = "temperature";

    public static DoctorMonitor newInstance() {
        DoctorMonitor fragment = new DoctorMonitor();
        return fragment;
    }

    public DoctorMonitor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("Doctor", Context.MODE_PRIVATE);
        Datasource = sharedPreferences.getString("SecretID", null);

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_monitor, container, false);

            heart_rate = (TextView) view.findViewById(R.id.heart);
            blood_pressure = (TextView) view.findViewById(R.id.blood);
            temperature = (TextView) view.findViewById(R.id.temp);

            callAsynchronousTaskTemp();
            callAsynchronousTaskHeart();
            callAsynchronousTaskBlood();

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
    }

    private void callAsynchronousTaskTemp() {
        final Handler handler = new Handler();
        doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                (new UbidotsClientDoctor()).handleUbidots(Datasource, tempVarId, API_KEY, new UbidotsClientDoctor.UbiListenerDoctor() {
                    @Override
                    public void onDataReadyDoctor(List<UbidotsClientDoctor.Value> result) {
                        for (int i = 0; i < result.size(); i++) {
                            float obj = result.get(0).value;
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
                (new UbidotsClientDoctor()).handleUbidots(Datasource, bloodVarId, API_KEY, new UbidotsClientDoctor.UbiListenerDoctor() {
                    @Override
                    public void onDataReadyDoctor(List<UbidotsClientDoctor.Value> result) {
                        for (int i = 0; i < result.size(); i++) {
                            float obj = result.get(0).value;
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
                (new UbidotsClientDoctor()).handleUbidots(Datasource, heartVarId, API_KEY, new UbidotsClientDoctor.UbiListenerDoctor() {
                    @Override
                    public void onDataReadyDoctor(List<UbidotsClientDoctor.Value> result) {
                        for (int i = 0; i < result.size(); i++) {
                            float obj = result.get(0).value;
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
}
