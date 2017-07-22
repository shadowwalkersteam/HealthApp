package com.example.zohai.Fragments.DoctorPanel;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zohai.Fragments.Monitor;
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

public class DoctorProfile extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {
    private EditText f_name;
    private EditText dob;
    private EditText email_id;
    private EditText ph_nmbr;
    private ProgressDialog progressDialog;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String firebaseUser;

    public static DoctorProfile newInstance() {
        DoctorProfile fragment = new DoctorProfile();
        return fragment;
    }

    public DoctorProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
        final View view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);
        f_name = (EditText) view.findViewById(R.id.fullname);
        dob = (EditText) view.findViewById(R.id.age);
        email_id = (EditText) view.findViewById(R.id.Emailaddress);
        ph_nmbr = (EditText) view.findViewById(R.id.Phonenumber);

        f_name.setEnabled(false);
        dob.setEnabled(false);
        email_id.setEnabled(false);
        ph_nmbr.setEnabled(false);

        progressDialog.setMessage("Getting your profile ready");
        progressDialog.show();

        mFirebaseDatabase.child(firebaseUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile user = dataSnapshot.getValue(UserProfile.class);
                if (user == null) {
                    Toast.makeText(getActivity(), "No user data found.", Toast.LENGTH_SHORT).show();
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
        return view;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Myapplication.getInstance().setConnectivityListener(this);

    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToast(isConnected);
    }
}
