package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.OrderItemDescriptionAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.ActivityOrderDecriptionBinding;

public class OrderDecriptionActivity extends AppCompatActivity {
    ActivityOrderDecriptionBinding binder;
    private int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_order_decription);
        tag = getIntent().getIntExtra("tag", 0);
        binder.viewTool.toolbarTitle.setText("ORDER ID : #Im5435434");

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
        binder.setAdapt(new OrderItemDescriptionAdapter());
        binder.viewTool.toolbarBack.setOnClickListener((v) -> finish());
        binder.viewTool.toolbarHelp.setOnClickListener((v) -> startActivity(new Intent(OrderDecriptionActivity.this, HalpActivity.class)));
    }


    public class Click {
        public void readyForPickup(View v) {
        }

        public void cancelOrder(View v) {
        }

        public void acceptOrder(View v) {
        }

    }
}
