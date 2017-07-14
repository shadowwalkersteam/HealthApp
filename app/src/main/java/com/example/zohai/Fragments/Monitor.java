package com.example.zohai.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zohai.healthapp.Dashboard2;
import com.example.zohai.healthapp.DatasourceID;
import com.example.zohai.healthapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Monitor extends Fragment {
    SharedPreferences sharedPreferences;
    String Datasource;
    private TextView heart_rate;
    private TextView blood_pressure;
    private TextView temperature;
    public static Monitor newInstance()
    {
        Monitor fragment = new Monitor();
        return fragment;
    }


    public Monitor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        Datasource = sharedPreferences.getString("DataID",null);
//        Datasource = getActivity().getIntent().getStringExtra("hello");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor, container, false);
        heart_rate = (TextView) view.findViewById(R.id.heart);
        blood_pressure = (TextView) view.findViewById(R.id.blood);
        temperature = (TextView) view. findViewById(R.id.temperature);
        // Inflate the layout for this fragment
        return view;
    }

}
