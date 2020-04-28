package com.example.health06.Workout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.health06.BaseActivity;
import com.example.health06.R;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TodaysWorkout extends BaseActivity {

    ScrollView scrollView1;
    ScrollView scrollView2;

    LinearLayout layoutView1;
    LinearLayout layoutView2;
    private Byte IOUtils;

    TextView textView1;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todaysworkout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        scrollView1 = findViewById(R.id.scrollView);
        scrollView2 = findViewById(R.id.scrollView2);

        textView1 = findViewById(R.id.textView4);
        textView2 = findViewById(R.id.textView6);

        textView1.setText("Standard Workout");
        textView2.setText("Advanced Workout");

        layoutView1 = scrollView1.findViewById(R.id.linearLayoutView1); //bottom view
        layoutView2 = scrollView2.findViewById(R.id.linearLayoutView2); //top view

        TextView tv = new TextView(this);
        String fileName = "basicworkout.txt";
        File file = getApplicationContext().getFileStreamPath(fileName);
        if(file == null || !file.exists()) {
            try {
                FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
                fos.write("Bench Press 3 5 125\nBicep Curls 3 10 10\nLeg Press 5 5 135".getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String s = readFile(fileName);
//        String[] strArray = s.split("\n");
        tv.setText(s);
        tv.setTextColor(Color.rgb(0,0,0));
        tv.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        layoutView1.addView(tv);



    }

    private String readFile(String fileName) {
        String ret = "";
        StringBuilder sb = new StringBuilder();
        File file = getApplicationContext().getFileStreamPath(fileName);
        if(file == null || !file.exists()) {

        }else{
            //for storing caloric intake by day
            try {
                FileInputStream fis = openFileInput(fileName);
                Scanner sc = new Scanner(fis);
                //empty
                while(sc.hasNextLine()) {
                    sb.append(sc.nextLine()).append('\n');
                }
                sc.close();
                fis.close();
                ret = sb.toString();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }


        return ret;

    }



}
