package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.OrderItemDescriptionAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.constants.UrlConstants;
import com.app.mylibertarestaurant.databinding.ActivityOrderDecriptionBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.FragmentTransactionUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderDecriptionActivity extends AppCompatActivity {
    ActivityOrderDecriptionBinding binder;
    @Inject
    APIInterface apiInterface;
    private int tag;
    private OrderDetailsModel orderDetailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_order_decription);
        tag = getIntent().getIntExtra("tag", 0);
        orderDetailsModel = new Gson().fromJson(getIntent().getStringExtra("data"), OrderDetailsModel.class);
        //orderDetail(orderDetailsModel.get_id());
        binder.viewTool.toolbarTitle.setText("ORDER ID :" + orderDetailsModel.getOrder_no());

        if (tag == Constants.FROM_READY_REQUEST_TAG) {
            binder.llOrderRequest.setVisibility(View.GONE);
            binder.tvPickupReady.setVisibility(View.GONE);
            binder.tvVerify.setVisibility(View.VISIBLE);
            binder.clOtp.setVisibility(View.VISIBLE);
        } else if (tag == Constants.FROM_ONGOING_REQUEST_TAG) {
            binder.llOrderRequest.setVisibility(View.GONE);
            binder.tvPickupReady.setVisibility(View.VISIBLE);
            binder.clOtp.setVisibility(View.GONE);
            binder.tvVerify.setVisibility(View.GONE);
        } else {
            binder.llOrderRequest.setVisibility(View.VISIBLE);
            binder.tvPickupReady.setVisibility(View.GONE);
            binder.clOtp.setVisibility(View.GONE);
            binder.tvVerify.setVisibility(View.GONE);
        }
        binder.rvItem.setLayoutManager(new LinearLayoutManager(this));
        binder.setAdapt(new OrderItemDescriptionAdapter(orderDetailsModel.getOrder()));
        binder.setModel(orderDetailsModel);
        binder.setClickHandle(new Click());
        setOtpEditTextListener();
        binder.viewTool.toolbarBack.setOnClickListener((v) -> onBackPressed());
        binder.viewTool.toolbarHelp.setOnClickListener((v) -> startActivity(new Intent(OrderDecriptionActivity.this, HalpActivity.class)));
    }

    private void acceptOrderApi() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(OrderDecriptionActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(OrderDecriptionActivity.this);
        apiInterface.acceptOrder(orderDetailsModel.get_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(OrderDecriptionActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            Intent mIntent = new Intent();
                            mIntent.setAction(Constants.BROADCAST_ORDER_ACCEPT);
                            LocalBroadcastManager.getInstance(OrderDecriptionActivity.this).sendBroadcast(mIntent);
                            onBackPressed();
                        } else {
                            ResponseDialog.showErrorDialog(OrderDecriptionActivity.this, response.getMessage());
                        }

                    }
                });
    }

    private void rejectOrderApi() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(OrderDecriptionActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(OrderDecriptionActivity.this);
        apiInterface.rejectOrder(orderDetailsModel.get_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(OrderDecriptionActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            Intent mIntent = new Intent();
                            mIntent.setAction(Constants.BROADCAST_ORDER_CANCEL);
                            LocalBroadcastManager.getInstance(OrderDecriptionActivity.this).sendBroadcast(mIntent);
                            onBackPressed();
                        } else {
                            ResponseDialog.showErrorDialog(OrderDecriptionActivity.this, response.getMessage());
                        }

                    }
                });
    }

    private void readyForPickupAPI() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(OrderDecriptionActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(OrderDecriptionActivity.this);

        HashMap<String, String> param = new HashMap<>();
        param.put("order_id", orderDetailsModel.get_id());
        param.put("delivery_status", "6");


        apiInterface.readyForPickup(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(OrderDecriptionActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            Intent mIntent = new Intent();
                            mIntent.setAction(Constants.BROADCAST_ORDER_READY_FOR_PICKUP);
                            LocalBroadcastManager.getInstance(OrderDecriptionActivity.this).sendBroadcast(mIntent);
                            onBackPressed();

                        } else {
                            ResponseDialog.showErrorDialog(OrderDecriptionActivity.this, response.getMessage());
                        }

                    }
                });
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

    private void otpVerify(String otp) {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(OrderDecriptionActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(OrderDecriptionActivity.this);
        HashMap<String, String> param = new HashMap<>();
        param.put("driverId", orderDetailsModel.get_id());
        param.put("orderId", orderDetailsModel.get_id());
        param.put("otp", otp);


        apiInterface.readyForPickup(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(OrderDecriptionActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            Intent mIntent = new Intent();
                            mIntent.setAction(Constants.DRIVER_VERIFIED_WITH_OTP);
                            LocalBroadcastManager.getInstance(OrderDecriptionActivity.this).sendBroadcast(mIntent);
                            onBackPressed();
                        } else {
                            ResponseDialog.showErrorDialog(OrderDecriptionActivity.this, response.getMessage());
                        }

                    }
                });
    }

    private void orderDetail(String id) {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(OrderDecriptionActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(OrderDecriptionActivity.this);
        apiInterface.getOrderDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<OrderDetailsModel>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(OrderDecriptionActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<OrderDetailsModel> response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@", "Details" + new Gson().toJson(response));
                        if (response.getStatus().equals("200")) {

                        } else {
                            ResponseDialog.showErrorDialog(OrderDecriptionActivity.this, response.getMessage());
                        }
                    }
                });
    }

    public class Click {
        public void readyForPickup(View v) {
            readyForPickupAPI();
        }

        public void cancelOrder(View v) {
            rejectOrderApi();
        }

        public void acceptOrder(View v) {
            acceptOrderApi();

        }

        public void verifyOtp(View v) {
            String otp = "";
            otp = binder.otp1.getText().toString() + binder.otp2.getText().toString() + binder.otp3.getText().toString() + binder.otp4.getText().toString();
            if (otp.length() >= 4) {
                otpVerify(otp);
            } else {
                Toast.makeText(OrderDecriptionActivity.this, "Please enter 4 digit otp", Toast.LENGTH_SHORT).show();
            }

        }


    }
}
