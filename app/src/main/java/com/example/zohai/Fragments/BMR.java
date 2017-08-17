package com.example.zohai.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.system.StructPollfd;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zohai.healthapp.R;

public class BMR extends Fragment {
    EditText feet,inches,kg,age;
    TextView results;
    Button calcMale, calcFemale;
    double num1,num2,num3,num4,sum;

    public static BMR newInstance()
    {
        BMR fragment = new BMR();
        return fragment;
    }


    public BMR() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bmr, container, false);
        feet = (EditText) view.findViewById(R.id.ft);
        inches = (EditText) view.findViewById(R.id.in);
        kg = (EditText) view.findViewById(R.id.kg);
        age = (EditText) view.findViewById(R.id.age);
        results = (TextView) view.findViewById(R.id.result);
        calcMale = (Button) view.findViewById(R.id.male);
        calcFemale = (Button) view.findViewById(R.id.female);

        calcMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= feet.getText().toString();
                String s2= inches.getText().toString();
                String s3= kg.getText().toString();
                String s4= age.getText().toString();
                if(s1.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }else  if(s2.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }else  if(s3.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }else  if(s4.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    num1 = Double.parseDouble(s1);
                    num2 = Double.parseDouble(s2);
                    num3 = Double.parseDouble(s3);
                    num4 = Double.parseDouble(s4);

//                    convert feet to cms
                    double cm1 = num1*30.48;
//                    convert inches to cm
                    double cm2 = num2*2.54;
                    double heightCM = cm1 + cm2;
//                    BMR formula for male
                    sum = 66.47+(13.7 * num3) + (5.0 * heightCM) - (6.8 * num4);
                    results.setText(Double.toString(sum));
                }
            }
        });

        calcFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= feet.getText().toString();
                String s2= inches.getText().toString();
                String s3= kg.getText().toString();
                String s4= age.getText().toString();
                if(s1.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }else  if(s2.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }else  if(s3.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }else  if(s4.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    num1 = Double.parseDouble(s1);
                    num2 = Double.parseDouble(s2);
                    num3 = Double.parseDouble(s3);
                    num4 = Double.parseDouble(s4);

//                    convert feet to cms
                    double cm1 = num1*30.48;
//                    convert inches to cm
                    double cm2 = num2*2.54;
                    double heightCM = cm1 + cm2;
//                    BMR formula for female
                    sum = 655.1+(9.6 * num3) + (1.8 * heightCM) - (4.7 * num4);
                    results.setText(Double.toString(sum));
                }
            }
        });

        return view;
    }

}
