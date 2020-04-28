package com.example.health06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import static com.example.health06.SettingsActivity.MY_GLOBAL_PREFS;

public class clearProgressPopUp extends BaseActivity {

    Button cancel;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_progress_pop_up);

        cancel = findViewById(R.id.cancelBtn);
        ok = findViewById(R.id.okBtn);

        // Display parameters
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // Setting popup window activity to be 80% of the width of the device
        // and 70% of the height of the device
        getWindow().setLayout((int)(width * 0.8), (int) (height * 0.7));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor =
                        getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE).edit();
                editor.putInt(getString(R.string.PREF_PROGRESS), 0);
                editor.apply();
            }
        });
    }

    public void loadDefaultOnClick(View view) {
        finish();
    }
}
