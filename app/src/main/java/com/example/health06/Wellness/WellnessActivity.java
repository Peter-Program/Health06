package com.example.health06.Wellness;

import android.os.Bundle;

import com.example.health06.BaseActivity;
import com.example.health06.R;

import androidx.appcompat.app.ActionBar;

public class WellnessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellness);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

    }
}