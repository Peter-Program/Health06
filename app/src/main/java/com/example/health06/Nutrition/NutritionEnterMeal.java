package com.example.health06.Nutrition;

import com.example.health06.BaseActivity;
import com.example.health06.MainActivity;
import com.example.health06.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import java.io.*;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NutritionEnterMeal extends BaseActivity {

    EditText num_cals;
    TextView calorie_guide;
    EditText meal;
    Button submit_meal;

    private static final String calorieFile = "dailyCalories.txt";
    private static final String todaysMealsFile = "todaysMeals.txt";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_enter_meal);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        num_cals = findViewById(R.id.num_cals);
        calorie_guide = findViewById(R.id.calorie_guide);
        meal = findViewById(R.id.meal);
        submit_meal = findViewById(R.id.submit_meal);


    }

    public void onClickSubmitMeal(View view) {
        Toast.makeText(this, "Meal Entered",
                Toast.LENGTH_SHORT).show();
        addToTodaysMeals();
        updateCalories();
        Intent intent = new Intent(this, NutritionActivity.class);
        startActivity(intent);
    }

    private void updateCalories() {
        int cals = 0;
        String total = "";
        try{
            FileInputStream fis = openFileInput(calorieFile);
            Scanner sc = new Scanner(fis);
            cals = Integer.valueOf(sc.nextLine());
            total += sc.nextLine();
            sc.close();
            fis.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        try{

            FileOutputStream fos = openFileOutput(calorieFile, MODE_PRIVATE);
            cals += Integer.valueOf(num_cals.getText().toString());
            String fileContent = cals + "\n" + total;
            fos.write(fileContent.getBytes());
            fos.close();
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    private void updateMeals() {

    }

    //initialize meals file
    private void addToTodaysMeals(){
        FileOutputStream fos;
        try {
            //append MEAL

            fos = openFileOutput(todaysMealsFile, MODE_APPEND);
            String currMeal = meal.getText().toString();
            currMeal = currMeal.replace('|', '#');
            String mealInfo = currMeal + "|" + num_cals.getText().toString() + " calories" + "\n";
            fos.write(mealInfo.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), NutritionActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }


}
