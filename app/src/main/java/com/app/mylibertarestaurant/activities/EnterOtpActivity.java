package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.constants.UrlConstants;
import com.app.mylibertarestaurant.databinding.ActivityEnterOtpBinding;
import com.app.mylibertarestaurant.prefes.MySharedPreference;

import java.util.HashMap;

public class EnterOtpActivity extends AppCompatActivity {
    ActivityEnterOtpBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_enter_otp);
        binder.setClick(new Presenter());
        setOtpEditTextListener();
    }

    void setOtpEditTextListener() {

        binder.otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    binder.otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        binder.otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    binder.otp3.requestFocus();
                }
                if (i2 == 0) {
                    binder.otp1.requestFocus(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binder.otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    binder.otp4.requestFocus();
                }
                if (i2 == 0) {
                    binder.otp2.requestFocus(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binder.otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 == 0) {
                    binder.otp3.requestFocus(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public class Presenter {
        public void doContinue(View v) {
            String otp = "";
            otp = binder.otp1.getText().toString() + binder.otp2.getText().toString() + binder.otp3.getText().toString() + binder.otp4.getText().toString();
            if (otp.length() >= 4) {
                HashMap<String, String> param = new HashMap<>();
                param.put("deviceId", MySharedPreference.getInstance(EnterOtpActivity.this).getFCM());
                param.put("deviceType", UrlConstants.DEVICE_TYPE_KEY);
                param.put("otp", otp);
            } else {
                Toast.makeText(EnterOtpActivity.this, "Please enter 4 digit otp", Toast.LENGTH_SHORT).show();
            }
            //startActivity(new Intent(EnterOtpActivity.this, MainActivity.class));

        }

    }

}
