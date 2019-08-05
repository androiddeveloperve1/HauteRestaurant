package com.app.mylibertarestaurant.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityForgotPasswordBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.AppUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */
public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binder;
    @Inject
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        binder.setHandler(new Listener());

    }

    private void sendOtp(HashMap<String, String> param) {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(this);
        ((MyApplication) getApplication()).getConfiguration().inject(this);
        apiInterface.forGotPass(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ForgotPasswordActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            finish();
                            Toast.makeText(ForgotPasswordActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            ResponseDialog.showErrorDialog(ForgotPasswordActivity.this, response.getMessage());
                        }
                    }
                });
    }

    public class Listener {
        public void onSend(View e) {
            if (binder.etEmail.getText().toString().trim().length() <= 0) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter email ID", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!AppUtils.eMailValidation(binder.etEmail.getText().toString().trim())) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter the valid email ID", Toast.LENGTH_SHORT).show();
                return;
            }
            HashMap<String, String> param = new HashMap<>();
            param.put("email", binder.etEmail.getText().toString().trim());
            sendOtp(param);

        }

        public void onBack(View e) {
            finish();
        }

    }
}
