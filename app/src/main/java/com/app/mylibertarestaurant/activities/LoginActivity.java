package com.app.mylibertarestaurant.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityLoginBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.AppUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binder;
    @Inject
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binder.setClick(new Presenter());
    }

    private void getDataFromServer(final HashMap param) {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(LoginActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(LoginActivity.this);
        apiInterface.login(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<RestaurantDetailModel>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(LoginActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<RestaurantDetailModel> response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            MySharedPreference.getInstance(LoginActivity.this).setUser(response.getData());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                           /*if (response.getIs_profile_complete().equals("1")) {

                                if (response.getData().getIs_mobile_verify().equals("1")) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(LoginActivity.this, EnterOtpActivity.class));
                                }

                            } else {
                                ResponseDialog.showErrorDialog(LoginActivity.this, response.getMessage());
                            }*/
                        } else {
                            ResponseDialog.showErrorDialog(LoginActivity.this, response.getMessage());
                        }

                    }
                });
    }

    public class Presenter {
        public void doContinue(View v) {
            if (binder.etId.getText().toString().trim().length() > 0) {
                if (AppUtils.eMailValidation(binder.etId.getText().toString().trim())) {
                    if (binder.etPass.getText().toString().trim().length() > 0) {
                        HashMap<String, String> param = new HashMap<>();
                        param.put("email", binder.etId.getText().toString().trim());
                        param.put("password", binder.etPass.getText().toString().trim());
                        getDataFromServer(param);

                    } else {
                        Toast.makeText(LoginActivity.this, "Please enter the passsword", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Please enter the valid email id", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(LoginActivity.this, "Please enter the email id", Toast.LENGTH_SHORT).show();
            }

        }

        public void contactNow(View v) {

        }

        public void forgot(View v) {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        }


    }
}
