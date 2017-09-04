package com.example.zohai.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zohai.healthapp.R;

public class WaterNeeds extends Fragment {

    EditText minutes;
    TextView results,condition;
    Button calculate;

    double num,num1,num2,num3;

    public static WaterNeeds newInstance()
    {
        WaterNeeds fragment = new WaterNeeds();
        return fragment;
    }

    public WaterNeeds() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_water_needs, container, false);
        minutes = (EditText) view.findViewById(R.id.mint);
        results = (TextView) view.findViewById(R.id.result);
        condition = (TextView) view.findViewById(R.id.status);
        calculate = (Button) view.findViewById(R.id.watercalc);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= minutes.getText().toString();
                if(s1.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }else{
                    num1 = Double.parseDouble(s1);

                    // water need in ounces {(activity/30)*12}+100
                    // ounces to liters = oz/33.814

                    num2=(num1/30)*12;
                    num3=100+num2;
                    num=num3/33.814;


                    results.setText(Double.toString(num));
            }
        }


    });
        return view;
}
}
