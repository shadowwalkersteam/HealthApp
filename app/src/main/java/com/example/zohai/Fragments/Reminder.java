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

/**
 * A simple {@link Fragment} subclass.
 */
public class Reminder extends Fragment {
    EditText feet,inches;
    TextView results,condition;
    Button male,female;
    double num1,num2,num3,num4,sum1,sum2,sum,h1,h2,h3,h4;

    public static Reminder newInstance()
    {
        Reminder fragment = new Reminder();
        return fragment;
    }


    public Reminder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        feet = (EditText) view.findViewById(R.id.ft);
        inches = (EditText) view.findViewById(R.id.in);
        results = (TextView) view.findViewById(R.id.result);
        condition = (TextView) view.findViewById(R.id.status);
        male = (Button) view.findViewById(R.id.male);
        female = (Button) view.findViewById(R.id.female);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= feet.getText().toString();
                String s2= inches.getText().toString();
                if(s1.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }else  if(s2.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    num1 = Double.parseDouble(s1);
                    num3 = Double.parseDouble(s2);
                    //height ft to cm / 0.032808
                    //height in to cm / 0.39370

                    h1=num1/0.032808;
                    h3=(num3/0.39370)+h1;
                    sum1= h3-100;
                    sum2=(h3-100)*0.1;
                    sum=sum1-sum2;

                    results.setText(Double.toString(sum));
                }
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= feet.getText().toString();
                String s2= inches.getText().toString();
                if(s1.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }else  if(s2.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    num2 = Double.parseDouble(s1);
                    num4 = Double.parseDouble(s2);

                    //height ft to cm / 0.032808
                    //height in to cm / 0.39370
                    h2=num2/0.032808;
                    h4=(num4/0.39370)+h2;
                    sum1= h4-100;
                    sum2=(h4-100)*0.15;
                    sum=sum1-sum2;

                    results.setText(Double.toString(sum));
                }
            }
        });

        return view;
    }

}
