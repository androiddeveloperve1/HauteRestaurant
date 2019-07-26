package com.app.mylibertarestaurant.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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
    FragmentInventoryChildBinding binder;

    private InventoryResponseModel model;
    private ArrayList<InventoryModel> list;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_inventory_child, container, false);
        binder.rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        model = new Gson().fromJson(getArguments().getString("data"), InventoryResponseModel.class);
        binder.setAdapter(new InventoryItemAdapter(new RecycleItemClickListener<InventoryModel>() {
            @Override
            public void onItemClicked(int position, InventoryModel data) {
                Intent mIntent = new Intent(getActivity(), ItemDescriptionActivity.class);
                mIntent.putExtra("data", new Gson().toJson(data));
                mIntent.putExtra("id", model.get_id());
                startActivityForResult(mIntent, 100);
            }
        }, model.getAttribute_data()));
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
}
