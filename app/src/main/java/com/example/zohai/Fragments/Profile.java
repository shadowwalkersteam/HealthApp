package com.example.zohai.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.zohai.healthapp.R;
import com.example.zohai.healthapp.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private EditText f_name;
    private EditText dob;
    private EditText email_id;
    private EditText ph_nmbr;
    private EditText bldgrp;
    private Button update;
    private String userId;
    private ProgressDialog progressDialog;

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
        bldgrp = (EditText) view.findViewById(R.id.bloodgrp);
        update = (Button) view.findViewById(R.id.updateprofile);

        f_name.setEnabled(false);
        dob.setEnabled(false);
        email_id.setEnabled(false);
        ph_nmbr.setEnabled(false);
        bldgrp.setEnabled(false);

        progressDialog.setMessage("Getting your profile ready");
        progressDialog.show();

        mFirebaseDatabase.child(firebaseUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserProfile user = dataSnapshot.getValue(UserProfile.class);
                if (user == null) {
                    Toast.makeText(getActivity(), "No user profile found", Toast.LENGTH_SHORT).show();
                    return;
                }
                f_name.setText(user.getName());
                dob.setText(user.getAge());
                email_id.setText(user.getEmail());
                ph_nmbr.setText(user.getPhone());
                bldgrp.setText(user.getGroup());

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Failed to read user", Toast.LENGTH_SHORT).show();
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
                bldgrp.setEnabled(true);
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
                String BloodGroup = bldgrp.getText().toString();

                if (TextUtils.isEmpty(firebaseUser)) {
                    createUser(FullName, DateOfBirth, EmailID, PhoneNumber, BloodGroup);
                } else {
                    updateUser(FullName, DateOfBirth, EmailID, PhoneNumber, BloodGroup);
                }

                f_name.setEnabled(false);
                dob.setEnabled(false);
                email_id.setEnabled(false);
                ph_nmbr.setEnabled(false);
                bldgrp.setEnabled(false);
                update.setVisibility(View.INVISIBLE);

            }
        });
        toggleButton();
        
        return view;
    }

    private void toggleButton() {
        if (TextUtils.isEmpty(firebaseUser)) {
            update.setText("Save");
        } else {
            update.setText("Update");
        }
    }

    private void createUser(String fullName, String dateOfBirth, String emailID, String phoneNumber, String bloodGroup) {
        if (TextUtils.isEmpty(firebaseUser)) {

//            userId = mFirebaseDatabase.push().getKey();
            firebaseUser = String.valueOf(mFirebaseDatabase.push());
        }

        UserProfile user = new  UserProfile(fullName, dateOfBirth, emailID, phoneNumber, bloodGroup);

        mFirebaseDatabase.child(firebaseUser).setValue(user);

        addUserChangeListener();
    }

    private void addUserChangeListener() {
        mFirebaseDatabase.child(firebaseUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile user = dataSnapshot.getValue(UserProfile.class);
                if (user == null) {
                    Toast.makeText(getActivity(), "User data is null", Toast.LENGTH_SHORT).show();
                    return;
                }
                f_name.setText(user.name);
                dob.setText(user.age);
                email_id.setText(user.email);
                bldgrp.setText(user.group);
                ph_nmbr.setText(user.phone);
                toggleButton();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Failed to read user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser(String fullName, String dateOfBirth, String emailID, String phoneNumber, String bloodGroup){

        if (!TextUtils.isEmpty(fullName))
            mFirebaseDatabase.child(firebaseUser).child("name").setValue(fullName);

        if (!TextUtils.isEmpty(dateOfBirth))
            mFirebaseDatabase.child(firebaseUser).child("age").setValue(dateOfBirth);

        if (!TextUtils.isEmpty(emailID))
            mFirebaseDatabase.child(firebaseUser).child("email").setValue(emailID);

        if (!TextUtils.isEmpty(phoneNumber))
            mFirebaseDatabase.child(firebaseUser).child("phone").setValue(phoneNumber);

        if (!TextUtils.isEmpty(bloodGroup))
            mFirebaseDatabase.child(firebaseUser).child("blood").setValue(bloodGroup);
    }
}
