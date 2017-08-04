package com.example.zohai.Fragments.DoctorPanel;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zohai.healthapp.R;

import static android.support.design.widget.Snackbar.LENGTH_INDEFINITE;

public class IDprofile extends Fragment {

    TextView info;

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
        View view = inflater.inflate(R.layout.fragment_idprofile, container, false);
        final View viewPos = view.findViewById(R.id.myCoordinatorLayout);
        info = (TextView) view.findViewById(R.id.proInfo);

        String message = null;
        int color = 0;
        color = Color.WHITE;
        message = "Please sign in with Patient Email to get profile";
        Snackbar snackbar = Snackbar.make(viewPos,message,Snackbar.LENGTH_INDEFINITE);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.DarkColor));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
        return view;
    }
}
