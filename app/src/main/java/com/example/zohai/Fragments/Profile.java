package com.example.zohai.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zohai.healthapp.ConnectivityReceiver;
import com.example.zohai.healthapp.Myapplication;
import com.example.zohai.healthapp.R;
import com.example.zohai.healthapp.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener{

    private EditText f_name;
    private EditText dob;
    private EditText email_id;
    private EditText ph_nmbr;
//    private EditText blood;
    private Button update;
//    private String userId;
    private ProgressDialog progressDialog;

    //initializing firebase instances
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String firebaseUser;

    public static Profile newInstance()
    {
        Profile fragment = new Profile();
        return fragment;
    }


    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mFirebaseInstance.getReference("App_title").setValue("Smart Health");
        progressDialog = new ProgressDialog(getActivity());

        checkConnection();

    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showToast(isConnected);
    }

    private void showToast(boolean isConnected) {
        if (!isConnected) {
            Toast.makeText(getActivity(),"Sorry! Not connected to internet",Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(getActivity(),"Good! Connected to Internet",Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        f_name = (EditText) view.findViewById(R.id.fullname);
        dob = (EditText) view.findViewById(R.id.age);
        email_id = (EditText) view.findViewById(R.id.Emailaddress);
        ph_nmbr = (EditText) view.findViewById(R.id.Phonenumber);
//        blood = (EditText) view.findViewById(R.id.bloodgrp);
        update = (Button) view.findViewById(R.id.updateprofile);

        f_name.setEnabled(false);
        dob.setEnabled(false);
        email_id.setEnabled(false);
        ph_nmbr.setEnabled(false);
//        blood.setEnabled(false);

        progressDialog.setMessage("Getting your profile ready");
        progressDialog.show();

        //if user node is available get the data
        mFirebaseDatabase.child(firebaseUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                UserProfile user = dataSnapshot.getValue(UserProfile.class);
                if (user == null) {
                    Toast.makeText(getActivity(), "No user data found. Please fill your details", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                } else {
                    f_name.setText(user.getName());
                    dob.setText(user.getAge());
                    email_id.setText(user.getEmail());
                    ph_nmbr.setText(user.getPhone());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f_name.setEnabled(true);
                dob.setEnabled(true);
                email_id.setEnabled(true);
                ph_nmbr.setEnabled(true);
//                blood.setEnabled(true);
                update.setVisibility(View.VISIBLE);
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FullName = f_name.getText().toString().trim();
                String DateOfBirth = dob.getText().toString().trim();
                String EmailID = email_id.getText().toString().trim();
                String PhoneNumber = ph_nmbr.getText().toString().trim();
//                String BloodGroup = blood.getText().toString();

                if (TextUtils.isEmpty(firebaseUser)) {
                    createUser(FullName, DateOfBirth, EmailID, PhoneNumber);
                } else {
                    updateUser(FullName, DateOfBirth, EmailID, PhoneNumber);
                }

                f_name.setEnabled(false);
                dob.setEnabled(false);
                email_id.setEnabled(false);
                ph_nmbr.setEnabled(false);
//                blood.setEnabled(false);
                update.setVisibility(View.INVISIBLE);

            }
        });
        toggleButton();
        
        return view;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Myapplication.getInstance().setConnectivityListener(this);

    }

    private void toggleButton() {
        if (TextUtils.isEmpty(firebaseUser)) {
            update.setText("Save");
        } else {
            update.setText("Update");
        }
    }

    //create user node and save values in that user node
    private void createUser(String fullName, String dateOfBirth, String emailID, String phoneNumber) {
        if (TextUtils.isEmpty(firebaseUser)) {

//            userId = mFirebaseDatabase.push().getKey();
            firebaseUser = String.valueOf(mFirebaseDatabase.push());
        }

        UserProfile user = new  UserProfile(fullName, dateOfBirth, emailID, phoneNumber);

        mFirebaseDatabase.child(firebaseUser).setValue(user);

        addUserChangeListener();
    }

    //if data is changed then update the fields
    private void addUserChangeListener() {
        mFirebaseDatabase.child(firebaseUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile user = dataSnapshot.getValue(UserProfile.class);
                if (user == null) {
                    Toast.makeText(getActivity(), "No user data found. Please fill your details", Toast.LENGTH_SHORT).show();
                    return;
                }
                f_name.setText(user.name);
                dob.setText(user.age);
                email_id.setText(user.email);
                ph_nmbr.setText(user.phone);
//                blood.setText(user.group);
                toggleButton();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //update the existing user data
    private void updateUser(String fullName, String dateOfBirth, String emailID, String phoneNumber){

        if (!TextUtils.isEmpty(fullName))
            mFirebaseDatabase.child(firebaseUser).child("name").setValue(fullName);

        if (!TextUtils.isEmpty(dateOfBirth))
            mFirebaseDatabase.child(firebaseUser).child("age").setValue(dateOfBirth);

        if (!TextUtils.isEmpty(emailID))
            mFirebaseDatabase.child(firebaseUser).child("email").setValue(emailID);

        if (!TextUtils.isEmpty(phoneNumber))
            mFirebaseDatabase.child(firebaseUser).child("phone").setValue(phoneNumber);

//        if (!TextUtils.isEmpty(bloodGroup))
//            mFirebaseDatabase.child(firebaseUser).child("blood").setValue(bloodGroup);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToast(isConnected);
    }
}
