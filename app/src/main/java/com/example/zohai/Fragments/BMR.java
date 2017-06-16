package com.example.zohai.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zohai.healthapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BMR extends Fragment {
    public static BMR newInstance()
    {
        BMR fragment = new BMR();
        return fragment;
    }


    public BMR() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bmr, container, false);
    }

}
