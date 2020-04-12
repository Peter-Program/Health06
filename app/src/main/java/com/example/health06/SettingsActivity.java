package com.example.health06;

import androidx.appcompat.app.ActionBar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class SettingsActivity extends BaseActivity {
    SharedPreferences prefs;
    public static String MY_GLOBAL_PREFS = "my_global_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        prefs = getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE);

        loadPref();
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

    void savePref() {
        SharedPreferences.Editor editor =
                getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE).edit();
        // can put various data types to be stored like a hash, with KEY/VALUE pairs
//        editor.putString(getString(R.string.PREF_NOTIFICATION_THRESHOLD), threshold.getText().toString());
        // must apply to commit the data
        editor.apply();
    }

    void loadPref() {
        // Getting the data from SharedPreferences
        SharedPreferences prefs =
                getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE);
        // example of getting a string
//        String thresh = prefs.getString(getString(R.string.PREF_NOTIFICATION_THRESHOLD), THRESHOLD_DEFAULT);
//        threshold.setText(thresh);
    }
}
