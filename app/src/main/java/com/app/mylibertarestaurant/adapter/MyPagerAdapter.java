package com.app.mylibertarestaurant.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.mylibertarestaurant.fragments.OngoingRequestFragment;
import com.app.mylibertarestaurant.fragments.OrderRequestFragment;
import com.app.mylibertarestaurant.fragments.ReadyOrderFragment;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<OrderDetailsModel> newOrderRequest;
    private ArrayList<OrderDetailsModel> onGoingOrder;
    private ArrayList<OrderDetailsModel> readyForPickupOrder;

    public MyPagerAdapter(FragmentManager fm, ArrayList<OrderDetailsModel> newOrderRequest, ArrayList<OrderDetailsModel> onGoingOrder, ArrayList<OrderDetailsModel> readyForPickupOrder) {
        super(fm);
        this.newOrderRequest = newOrderRequest;
        this.onGoingOrder = onGoingOrder;
        this.readyForPickupOrder = readyForPickupOrder;
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment frag = null;
        Bundle b = new Bundle();
        switch (pos) {
            case 0:
                b.putString("data", new Gson().toJson(newOrderRequest));
                frag = new OrderRequestFragment();
                break;
            case 1:
                b.putString("data", new Gson().toJson(onGoingOrder));
                frag = new OngoingRequestFragment();
                break;
            case 2:
                b.putString("data", new Gson().toJson(readyForPickupOrder));
                frag = new ReadyOrderFragment();
                break;

        }
        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 3;
    }


}