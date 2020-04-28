package com.example.health06.Nutrition;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.health06.BaseActivity;
import com.example.health06.MainActivity;
import com.example.health06.R;
import com.example.health06.Nutrition.ExpandableListAdapter;


import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.renderscript.ScriptGroup;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Calendar;



public class NutritionActivity extends BaseActivity {

    TextView currentCalories;
    TextView dailyCalories;
    TextView divider;
    TextView list_title;

    TextView daily_goal;
    TextView daily_total;
    ProgressBar calorieProgress;

    Button new_meal;
    Button progress;

    private static final String calorieFile = "dailyCalories.txt";
    private static final String todaysMealsFile = "todaysMeals.txt";
    private static final String calorieProgressFile = "calorieTracker.txt";

    private ExpandableListAdapter mealsAdapter;
    private ExpandableListView todaysMeals;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;




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
        progress = findViewById(R.id.progress);
        calorieProgress = findViewById(R.id.calorieProgress);
        list_title = findViewById(R.id.list_title);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int lastInitialized = prefs.getInt("DayOfYear", -1);
        int date = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        if(date != lastInitialized){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("DayOfYear",date);
            editor.commit();
            initializeDailyCalories();
            initializeDailyMeals();
        }

        loadCalories();

        divider.setText("/");
        daily_goal.setText("Target Calories");
        daily_total.setText("Current Calories");


        // get the listview
        todaysMeals = (ExpandableListView) findViewById(R.id.todaysMeals);

        //ready the list
        loadMeals();

        mealsAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        //set adapter
        todaysMeals.setAdapter(mealsAdapter);

    }

    private void loadCalories() {

        try {

            //set the fractional view
            FileInputStream fis = openFileInput(calorieFile);
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

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //reset or initialize caloric intake count to 0 vs daily calorie total of 2500
    private void initializeDailyCalories(){

        File file = getApplicationContext().getFileStreamPath(calorieFile);
        if(file == null || !file.exists()) {

        }else{
            //for storing caloric intake by day
            try {
                FileInputStream fis = openFileInput(calorieFile);
                Scanner sc = new Scanner(fis);
                //empty
                if(sc.hasNextLine() == true){
                    String endCals = sc.nextLine();
                    FileOutputStream fos = openFileOutput(calorieProgressFile, MODE_APPEND);
                    fos.write((endCals + ",").getBytes());
                }
                sc.close();
                fis.close();

            }
            catch(IOException e){
                e.printStackTrace();
            }
        }


        //initialize file
        try {
            FileOutputStream fos = openFileOutput(calorieFile, MODE_PRIVATE);
            fos.write("0\n2500\n".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void loadMeals(){

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        try {

            //Set meal list
            FileInputStream fis = openFileInput(todaysMealsFile);
            Scanner sc = new Scanner(fis);
            //empty
            if(sc.hasNext() != false){
                list_title.setText("Today's Meals");
            }
            while(sc.hasNext()){
                String line = sc.nextLine();
                String[] info = line.split("\\|");
                listDataHeader.add(info[0]);
                listDataChild.put(info[0], Arrays.asList(info[1]));
            }


            sc.close();
            fis.close();

        } catch(IOException e){
            e.printStackTrace();
        }

    }

    //reset or initialize caloric intake count to 0 vs daily calorie total of 2500
    private void initializeDailyMeals(){

        FileOutputStream fos;

        try {
            fos = openFileOutput(todaysMealsFile, MODE_PRIVATE);
            fos.write("".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onClickEnterMeal(View view) {
        Intent intent = new Intent(this, NutritionEnterMeal.class);
        startActivity(intent);
    }

    public void onClickProgress(View view){
        Intent intent = new Intent(this, TrackNutritionProgress.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }


}
