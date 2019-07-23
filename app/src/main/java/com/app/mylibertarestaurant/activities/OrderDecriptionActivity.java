package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.OrderItemDescriptionAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.ActivityOrderDecriptionBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.utils.FragmentTransactionUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

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
        binder.viewTool.toolbarTitle.setText("ORDER ID :" + orderDetailsModel.getOrder_no());

        if (tag == Constants.FROM_READY_REQUEST_TAG) {
            binder.llOrderRequest.setVisibility(View.GONE);
            binder.tvPickupReady.setVisibility(View.GONE);
            binder.clOtp.setVisibility(View.VISIBLE);
        } else if (tag == Constants.FROM_ONGOING_REQUEST_TAG) {
            binder.llOrderRequest.setVisibility(View.GONE);
            binder.tvPickupReady.setVisibility(View.VISIBLE);
            binder.clOtp.setVisibility(View.GONE);
        } else {
            binder.llOrderRequest.setVisibility(View.VISIBLE);
            binder.tvPickupReady.setVisibility(View.GONE);
            binder.clOtp.setVisibility(View.GONE);
        }
        binder.rvItem.setLayoutManager(new LinearLayoutManager(this));
        binder.setAdapt(new OrderItemDescriptionAdapter(orderDetailsModel.getOrder()));
        binder.setModel(orderDetailsModel);
        binder.setClickHandle(new Click());
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
                        Log.e("@@@@@@@@@@", ""+ new Gson().toJson(response));
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
                        Log.e("@@@@@@@@@@", ""+ new Gson().toJson(response));
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


    public class Click {
        public void readyForPickup(View v) {
            /*Intent mIntent = new Intent();
            mIntent.setAction(Constants.BROADCAST_ORDER_READY_FOR_PICKUP);
            LocalBroadcastManager.getInstance(OrderDecriptionActivity.this).sendBroadcast(mIntent);
            onBackPressed();*/
        }

        public void cancelOrder(View v) {
            rejectOrderApi();
        }

        public void acceptOrder(View v) {
            acceptOrderApi();

        }

    }
}
