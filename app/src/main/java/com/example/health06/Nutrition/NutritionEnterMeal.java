package com.example.health06.Nutrition;

import com.example.health06.BaseActivity;
import com.example.health06.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.util.Scanner;


public class NutritionEnterMeal extends BaseActivity {

    EditText num_cals;
    TextView calorie_guide;
    EditText meal;
    Button submit_meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_enter_meal);

        num_cals = findViewById(R.id.num_cals);
        calorie_guide = findViewById(R.id.calorie_guide);
        meal = findViewById(R.id.meal);
        submit_meal = findViewById(R.id.submit_meal);


    }


    public void onClickSubmitMeal(View view) {
        Toast.makeText(this, "Meal Submitted",
                Toast.LENGTH_SHORT).show();
        updateCalories();
        Intent intent = new Intent(this, NutritionActivity.class);
        startActivity(intent);
    }

    private void updateCalories() {
        int cals = 0;
        String total = "";
        try{
            FileInputStream fis = openFileInput("dailyCalories.txt");
            Scanner sc = new Scanner(fis);
            cals = Integer.valueOf(sc.nextLine());
            total += sc.nextLine();
            sc.close();
            fis.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        try{

            FileOutputStream fos = openFileOutput("dailyCalories.txt", MODE_PRIVATE);
            cals += Integer.valueOf(num_cals.getText().toString());
            String fileContent = cals + "\n" + total;
            fos.write(fileContent.getBytes());
            fos.close();
        } catch(IOException e){
            e.printStackTrace();
        }

    }


}
