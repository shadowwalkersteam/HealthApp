package com.example.zohai.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ActivitySignin extends AppCompatActivity {


     ImageView signinback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        signinback = (ImageView)findViewById(R.id.signinback);


        signinback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivitySignin.this,MainActivity.class);
                startActivity(it);
            }
        });
    }
}
