package com.example.zohai.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zohai.healthapp.R;

public class Settings extends Fragment {
    private Button saveValues;
    private EditText maxHert;
    private EditText minhert;
    private EditText maxbld;
    private EditText minbld;
    private EditText maxtmp;
    private EditText mintmp;

    public String maxHeart;
    public String minHeart;
    public String maxBlood;
    public String minBlood;
    public String maxTemp;
    public String minTemp;

    public String maxHeart1;
    public String minHeart2;
    public String maxBlood3;
    public String minBlood4;
    public String maxTemp5;
    public String minTemp6;

    SharedPreferences sharedPreferences;

    public static Settings newInstance()
    {
        Settings fragment = new Settings();
        return fragment;
    }


    public Settings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("HealthValues", Context.MODE_PRIVATE);
        maxHeart1 = sharedPreferences.getString("maxheart", null);
        maxBlood3 = sharedPreferences.getString("maxblood",null);
        maxTemp5 = sharedPreferences.getString("maxtemp",null);
        minHeart2 = sharedPreferences.getString("minheart",null);
        minBlood4 = sharedPreferences.getString("minblood", null);
        minTemp6 = sharedPreferences.getString("mintemp", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        maxHert = (EditText) view.findViewById(R.id.maxheart);
        minhert = (EditText) view.findViewById(R.id.minheart);
        maxbld = (EditText) view.findViewById(R.id.maxblood);
        minbld = (EditText) view.findViewById(R.id.minblood);
        maxtmp = (EditText) view.findViewById(R.id.maxtemp);
        mintmp = (EditText) view.findViewById(R.id.mintemp);
        saveValues = (Button) view.findViewById(R.id.setvalue);

        maxHert.setText(maxHeart1);
        maxbld.setText(maxBlood3);
        maxtmp.setText(maxTemp5);
        minhert.setText(minHeart2);
        minbld.setText(minBlood4);
        mintmp.setText(minTemp6);

        saveValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maxHeart = maxHert.getText().toString();
                minHeart = minhert.getText().toString();
                maxBlood = maxbld.getText().toString();
                minBlood = minbld.getText().toString();
                maxTemp =  maxtmp.getText().toString();
                minTemp =  mintmp.getText().toString();

                SharedPreferences sp = getActivity().getSharedPreferences("HealthValues",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("maxheart", maxHeart);
                editor.putString("maxblood", maxBlood);
                editor.putString("maxtemp", maxTemp);
                editor.putString("minheart", minHeart);
                editor.putString("minblood", minBlood);
                editor.putString("mintemp", minTemp);
                editor.commit();

                maxHert.setEnabled(false);
                minhert.setEnabled(false);
                maxbld.setEnabled(false);
                minbld.setEnabled(false);
                maxtmp.setEnabled(false);
                mintmp.setEnabled(false);
            }
        });

        return view;
    }

}
