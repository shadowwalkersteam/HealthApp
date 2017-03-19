package com.example.zohai.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivitySignin extends AppCompatActivity {


    ImageView signinback;
    TextView forgot;
    TextView sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        signinback = (ImageView)findViewById(R.id.signinback);
        sign = (TextView)findViewById(R.id.signin);
        forgot = (TextView)findViewById(R.id.forgt);


        signinback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivitySignin.this,MainActivity.class);
                startActivity(it);
            }
        });
    }
}
