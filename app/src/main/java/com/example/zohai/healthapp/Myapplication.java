package com.example.zohai.healthapp;


import android.app.Application;

public class Myapplication extends Application {

    private static Myapplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized Myapplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
