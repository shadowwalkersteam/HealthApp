package com.example.zohai.healthapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoctorPatient extends AppCompatActivity {
    TextView doctor;
    TextView patient;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient);
        doctor = (TextView)findViewById(R.id.doc);
        patient = (TextView) findViewById(R.id.patien);
        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // if user is null launch login activity
                    startActivity(new Intent(DoctorPatient.this, MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(DoctorPatient.this,Dashboard2.class));
                }

            }
        };

       doctor.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent it = new Intent(DoctorPatient.this,AddPatient.class);
               startActivity(it);
           }
       });

        patient.setOnClickListener(      new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(DoctorPatient.this,MainActivity.class);
                startActivity(it);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

}
