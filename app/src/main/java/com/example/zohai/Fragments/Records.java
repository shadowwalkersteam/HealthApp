package com.example.zohai.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.zohai.healthapp.ConnectivityReceiver;
import com.example.zohai.healthapp.Dashboard2;
import com.example.zohai.healthapp.DatasourceID;
import com.example.zohai.healthapp.Myapplication;
import com.example.zohai.healthapp.UbidotsClient;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.example.zohai.healthapp.R;

public class Records extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener{
    private String API_KEY = "9XWFrsh83kQWtNvBB6oU1F6zufzS8B";

    private String heartVarId = "heart-rate";
    private String bloodVarId = "blood-pressure";
    private String tempVarId = "temperature";

    SharedPreferences sharedPreferences;

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
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Datasource = sharedPreferences.getString("DataID",null);

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
    public void onResume()
    {
        super.onResume();
        Myapplication.getInstance().setConnectivityListener(this);

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
        (new UbidotsClient()).handleUbidots(Datasource,heartVarId, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value> result) {
                Log.d("Chart", "======== On data Ready ===========");
                List<Entry> entries = new ArrayList();
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
                heartchart.setVisibleXRangeMaximum(30);
                final Handler handler = new Handler(Records.this.getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        heartchart.animateX(3000);
                        heartchart.moveViewToX(-1);
                        heartchart.notifyDataSetChanged();
                        heartchart.invalidate();
                    }
                });

            }
        });

//        Blood Pressure
        (new UbidotsClient()).handleUbidots(Datasource,bloodVarId, API_KEY, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value> result) {
                Log.d("Chart", "======== On data Ready ===========");
                List<Entry> entries = new ArrayList();
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
                bloodchart.setVisibleXRangeMaximum(30);
                final Handler handler = new Handler(Records.this.getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bloodchart.animateX(3000);
                        bloodchart.moveViewToX(-1);
                        bloodchart.notifyDataSetChanged();
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
                List<Entry> entries = new ArrayList();
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
                lse.setColor(Color.DKGRAY);
                lse.setCircleColor(Color.DKGRAY);
                lse.setLineWidth(1f);
                lse.setCircleSize(3f);
                lse.setDrawCircleHole(false);
                lse.setFillAlpha(65);
                lse.setFillColor(Color.DKGRAY);

                LineData ld = new LineData(labels, lse);
                tempchart.setData(ld);
                tempchart.setVisibleXRangeMaximum(30);
                final Handler handler = new Handler(Records.this.getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tempchart.animateX(3000);
                        tempchart.moveViewToX(-1);
                        tempchart.notifyDataSetChanged();
                        tempchart.invalidate();
                    }
                });

            }
        });

        return view;
    }

    private void initChartTemp(LineChart chart){
        //Limit Line
        LimitLine ll1 = new LimitLine(103f, "High Temperature");
        ll1.setLineWidth(1f);
        ll1.enableDashedLine(10f, 4f, 0f);
        ll1.setLineColor(Color.BLACK);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(8f);

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
        leftAxis.addLimitLine(ll1);
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
        //Limit line
        LimitLine ll1 = new LimitLine(120f, "Normal Systolic");
        ll1.setLineWidth(1f);
        ll1.enableDashedLine(10f, 4f, 0f);
        ll1.setLineColor(Color.BLACK);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(8f);

        LimitLine ll2 = new LimitLine(80f, "Normal Diastolic");
        ll2.setLineWidth(1f);
        ll2.enableDashedLine(10f, 4f, 0f);
        ll2.setLineColor(Color.BLACK);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(8f);

        chart.setTouchEnabled(true);
        chart.setPinchZoom(false);
//        chart.setDrawGridBackground(false);
        chart.getAxisRight().setEnabled(false);
        chart.setDescription("mmHg");
        chart.setGridBackgroundColor(Color.parseColor("#E1F5FE"));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaxValue(140F);
        leftAxis.setAxisMinValue(65F);
        leftAxis.setStartAtZero(false);
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
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
        //Limit line
        LimitLine ll1 = new LimitLine(100f, "Normal Heart Rate");
        ll1.setLineWidth(1f);
        ll1.enableDashedLine(10f, 4f, 0f);
        ll1.setLineColor(Color.BLACK);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(8f);

        LimitLine ll2 = new LimitLine(60f, "Normal Heart Rate");
        ll2.setLineWidth(1f);
        ll2.enableDashedLine(10f, 4f, 0f);
        ll2.setLineColor(Color.BLACK);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(8f);

        chart.setTouchEnabled(true);
        chart.setPinchZoom(false);
//        chart.setDrawGridBackground(false);
        chart.getAxisRight().setEnabled(false);
        chart.setDescription("BPM");
        chart.setGridBackgroundColor(Color.parseColor("#E1F5FE"));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaxValue(160F);
        leftAxis.setAxisMinValue(35F);
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
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

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToast(isConnected);
    }
}
