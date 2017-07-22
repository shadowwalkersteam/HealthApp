package com.example.zohai.healthapp.DoctorPanel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zohai.healthapp.R;

public class AddByOptions extends AppCompatActivity {
    TextView byId;
    TextView byEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_by_options);
        byId = (TextView) findViewById(R.id.byid);
        byEmail = (TextView) findViewById(R.id.byemail);

        byId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(AddByOptions.this,AddbyID.class);
                startActivity(it);
            }
        });

        byEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(AddByOptions.this,AddPatient.class);
                startActivity(it);
            }
        });
    }
}
