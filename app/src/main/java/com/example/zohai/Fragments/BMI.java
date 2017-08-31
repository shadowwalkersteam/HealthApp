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

public class BMI extends Fragment {
    EditText feet,inches,kg;
    TextView results,condition;
    Button calculate;
    double num1,num2,num3,sum;
    public static BMI newInstance()
    {
        BMI fragment = new BMI();
        return fragment;
    }


    public BMI() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);
        feet = (EditText) view.findViewById(R.id.ft);
        inches = (EditText) view.findViewById(R.id.in);
        kg = (EditText) view.findViewById(R.id.kg);
        results = (TextView) view.findViewById(R.id.result);
        condition = (TextView) view.findViewById(R.id.status);
        calculate = (Button) view.findViewById(R.id.bmicalc);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= feet.getText().toString();
                String s2= inches.getText().toString();
                String s3= kg.getText().toString();
                if(s1.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }else  if(s2.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }else  if(s3.equalsIgnoreCase("") ){
                    Toast.makeText(getActivity(), "Fill All Entries", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    num1 = Double.parseDouble(s1);
                    num2 = Double.parseDouble(s2);
                    num3 = Double.parseDouble(s3);


//                    convert feet to meters
                    double meter1 = num1*0.3048;
//                    convert inches to meters
                    double meter2 = num2*0.0254;
                    double sum1 = (meter1 + meter2);
//                    calculate bmi
                    sum = num3/(sum1*sum1);

                    results.setText(Double.toString(sum));

                    if(sum >= 18.5 && sum  <=   25)
                    {
                        condition.setText("Normal (healthy weight)");
                    }
                    else if (sum >= 16 && sum <=18.5 )
                    {
                        condition.setText("Underweight");
                    }
                    else if (sum <= 15)
                    {
                        condition.setText("Severely underweight");
                    }
                    else if (sum >=25 && sum <= 30)
                    {
                        condition.setText("Overweight");
                    }
                    else if (sum >= 30)
                    {
                        condition.setText("Severely Overweight");
                    }
                }
            }
        });

        return view;
    }

}
