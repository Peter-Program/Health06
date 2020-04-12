package com.example.health06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends BaseActivity {

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
}
