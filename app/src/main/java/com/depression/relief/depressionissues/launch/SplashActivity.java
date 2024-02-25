package com.depression.relief.depressionissues.launch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.depression.relief.depressionissues.R;

public class SplashActivity extends AppCompatActivity {
    LottieAnimationView splashloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashloader = findViewById(R.id.splashloader);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashloader.setVisibility(View.GONE);
                Intent intent = new Intent(SplashActivity.this, ChooseProfessionActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}