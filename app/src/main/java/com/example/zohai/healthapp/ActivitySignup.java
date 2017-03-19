package com.example.zohai.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ActivitySignup extends AppCompatActivity {

  ImageView signupback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signupback = (ImageView)findViewById(R.id.signupback);

        signupback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(ActivitySignup.this, MainActivity.class);
                startActivity(it);

            }
        });

    }
}
