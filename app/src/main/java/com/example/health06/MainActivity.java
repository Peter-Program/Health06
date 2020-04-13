package com.example.health06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.health06.Nutrition.NutritionActivity;
import com.example.health06.Wellness.WellnessActivity;
import com.example.health06.Workout.WorkoutActivity;

import java.io.*;

public class MainActivity extends BaseActivity {

    boolean created = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout workoutLayout = findViewById(R.id.workoutLayout);
        LinearLayout nutritionLayout = findViewById(R.id.nutritionLayout);
        LinearLayout wellnessLayout = findViewById(R.id.wellnessLayout);


        workoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WorkoutActivity.class);
                startActivity(intent);
            }
        });

        nutritionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NutritionActivity.class);
                startActivity(intent);
            }
        });

        wellnessLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WellnessActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeCalorieFile() {

        String fileName = "dailyCalories.txt";
        File file = new File(getFilesDir(), fileName);
        FileOutputStream fos;

        try {
                if(created == false){
                fos = new FileOutputStream(file);
                fos.write("0\n2500\n".getBytes());
                fos.close();
                created = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
