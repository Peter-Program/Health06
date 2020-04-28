package com.example.health06.Nutrition;
import com.example.health06.R;

import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;



import android.os.Bundle;

import com.example.health06.R;
import com.example.health06.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TrackNutritionProgress extends BaseActivity {

    private static final String calorieProgressFile = "calorieTracker.txt";
    private LineChart chart;
    private ArrayList<Entry> caloriesOverTime;
    private ArrayList<Integer> pointColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_nutrition_progress);
        caloriesOverTime = new ArrayList<>();
        pointColors = new ArrayList<>();

        chart = findViewById(R.id.chart);

        CustomMarkerView mv = new CustomMarkerView(getApplicationContext(), R.layout.custom_marker);

        // set the marker to the chart
        chart.setMarker(mv);

        getDataPoints();

        renderData();
    }


    private void getDataPoints(){
        //for storing caloric intake by day
        String data = "";


        File file = getApplicationContext().getFileStreamPath(calorieProgressFile);
        if(file == null || !file.exists()) {
            data = "2134,2345,2312,2545,1356,2800,2123";
        }
        else{

            try {
                FileInputStream fis = openFileInput(calorieProgressFile);
                Scanner sc = new Scanner(fis);
                if(sc.hasNext()){
                    data = sc.nextLine();
                }

                sc.close();
                fis.close();

            }
            catch(IOException e){
                e.printStackTrace();
            }

        }

        String[] points = data.split(",");
        int i = 1;
        int good = ContextCompat.getColor(getApplicationContext(),R.color.colorNutrition);
        int bad = ContextCompat.getColor(getApplicationContext(),R.color.colorFilled);
        for(String val: points){
            Integer cals = Integer.parseInt(val);
            caloriesOverTime.add(new Entry(i, cals));
            if(cals  <= 2500) {
                pointColors.add(good);
            }else{
                pointColors.add(bad);
            }
            i++;
        }

    }

    private void renderData(){
        LineDataSet dataSet = new LineDataSet(caloriesOverTime, "Daily Calories Over Time");
        dataSet.setColor(ContextCompat.getColor(getApplicationContext(),R.color.colorText));
        dataSet.setCircleColors(pointColors);
        dataSet.setLineWidth(2.4f);
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);


        //styling
        Description desc = chart.getDescription();
        chart.setNoDataText("No Previous Information!");
        chart.setDrawBorders(true);
        desc.setEnabled(false);
        chart.setPinchZoom(true);
        XAxis topAxis = chart.getXAxis();
        topAxis.setEnabled(false);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();

        LimitLine ll1 = new LimitLine(2500, "Target");
        ll1.setLineWidth(2.4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLineColor(ContextCompat.getColor(getApplicationContext(),R.color.colorText));
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(14f);
        leftAxis.addLimitLine(ll1);

        chart.invalidate(); // refresh

    }

}
