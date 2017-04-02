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
public class Home extends Fragment {

    public static Home newInstance()
    {
        Home fragment = new Home();
        return fragment;
    }


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
