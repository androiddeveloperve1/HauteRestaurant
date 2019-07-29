package com.app.mylibertarestaurant.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.ItemDescriptionActivity;
import com.app.mylibertarestaurant.adapter.InventoryItemAdapter;
import com.app.mylibertarestaurant.databinding.FragmentInventoryChildBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.InventoryModel;
import com.app.mylibertarestaurant.model.InventoryResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class FragmentInventoryListChild extends Fragment {
    static FragmentInventoryListChild fragmentInventoryListChild;
    FragmentInventoryChildBinding binder;
    private InventoryResponseModel model;
    private InventoryItemAdapter inventoryItemAdapter;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_inventory_child, container, false);
        binder.rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        model = new Gson().fromJson(getArguments().getString("data"), InventoryResponseModel.class);
        Log.e("@@@@Data insid fragnet", getArguments().getString("data"));
        inventoryItemAdapter = new InventoryItemAdapter(new RecycleItemClickListener<InventoryModel>() {
            @Override
            public void onItemClicked(int position, InventoryModel data) {
                Intent mIntent = new Intent(getActivity(), ItemDescriptionActivity.class);
                mIntent.putExtra("data", new Gson().toJson(data));
                mIntent.putExtra("attribute_id", model.get_id());
                startActivityForResult(mIntent, 100);
            }
        }, model.getAttribute_data());


        binder.setAdapter(inventoryItemAdapter);


        View view = binder.getRoot();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // refresh here
        }
    }

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            try {
                inventoryItemAdapter.notifyDataSetChanged();
                Log.e("@@@@@@@", "NOTIFED");
            } catch (Exception e) {

            }

        }
    }*/

    /* public static FragmentInventoryListChild getInsrance()
    {
        if(fragmentInventoryListChild == null)
            fragmentInventoryListChild = new FragmentInventoryListChild();
        return fragmentInventoryListChild;
    }*/




}
