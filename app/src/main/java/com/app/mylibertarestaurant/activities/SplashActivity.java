package com.app.mylibertarestaurant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.prefes.MySharedPreference;

public class SplashActivity extends AppCompatActivity {
    ActivityRunnable runnable;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        runnable = new ActivityRunnable();
        handler.postDelayed(runnable, 3000);
    }

    private class ActivityRunnable implements Runnable {
        @Override
        public void run() {
            RestaurantDetailModel model = MySharedPreference.getInstance(SplashActivity.this).getUser();
            if (model != null) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                /*if (model.getIs_mobile_verify().equals("1")) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, EnterOtpActivity.class));
                    finish();
                }*/
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }
    }
}
