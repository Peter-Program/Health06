package com.example.health06.Nutrition;

import androidx.appcompat.app.ActionBar;

import com.example.health06.BaseActivity;
import com.example.health06.R;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.*;
import java.util.Scanner;



public class NutritionActivity extends BaseActivity {


    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    TextView currentCalories;
    TextView dailyCalories;
    TextView divider;

    TextView daily_goal;
    TextView daily_total;

    Button new_meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        currentCalories = findViewById(R.id.currentCalories);
        dailyCalories = findViewById(R.id.dailyCalories);
        divider = findViewById(R.id.divider);
        daily_goal = findViewById(R.id.daily_goal);
        daily_total = findViewById(R.id.daily_total);
        new_meal = findViewById(R.id.new_meal);

        loadCalories();

        divider.setText("/");
        daily_goal.setText("Target Calories");
        daily_total.setText("Current Calories");
    }

    private void loadCalories() {
        try {
            File file = new File(path, "dailyCalories.txt");
            Scanner sc = new Scanner(file);
            currentCalories.setText(sc.nextLine());
            dailyCalories.setText(sc.nextLine());
            sc.close();
        } catch(IOException e){
            e.printStackTrace();
        }


    }

    public void onClickEnterMeal(View view) {
        Intent intent = new Intent(this, NutritionEnterMeal.class);
        startActivity(intent);
    }




}
