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
    TextView progressView;
    Button clrBtn;
    Button restoreBtn;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        clrBtn = findViewById(R.id.btnClearProgress2);
        restoreBtn = findViewById(R.id.btnSetProgress);
        weightText = findViewById(R.id.weightText);
        heightText = findViewById(R.id.heightText);
        progressView = findViewById(R.id.progressView);
        saveBtn = findViewById(R.id.settingsSaveBtn);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        prefs = getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE);

        loadPref();

        setResult(REQUEST_CODE);

        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressView.setText(0 + "%");
                Toast.makeText(v.getContext(), "Cleared Progress",
                        Toast.LENGTH_SHORT).show();
            }
        });

        restoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressView.setText(PROGRESS_DEFAULT + "%");
                Toast.makeText(v.getContext(), "Restored Progress",
                        Toast.LENGTH_SHORT).show();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePref();
                Toast.makeText(v.getContext(), "Saved Data",
                        Toast.LENGTH_SHORT).show();
                finish();
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
//        String thresh = prefs.getString(getString(R.string.PREF_NOTIFICATION_THRESHOLD), THRESHOLD_DEFAULT);
//        threshold.setText(thresh);
    }
}
