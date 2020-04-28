package com.example.health06;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsActivity extends BaseActivity {
    private final String TAG = "CodeRunner";
    SharedPreferences prefs;
    public static String MY_GLOBAL_PREFS = "my_global_prefs";

    public static String WEIGHT_DEFAULT = "150";
    public static String HEIGHT_DEFAULT = "6";
    public static int PROGRESS_DEFAULT = 23;

    EditText weightText;
    EditText heightText;
    EditText progressText;
    TextView progressView;
    Button clrBtn;
    Button setBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        clrBtn = findViewById(R.id.btnSetProgress);
        setBtn = findViewById(R.id.btnSetProgress);
        weightText = findViewById(R.id.weightText);
        heightText = findViewById(R.id.heightText);
        progressText = findViewById(R.id.progressText);
        progressView = findViewById(R.id.progressView);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        prefs = getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE);

        loadPref();

        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), clearProgressPopUp.class);
                startActivity(intent);
            }
        });

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = Integer.parseInt(progressText.getText().toString());
                if (p >= 0 && p < 100) {
                    progressView.setText(p + " %");
                } else {
                    Toast.makeText(v.getContext(), "Must be a value between 0 and 100",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Saving preferences
        savePref();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private int getProgressValue() {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(progressView.getText().toString());
        if(m.find())
            return Integer.parseInt(m.group(0));
        return 0;
    }

    void savePref() {
        SharedPreferences.Editor editor =
                getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE).edit();
        editor.putString(getString(R.string.PREF_HEIGHT), heightText.getText().toString());
        editor.putString(getString(R.string.PREF_WEIGHT), weightText.getText().toString());
        editor.putInt(getString(R.string.PREF_PROGRESS), getProgressValue());
        editor.apply();
    }

    void loadPref() {
        // Getting the data from SharedPreferences
        SharedPreferences prefs =
                getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE);
        // example of getting a string
        String weight = prefs.getString(getString(R.string.PREF_WEIGHT), WEIGHT_DEFAULT);
        weightText.setText(weight);

        String height = prefs.getString(getString(R.string.PREF_HEIGHT), HEIGHT_DEFAULT);
        heightText.setText(height);

        int progress = prefs.getInt(getString(R.string.PREF_PROGRESS), PROGRESS_DEFAULT);
        progressView.setText(progress + " %");
        progressText.setText(progress + "");
//        String thresh = prefs.getString(getString(R.string.PREF_NOTIFICATION_THRESHOLD), THRESHOLD_DEFAULT);
//        threshold.setText(thresh);
    }
}
