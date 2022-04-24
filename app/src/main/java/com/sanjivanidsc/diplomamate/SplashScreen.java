package com.sanjivanidsc.diplomamate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.analytics.FirebaseAnalytics;

public class SplashScreen extends AppCompatActivity {

    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, SignInActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable, 2500);


    }

}