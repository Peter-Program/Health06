package com.example.health06.Workout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.health06.BaseActivity;
import com.example.health06.R;

public class WorkoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        TextView workoutHomeText = findViewById(R.id.textView5);
        workoutHomeText.setText("Workout Home");


    }

    public void onClickEnterCustomWorkout(View view) {
        Intent intent = new Intent(this, CustomWorkout.class);
        startActivity(intent);
    }

    public void onClickEnterTodaysWorkout(View view) {
        Intent intent = new Intent(this, TodaysWorkout.class);
        startActivity(intent);
    }
}
