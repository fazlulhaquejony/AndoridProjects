package com.example.fuhad.mycitymyenvironment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;
    private SharedPreferences dataSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                dataSave = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                if (!dataSave.getBoolean("LoggedIn", false)) {

                    startActivity(new Intent(splash.this, LoginActivity.class));
                    finish();

                } else {
                    startActivity(new Intent(splash.this, MainActivity.class));
                    finish();
                }

                // close this activity
                //finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
