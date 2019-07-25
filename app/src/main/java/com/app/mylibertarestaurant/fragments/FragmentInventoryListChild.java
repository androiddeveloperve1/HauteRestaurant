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

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class FragmentInventoryListChild extends Fragment {
    FragmentInventoryChildBinding binder;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_inventory_child, container, false);
        binder.rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        binder.setAdapter(new InventoryItemAdapter(new RecycleItemClickListener() {
            @Override
            public void onItemClicked(int position, Object data) {
                Intent mIntent = new Intent(getActivity(), ItemDescriptionActivity.class);
                startActivityForResult(mIntent, 100);
            }
        }));

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
