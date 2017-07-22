package com.example.zohai.Fragments.DoctorPanel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zohai.healthapp.R;

public class IDprofile extends Fragment {


    public static IDprofile newInstance() {
        IDprofile fragment = new IDprofile();
        return fragment;
    }

    public IDprofile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idprofile, container, false);
    }

}
