package com.example.zohai.healthapp.DoctorPanel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.zohai.healthapp.Dashboard2;
import com.example.zohai.healthapp.DatasourceID;
import com.example.zohai.healthapp.R;

public class DoctorPanel_UniqueID extends AppCompatActivity {

    public String id;
    public EditText uniqueID;
    private Button Submit;
    private TextInputLayout Datainputlayoutid;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_panel__unique_id);

        uniqueID = (EditText) findViewById(R.id.dataID);
        Submit = (Button) findViewById(R.id.confirm);
        Datainputlayoutid = (TextInputLayout) findViewById(R.id.data_input_layout_id);
        sharedPreferences = getSharedPreferences("Doctor", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("SecretID"))
            startActivity(new Intent(DoctorPanel_UniqueID.this,DoctorDashboard.class));

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        if (!checkID()) {
            return;
        }
        Datainputlayoutid.setErrorEnabled(false);
        String dsource = uniqueID.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SecretID",dsource);
        editor.commit();
        Intent it = new Intent(DoctorPanel_UniqueID.this, DoctorDashboard.class);
        startActivity(it);
    }
    private boolean checkID() {
        String ID = uniqueID.getText().toString().trim();
        if (ID.isEmpty() || !isIDValid(ID)) {
            Datainputlayoutid.setErrorEnabled(true);
            Datainputlayoutid.setError(getString(R.string.err_msg_id));
            uniqueID.setError(getString(R.string.err_msg_required));
            requestFocus(uniqueID);
            return false;
        }
        Datainputlayoutid.setErrorEnabled(false);
        return true;
    }

    private boolean isIDValid(String id) {
        return (id.length() >= 8);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
