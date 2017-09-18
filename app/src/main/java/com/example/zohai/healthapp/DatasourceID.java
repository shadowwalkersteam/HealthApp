package com.example.zohai.healthapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.zohai.Fragments.Monitor;

public class DatasourceID extends AppCompatActivity {
    public String id;
    public EditText uniqueID;
    private Button Submit;
    private TextInputLayout Datainputlayoutid;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasource_id);
        uniqueID = (EditText) findViewById(R.id.dataID);
        Submit = (Button) findViewById(R.id.confirm);
        Datainputlayoutid = (TextInputLayout) findViewById(R.id.data_input_layout_id);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        //if id is already saved then skip the DatasourceID activity
        if(sharedPreferences.contains("DataID"))
            startActivity(new Intent(DatasourceID.this,Dashboard2.class));

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    public void submitForm() {
        if (!checkID()) {
            return;
        }
        Datainputlayoutid.setErrorEnabled(false);
        String dsource = uniqueID.getText().toString();
        //putting datasource id in shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DataID",dsource);
        editor.commit();
        Intent it = new Intent(DatasourceID.this, Dashboard2.class);
//        it.putExtra("hello", uniqueID.getText().toString());
        startActivity(it);
    }

    //check for id if it is valid or not
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
