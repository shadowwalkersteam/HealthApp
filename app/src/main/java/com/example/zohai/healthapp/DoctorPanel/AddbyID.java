package com.example.zohai.healthapp.DoctorPanel;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zohai.healthapp.R;

import java.util.ArrayList;
import java.util.List;

public class AddbyID extends AppCompatActivity {

    private TextView addPatient;
    public ListView listPatient;

    String UniqueID;
    String Username;

    List<String> arrayList;
    ArrayAdapter<String> adapter;
    List<UniqueID> unique;
    DatabaseHandler db;

    int key;
    String name;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addby_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        db = new DatabaseHandler(this);

        addPatient = (TextView)findViewById(R.id.addpatient);
        listPatient = (ListView)findViewById(R.id.listpatient);
        arrayList = new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        final UniqueID unike = new UniqueID();

        readingAll();

        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddbyID.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                builder.setView(dialogView);

                final EditText ID = (EditText) dialogView.findViewById(R.id.edit1);
//                final EditText Name = (EditText) dialogView.findViewById(R.id.edit2);

                builder.setTitle("Unique ID");
                builder.setMessage("Enter your patient unique ID");
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UniqueID = ID.getText().toString();
//                        Username = Name.getText().toString();
                        db.addUnique(new UniqueID(UniqueID,Username));
                        arrayList.add(UniqueID);
//                        arrayList.add(Username);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

        listPatient.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listPatient.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddbyID.this);
                builder.setTitle("Delete Item");
                builder.setMessage("Are you sure want to delete this item?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteUniqueID(new UniqueID(key,data,name));
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
                String text = ((TextView)view).getText().toString();
                Intent it = new Intent(AddbyID.this,IdDashboard.class);
                it.putExtra("UniqueID",text);
                startActivity(it);

            }
        });
    }

    private void readingAll() {
        unique = db.getAllids();
        for (UniqueID un : unique)
        {
            key = un.getID();
            data = un.getDatasource();
//            name = un.getName();
            arrayList.add(data);
//            arrayList.add(name);
        }
        listPatient.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
