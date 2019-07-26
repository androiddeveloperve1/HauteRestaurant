package com.app.mylibertarestaurant.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.OrderDecriptionActivity;
import com.app.mylibertarestaurant.adapter.RequestOrderItemAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.FragmentOngoingOrderBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class OngoingRequestFragment extends Fragment {
    FragmentOngoingOrderBinding binder;
    static OngoingRequestFragment orderRequestFragment;
    private ArrayList<OrderDetailsModel> order;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_ongoing_order, container, false);
        binder.rvItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        order = new Gson().fromJson(getArguments().getString("data"), new TypeToken<ArrayList<OrderDetailsModel>>() {
        }.getType());


        if (order != null && order.size() > 0) {
            binder.tvNoData.setVisibility(View.GONE);
            binder.rvItems.setVisibility(View.VISIBLE);

            binder.setAdp(new RequestOrderItemAdapter(order, new RecycleItemClickListener<OrderDetailsModel>() {
                @Override
                public void onItemClickedWithTag(int position, OrderDetailsModel data, int tag) {
                    Intent mIntent = new Intent(getActivity(), OrderDecriptionActivity.class);
                    mIntent.putExtra("tag", tag);
                    mIntent.putExtra("id", data.get_id());
                    startActivity(mIntent);
                }
            }, Constants.FROM_ONGOING_REQUEST_TAG));

        } else {

            binder.tvNoData.setVisibility(View.VISIBLE);
            binder.rvItems.setVisibility(View.GONE);
        }


        View view = binder.getRoot();
        orderRequestFragment = this;
        return view;
    }

    public static OngoingRequestFragment getInstance()
    {
        if(orderRequestFragment == null)
            orderRequestFragment = new OngoingRequestFragment();
        return orderRequestFragment;

    }
}
