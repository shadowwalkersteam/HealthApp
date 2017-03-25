package com.example.zohai.healthapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DoctorPatient extends AppCompatActivity {
    TextView doctor;
    TextView patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient);
        doctor = (TextView)findViewById(R.id.doc);
        patient = (TextView) findViewById(R.id.patien);

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(DoctorPatient.this,MainActivity.class);
                startActivity(it);
            }
        });
    }
}
