package com.example.hp.ikurenewedition;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.hp.ikurenewedition.fragments.BloodPressureFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hp on 10-02-2018.
 */

public class Blood_Pressure_Graph extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {
    // private String[] timestamp = new String[5];
    // private String[] systolic = new String[5];
    // private String[] diastolic = new String[5];
    // private float s1 =0,s2=0,s3=0,s4=0,s5=0;
    private float d1 = 0, d2 = 0, d3 = 0, d4 = 0, d5 = 0;
    private float t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0;
    private LineChart mChart;
    private float greatest;
    private ArrayList<String> systolic = new ArrayList<>();
    private ArrayList<String> diastolic = new ArrayList<>();
    private ArrayList<String> timestamp = new ArrayList<>();
    private String pid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_graph);
        Intent i = getIntent();
        systolic = i.getStringArrayListExtra("systolic");
        diastolic = i.getStringArrayListExtra("diastolic");
        timestamp = i.getStringArrayListExtra("timestamp");
        pid = i.getStringExtra("pid");
        Collections.reverse(systolic);
        Collections.reverse(diastolic);
        Collections.reverse(timestamp);

        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);




     /*   greatest = Float.parseFloat(systolic[0]);
        for (int j = 1; j < 5; j++) {
            if (Float.parseFloat(systolic[j]) > greatest) {
                greatest = Float.parseFloat(systolic[j]);
            }
        }
    */
        setData();
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        mChart.setDescription("Blood Pressure Trends");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaxValue(220);
        leftAxis.setAxisMinValue(0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mChart.invalidate();


    }

    public void bullshit() {
        Toast.makeText(Blood_Pressure_Graph.this, "sorry", Toast.LENGTH_LONG).show();
        //Intent i=new Intent(NetworkActivity.this,MainActivity.class);
        finish();
        //startActivity(i);
    }


    private ArrayList<String> setXAxisValues() {
        ArrayList<String> xVals = new ArrayList<String>();
        try {

            for (int j = 0; j < timestamp.size(); j++) {
                xVals.add(String.valueOf(convert(Float.valueOf(timestamp.get(j)))));

            }
            //xVals.add(String.valueOf(convert(t4)));
            //xVals.add(String.valueOf(convert(t3)));
            //xVals.add(String.valueOf(convert(t2)));
            //xVals.add(String.valueOf(convert(t1)));

            return xVals;
        } catch (Exception e) {
            e.printStackTrace();
            bullshit();
        }
        return xVals;
    }

    private ArrayList<Entry> setYAxisValues1() {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        try {
            for (int j = 0; j < systolic.size(); j++) {
                yVals.add(new Entry(Float.parseFloat(systolic.get(j)), j));
            }
            //yVals.add(new Entry(s5, 0));
            //yVals.add(new Entry(s4, 1));
            //yVals.add(new Entry(s3, 2));
            //yVals.add(new Entry(s2, 3));
            //yVals.add(new Entry(s1, 4));

            return yVals;
        } catch (Exception e) {
            e.printStackTrace();
            bullshit();
        }
        return yVals;
    }

    private ArrayList<Entry> setYAxisValues2() {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        try {
            for (int j = 0; j < diastolic.size(); j++) {
                yVals.add(new Entry(Float.parseFloat(diastolic.get(j)), j));
            }
            return yVals;
        } catch (Exception e) {
            e.printStackTrace();
            bullshit();
        }
        return yVals;
    }


    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues1();
        ArrayList<Entry> yVals2 = setYAxisValues2();


        LineDataSet set1, set2;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Systolic");
        set2 = new LineDataSet(yVals2, "Diastolic");
        set1.setFillAlpha(110);
        set2.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.RED);
        set2.setColor(Color.parseColor("#FF9933"));
        set1.setCircleColor(Color.BLACK);
        set2.setCircleColor(Color.BLACK);
        set1.setLineWidth(2f);
        set2.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set2.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set2.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set2.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set2.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets
        dataSets.add(set2);
        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);

    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2,
                             float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: "
                + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex()
                + ", high: " + mChart.getHighestVisibleXIndex());

        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin()
                + ", xmax: " + mChart.getXChartMax()
                + ", ymin: " + mChart.getYChartMin()
                + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }


    private String convert(Float time) {
        float tim = time;
        tim = tim * 1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("d MMM HH:mm");
        return formatter.format(tim);

        //return date;
    }


}
