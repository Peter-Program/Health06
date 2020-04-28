package com.example.health06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.health06.Nutrition.NutritionActivity;
import com.example.health06.Wellness.WellnessActivity;
import com.example.health06.Workout.WorkoutActivity;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;

import static com.example.health06.SettingsActivity.MY_GLOBAL_PREFS;
import static com.example.health06.SettingsActivity.PROGRESS_DEFAULT;

public class MainActivity extends BaseActivity {
    private final String TAG = "CodeRunner";

    boolean created = false;
    TextView motivationalText;
    LinkedList<String> ls = new LinkedList<>();
    ProgressBar progressBar;
    TextView progressText;
    String workoutComp = "Workout Completion ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout workoutLayout = findViewById(R.id.workoutLayout);
        LinearLayout nutritionLayout = findViewById(R.id.nutritionLayout);
        LinearLayout wellnessLayout = findViewById(R.id.wellnessLayout);
        motivationalText = findViewById(R.id.motivatTextBox);
        progressBar = findViewById(R.id.progressBarMain);
        progressText = findViewById(R.id.progressPercentage);

        loadQuotes();
        loadProgress();
        setRandomQuote();

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

    private void loadQuotes() {
        ls.add(getString(R.string.Q1));
        ls.add(getString(R.string.Q2));
        ls.add(getString(R.string.Q3));
        ls.add(getString(R.string.Q4));
        ls.add(getString(R.string.Q5));
    }

    private void setRandomQuote() {
        int r = getRandomNumber();
        Log.i(TAG, "random Number is: " + r);
        String s = ls.get(r);
        motivationalText.setText(s);
    }

    private int getRandomNumber() {
        Random random = new Random();
        int r = random.nextInt(ls.size());
        return r;
    }

    private void loadProgress() {
        SharedPreferences prefs =
                getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE);
        int progress = prefs.getInt(getString(R.string.PREF_PROGRESS), PROGRESS_DEFAULT);
        progressBar.setProgress(progress);
        progressText.setText(workoutComp + progressBar.getProgress() + "%");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == REQUEST_CODE) {
            loadProgress();
        }
    }

}
