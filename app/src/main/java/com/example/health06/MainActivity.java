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
import android.widget.Toast;

import com.example.health06.Nutrition.NutritionActivity;
import com.example.health06.Workout.WorkoutActivity;

import java.io.*;

public class MainActivity extends BaseActivity {

    boolean created = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button workoutButton = findViewById(R.id.workoutButton);

        Button nutritionButton = findViewById(R.id.nutritionButton);

    }

    public void onClickWorkout(View view) {
        Toast.makeText(this, "Clicked Workout",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);

    }

    public void onClickNutrition(View view) {
        initializeCalorieFile();
        Intent intent = new Intent(this, NutritionActivity.class);
        startActivity(intent);
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
