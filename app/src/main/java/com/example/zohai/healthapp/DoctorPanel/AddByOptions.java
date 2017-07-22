package com.example.zohai.healthapp.DoctorPanel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.zohai.healthapp.R;

public class AddByOptions extends AppCompatActivity {
    TextView byId;
    TextView byEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_by_options);
    }
}
