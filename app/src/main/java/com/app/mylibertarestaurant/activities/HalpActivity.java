package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityHalpBinding;

public class HalpActivity extends AppCompatActivity {
    ActivityHalpBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_halp);
        binder.setClick(new MyClick());
    }

    public class MyClick {
        public void onBack(View v) {
            finish();

        }
    }
}
