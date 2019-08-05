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
import com.app.mylibertarestaurant.activities.ItemDescriptionActivity;
import com.app.mylibertarestaurant.adapter.InventoryItemAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.FragmentInventoryChildBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.InventoryModel;
import com.app.mylibertarestaurant.model.InventoryResponseModel;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class FragmentInventoryListChild extends Fragment {
    private FragmentInventoryChildBinding binder;
    private InventoryItemAdapter inventoryItemAdapter;
    private InventoryResponseModel model;
    private ArrayList<InventoryModel> list = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_inventory_child, container, false);
        binder.rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.e("@@@@@@@", "CALLED");


        inventoryItemAdapter = new InventoryItemAdapter(new RecycleItemClickListener<InventoryModel>() {
            @Override
            public void onItemClicked(int position, InventoryModel data) {
                Intent mIntent = new Intent(getActivity(), ItemDescriptionActivity.class);
                mIntent.putExtra("data", new Gson().toJson(data));
                mIntent.putExtra("attribute_id", model.get_id());
                startActivityForResult(mIntent, 100);
            }
        }, list);
        binder.setAdapter(inventoryItemAdapter);

        View view = binder.getRoot();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            getParentFragment().onActivityResult(requestCode, resultCode, data);
        }

    }


    public void doFilter(InventoryResponseModel model) {

        this.model = model;
        Log.e("@@@@@@@", "Filter");
        setData(model.getAttribute_data());

        int filter = MySharedPreference.getInstance(getActivity()).getFilter();
        if (filter == Constants.FILTER_ALL) {
            setData(model.getAttribute_data());
        } else if (filter == Constants.FILTER_VEG) {
            ArrayList<InventoryModel> listVeg = new ArrayList<>();
            for (int i = 0; i < model.getAttribute_data().size(); i++) {
                if (model.getAttribute_data().get(i).getFood_type().equals("veg")) {
                    listVeg.add(model.getAttribute_data().get(i));
                }
            }
            setData(listVeg);
        } else if (filter == Constants.FILTER_NON_VEG) {
            ArrayList<InventoryModel> listNonVeg = new ArrayList<>();
            for (int i = 0; i < model.getAttribute_data().size(); i++) {
                if (model.getAttribute_data().get(i).getFood_type().equals("non-veg")) {
                    listNonVeg.add(model.getAttribute_data().get(i));
                }
            }
            setData(listNonVeg);
        }

    }

    void setData(ArrayList<InventoryModel> list) {
        this.list.clear();
        this.list.addAll(list);
        if (list.size() > 0) {
            binder.nodata.setVisibility(View.GONE);
            binder.rvItem.setVisibility(View.VISIBLE);
            inventoryItemAdapter.notifyDataSetChanged();

        } else {
            binder.nodata.setVisibility(View.VISIBLE);
            binder.rvItem.setVisibility(View.GONE);

        }

    }



}