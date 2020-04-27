package com.example.health06.Nutrition;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.health06.BaseActivity;
import com.example.health06.R;


import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.*;
import java.util.Scanner;
import java.util.Calendar;



public class NutritionActivity extends BaseActivity {

    TextView currentCalories;
    TextView dailyCalories;
    TextView divider;

    TextView daily_goal;
    TextView daily_total;
    ProgressBar calorieProgress;

    Button new_meal;

    static final private String fileName = "dailyCalories.txt";
    static private int currentDay;

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
        calorieProgress = findViewById(R.id.calorieProgress);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        if(day != currentDay){
            initializeDailyCalories();
            currentDay = day;
        }
        loadCalories();

        divider.setText("/");
        daily_goal.setText("Target Calories");
        daily_total.setText("Current Calories");
    }

    private void loadCalories() {

        try {

            //set the fractional view
            FileInputStream fis = openFileInput(fileName);
            Scanner sc = new Scanner(fis);
            currentCalories.setText(sc.nextLine());
            dailyCalories.setText(sc.nextLine());
            sc.close();
            fis.close();

            //set progress bar
            int dailyGoal = Integer.parseInt(dailyCalories.getText().toString());
            calorieProgress.setMax(dailyGoal);
            int currCals = Integer.parseInt(currentCalories.getText().toString());
            LayerDrawable progressBarDrawable = (LayerDrawable) calorieProgress.getProgressDrawable();
            Drawable progressDrawable = progressBarDrawable.getDrawable(1);


            if(currCals >= dailyGoal){
                calorieProgress.setProgress(dailyGoal);
                progressDrawable.setColorFilter(ContextCompat.getColor(calorieProgress.getContext(), R.color.colorFilled), PorterDuff.Mode.SRC_IN);
            }
            else if(currCals >= .75*dailyGoal){
                calorieProgress.setProgress(currCals);
                progressDrawable.setColorFilter(ContextCompat.getColor(calorieProgress.getContext(), R.color.colorVeryFull), PorterDuff.Mode.SRC_IN);
            }
            else{
                if(currCals < 25){
                    calorieProgress.setProgress(25);
                }
                else {
                    calorieProgress.setProgress(currCals);
                }
                progressDrawable.setColorFilter(ContextCompat.getColor(calorieProgress.getContext(), R.color.colorNutrition), PorterDuff.Mode.SRC_IN);
            }

        } catch (FileNotFoundException noFile){
            initializeDailyCalories();
            loadCalories();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //reset or initialize caloric intake count to 0 vs daily calorie total of 2500
    private void initializeDailyCalories(){

            File file = new File(getFilesDir(), fileName);
            FileOutputStream fos;

            try {
                fos = new FileOutputStream(file);
                fos.write("0\n2500\n".getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void onClickEnterMeal(View view) {
        Intent intent = new Intent(this, NutritionEnterMeal.class);
        startActivity(intent);
    }




}
