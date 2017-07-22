package com.example.zohai.healthapp.DoctorPanel;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zohai.healthapp.R;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AddPatient extends AppCompatActivity {

    private TextView addPatient;
    public ListView listPatient;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    String patient;

    List<String> arrayList;
    ArrayAdapter<String> adapter;

    int selectedItem = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();

        addPatient = (TextView)findViewById(R.id.addpatient);
        listPatient = (ListView)findViewById(R.id.listpatient);
        arrayList = new ArrayList<String>();

         authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    patient = firebaseAuth.getCurrentUser().getEmail();
                    arrayList.add(patient);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No user found",Toast.LENGTH_SHORT).show();
                }
            }
        };
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listPatient.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(AddPatient.this,DoctorPanel_Signin.class);
                startActivity(it);
            }
        });

        listPatient.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddPatient.this);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                auth.signOut();
                                arrayList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });

        listPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent it = new Intent(AddPatient.this,DoctorPanel_UniqueID.class);
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
