package com.example.health06.Workout;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.health06.BaseActivity;
import com.example.health06.R;

public class CustomWorkout extends BaseActivity {

    TextView activityBox;
    TextView setBox;
    TextView repBox;
    TextView weightBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customworkout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        this.activityBox = findViewById(R.id.editText);
        activityBox.setText("Enter activity name");
        this.setBox = findViewById(R.id.editText2);
        setBox.setText("Enter number of sets");
        this.repBox = findViewById(R.id.editText3);
        repBox.setText("Enter number of reps");
        this.weightBox = findViewById(R.id.editText4);
        weightBox.setText("Enter the weight");


    }
}
