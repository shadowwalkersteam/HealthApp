package com.example.zohai.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zohai.healthapp.UbidotsClient;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.zohai.healthapp.R;

public class Records extends Fragment{
    private String API_KEY = "9XWFrsh83kQWtNvBB6oU1F6zufzS8B";
//    private String heartVarId = "591f1d3a762542541ea9a798";
//    private String bloodVarId = "591f1d3a762542541ea9a799";
//    private String tempVarId = "591f1d3a762542541ea9a79c";

    private String heartVarId = "heart-rate";
    private String bloodVarId = "blood-pressure";
    private String tempVarId = "temperature";


    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private LineChart heartchart;
    private LineChart bloodchart;
    private LineChart tempchart;
    String Datasource;

    public static Records newInstance()
    {
        Records fragment = new Records();
        return fragment;
    }


    public Records() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Datasource = getActivity().getIntent().getStringExtra("Datasource");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_records, container, false);
        heartchart = (LineChart) view.findViewById(R.id.heart_rate);
        bloodchart = (LineChart) view.findViewById(R.id.blood_pressure);
        tempchart = (LineChart) view.findViewById(R.id.temperature);
        initChartHeart(heartchart);
        initChartBlood(bloodchart);
        initChartTemp(tempchart);

//        Heart Rate
        (new UbidotsClient()).handleUbidots( Datasource,heartVarId, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value> result) {
                Log.d("Chart", "======== On data Ready ===========");
                ArrayList<Entry> entries = new ArrayList();
                List<String> labels = new ArrayList<String>();
                for (int i = 0; i < result.size(); i++) {

                    Entry be = new Entry(result.get(i).value, i);
                    entries.add(be);
                    Log.d("Chart", be.toString());
//                     Convert timestamp to date
                    Date d = new Date(result.get(i).timestamp);
                    // Create Labels
                    labels.add(sdf.format(d));
                }

                LineDataSet lse = new LineDataSet(entries, "Heart Rate");

                lse.setDrawHighlightIndicators(false);
                lse.setDrawValues(false);
                lse.setColor(Color.RED);
                lse.setCircleColor(Color.RED);
                lse.setLineWidth(1f);
                lse.setCircleSize(3f);
                lse.setDrawCircleHole(false);
                lse.setFillAlpha(65);
                lse.setFillColor(Color.RED);

               LineData ld = new LineData(labels, lse);
                heartchart.setData(ld);
                Handler handler = new Handler(Records.this.getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        heartchart.invalidate();
                    }
                });

            }
        });

//        Blood Pressure
        (new UbidotsClient()).handleUbidots( Datasource,bloodVarId, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value> result) {
                Log.d("Chart", "======== On data Ready ===========");
                ArrayList<Entry> entries = new ArrayList();
                List<String> labels = new ArrayList<String>();
                for (int i = 0; i < result.size(); i++) {

                    Entry be = new Entry(result.get(i).value, i);
                    entries.add(be);
                    Log.d("Chart", be.toString());
//                     Convert timestamp to date
                    Date d = new Date(result.get(i).timestamp);
                    // Create Labels
                    labels.add(sdf.format(d));
                }

                LineDataSet lse = new LineDataSet(entries, "Blood Pressure");

                lse.setDrawHighlightIndicators(false);
                lse.setDrawValues(false);
                lse.setColor(Color.BLUE);
                lse.setCircleColor(Color.BLUE);
                lse.setLineWidth(1f);
                lse.setCircleSize(3f);
                lse.setDrawCircleHole(false);
                lse.setFillAlpha(65);
                lse.setFillColor(Color.BLUE);

                LineData ld = new LineData(labels, lse);
                bloodchart.setData(ld);
                Handler handler = new Handler(Records.this.getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bloodchart.invalidate();
                    }
                });

            }
        });

//        Temperature
        (new UbidotsClient()).handleUbidots(Datasource, tempVarId, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value> result) {
                Log.d("Chart", "======== On data Ready ===========");
                ArrayList<Entry> entries = new ArrayList();
                List<String> labels = new ArrayList<String>();
                for (int i = 0; i < result.size(); i++) {

                    Entry be = new Entry(result.get(i).value, i);
                    entries.add(be);
                    Log.d("Chart", be.toString());
//                     Convert timestamp to date
                    Date d = new Date(result.get(i).timestamp);
                    // Create Labels
                    labels.add(sdf.format(d));
                }

                LineDataSet lse = new LineDataSet(entries, "Temperature");

                lse.setDrawHighlightIndicators(false);
                lse.setDrawValues(false);
                lse.setColor(Color.BLUE);
                lse.setCircleColor(Color.BLUE);
                lse.setLineWidth(1f);
                lse.setCircleSize(3f);
                lse.setDrawCircleHole(false);
                lse.setFillAlpha(65);
                lse.setFillColor(Color.BLUE);

                LineData ld = new LineData(labels, lse);
                tempchart.setData(ld);
                Handler handler = new Handler(Records.this.getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tempchart.invalidate();
                    }
                });

            }
        });

        return view;
    }

    private void initChartTemp(LineChart chart){
        chart.setTouchEnabled(true);
        chart.setPinchZoom(false);
//        chart.setDrawGridBackground(false);
        chart.getAxisRight().setEnabled(false);
        chart.setDescription("Fahrenheit");
        chart.setGridBackgroundColor(Color.parseColor("#E1F5FE"));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaxValue(108F);
        leftAxis.setAxisMinValue(97F);
        leftAxis.setStartAtZero(false);
        leftAxis.setAxisLineWidth(2);
        leftAxis.setDrawGridLines(true);
//        leftAxis.setGranularityEnabled(true);

        // X-Axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.resetLabelsToSkip();
        xAxis.setDrawGridLines(true);
        xAxis.setAxisLineWidth(2);
        xAxis.setDrawAxisLine(true);

    }

    private void initChartBlood(LineChart chart) {
        chart.setTouchEnabled(true);
        chart.setPinchZoom(false);
//        chart.setDrawGridBackground(false);
        chart.getAxisRight().setEnabled(false);
        chart.setDescription("mmHg");
        chart.setGridBackgroundColor(Color.parseColor("#E1F5FE"));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaxValue(120F);
        leftAxis.setAxisMinValue(77F);
        leftAxis.setStartAtZero(false);
        leftAxis.setAxisLineWidth(2);
        leftAxis.setDrawGridLines(true);
//        leftAxis.setGranularityEnabled(true);

        // X-Axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.resetLabelsToSkip();
        xAxis.setDrawGridLines(true);
        xAxis.setAxisLineWidth(2);
        xAxis.setDrawAxisLine(true);

    }

    private void initChartHeart(LineChart chart) {
        chart.setTouchEnabled(true);
        chart.setPinchZoom(false);
//        chart.setDrawGridBackground(false);
        chart.getAxisRight().setEnabled(false);
        chart.setDescription("BPM");
        chart.setGridBackgroundColor(Color.parseColor("#E1F5FE"));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaxValue(100F);
        leftAxis.setAxisMinValue(37F);
        leftAxis.setStartAtZero(false);
        leftAxis.setAxisLineWidth(2);
        leftAxis.setDrawGridLines(true);
//        leftAxis.setGranularityEnabled(true);

        // X-Axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.resetLabelsToSkip();
        xAxis.setDrawGridLines(true);
        xAxis.setAxisLineWidth(2);
        xAxis.setDrawAxisLine(true);
    }

}
