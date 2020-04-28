package com.example.health06;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    // App bar menu methods start-------------------------------------------------------------------
    // Menu item list is in res/menu/menu_main_activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);                                 // Initializing the app bar menu layout/style
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuMainActivitySettings:                                                     // When clicking on Settings
                return true;

            case R.id.menuMainActivityAbout:
                Toast.makeText(this, "Clicked About",
                        Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menuMainActivityCurrentSettings:
                Intent currentSettingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(currentSettingsIntent);
                return true;

            case android.R.id.home:
                this.finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    // App bar menu methods end---------------------------------------------------------------------


}
