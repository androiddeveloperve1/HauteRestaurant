package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityEnterOtpBinding;

public class EnterOtpActivity extends AppCompatActivity {
ActivityEnterOtpBinding binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder= DataBindingUtil.setContentView(this,R.layout.activity_enter_otp);
        binder.setClick(new Presenter());
    }


    public class Presenter {
        public void doContinue(View v) {
            startActivity(new Intent(EnterOtpActivity.this, MainActivity.class));

        }

    }
}
