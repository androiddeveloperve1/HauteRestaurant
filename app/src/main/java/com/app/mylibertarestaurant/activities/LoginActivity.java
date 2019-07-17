package com.app.mylibertarestaurant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binder.setClick(new Presenter());
    }

    public class Presenter {
        public void doContinue(View v) {
            if(binder.etId.getText().toString().trim().length()>0){
                startActivity(new Intent(LoginActivity.this, EnterOtpActivity.class));
            }else {
                Toast.makeText(LoginActivity.this, "Please enter the restaurant id", Toast.LENGTH_SHORT).show();
            }

        }

        public void contactNow(View v) {

        }


    }

}
