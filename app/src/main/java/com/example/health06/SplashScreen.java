package com.example.health06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.health06.SettingsActivity.MY_GLOBAL_PREFS;
import static com.example.health06.SettingsActivity.WEIGHT_DEFAULT;

/*
 * So, If user has granted us all permissions that we need then before launching into the main
 * activity we should use an Async task to load the data from the files and Load the data from
 * the SQLite database
 */

/*
 * Permission checks,
 *  1. if deny all then finish and state that you must grant permission
 *  2. if not every single permission is granted then finish and state that you must grant permission
 *  3. if all permissions are granted then launch main activity
 */

public class SplashScreen extends AppCompatActivity {
    private final String TAG = "CodeRunner";

    private static final int PERMISSION_ALL = 2000;
    private ProgressBar progressBar;                                                                // Has a max value of 100
    private int progressBarUpdateValue = 0;
    private String textUpdateData[] = {"Workout", "Workout", "Nutrition", "Wellness"};
    private int imageUpdateData[] = {R.drawable.ic_fitness_center_black_24dp,
            R.drawable.ic_fitness_center_black_24dp,
            R.drawable.ic_restaurant_menu_black_24dp,
            R.drawable.ic_local_hospital_black_24dp};
    TextView splashText;
    ImageView splashImage;

    // Put all permissions that you want here and in the manifest too
    String[] PERMISSIONS = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        splashText = findViewById(R.id.splashText);
        splashImage = findViewById(R.id.splashImage);

        checkingPermissions();
    }

    private void checkingPermissions() {

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
            loadData();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        // Need to double check if we have permission in the event the user leaves at least one
        // permission un granted
        if (hasPermissions(this, PERMISSIONS)) {
            loadData();
        } else {
            Toast.makeText(this, "You Must Grant Permission",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    //----------------------------------------------------------------------------------------------

    void loadData() {

        progressBar.setVisibility(View.VISIBLE);
        MyTask task = new MyTask();
        task.execute("dataFile1.txt", "dataFile2.txt");
    }

    void launchMainActivity() {

        // start the next activity.
        Log.i(TAG, "Starting Main Activity");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        // Finish Splash Activity
        finish();
    }

    // The AsyncTask parameters are
    // Type1 = Input, Type2 = on Progress Update, Type3 = When task complete
    class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {

            int i = 0;

            final int finalI = i;
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    splashText.setText(textUpdateData[finalI]);
                    splashImage.setImageResource(imageUpdateData[finalI]);
                }
            });
            i++;

            for (String file: strings) {
                Log.i(TAG, "Do in Background: " + file);
                // Read data of "file" here
                publishProgress(25);

                final int finalI1 = i;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        splashText.setText(textUpdateData[finalI1]);
                        splashImage.setImageResource(imageUpdateData[finalI1]);
                    }
                });

                i++;

                // Simulating large files
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(25);

                final int finalI2 = i;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        splashText.setText(textUpdateData[finalI2]);
                        splashImage.setImageResource(imageUpdateData[finalI2]);
                    }
                });

            }
            return "Threads are all done";
        }

        // Runs on the Main thread so It can make changes to the UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Update on Progress: " + values[0]);
            progressBarUpdateValue += values[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                progressBar.setProgress(progressBarUpdateValue, true);
            else
                progressBar.setProgress(progressBarUpdateValue);

        }

        // This function deals with the return value of doInBackground
        // Runs on the Main thread so It can make changes to the UI
        @Override
        protected void onPostExecute(String  s) {
            Log.i(TAG, s);
            launchMainActivity();
        }
    }
}