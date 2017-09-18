package com.example.zohai.healthapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zohai.healthapp.DoctorPanel.AddByOptions;
import com.example.zohai.healthapp.DoctorPanel.AddPatient;

public class DoctorPatient extends AppCompatActivity {
    TextView doctor;
    TextView patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient);
        doctor = (TextView)findViewById(R.id.doc);
        patient = (TextView) findViewById(R.id.patien);

        //on click listeners
       doctor.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent it = new Intent(DoctorPatient.this,AddByOptions.class);
               startActivity(it);
           }
       });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(DoctorPatient.this,CheckFirebaseUser.class);
                startActivity(it);
            }
        });
    }
}
