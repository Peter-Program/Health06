package com.example.health06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

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
                Toast.makeText(this, "Clicked Settings",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuMainActivityAbout:
                //Stuff
                Toast.makeText(this, "Clicked About",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuMainActivityCurrentSettings:
                //Stuff
                Toast.makeText(this, "Clicked Current Settings",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    // App bar menu methods end---------------------------------------------------------------------
}
