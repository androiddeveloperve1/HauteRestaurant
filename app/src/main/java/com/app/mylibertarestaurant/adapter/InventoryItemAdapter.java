package com.app.mylibertarestaurant.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.BR;
import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemInventoryBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.InventoryModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class InventoryItemAdapter extends RecyclerView.Adapter<InventoryItemAdapter.MyViewHolder> {
    RecycleItemClickListener listenr;
    private ArrayList<InventoryModel> list;

    public InventoryItemAdapter(RecycleItemClickListener listenr, ArrayList<InventoryModel> list) {
        this.listenr = listenr;
        this.list =list;
    }

    @NonNull
    @Override
    public InventoryItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemInventoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_inventory, viewGroup, false);
        binding.setClickListener(listenr);
        return new InventoryItemAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryItemAdapter.MyViewHolder holder, int i) {
        holder.bind(list.get(i));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding databinding) {
            super(databinding.getRoot());
            this.binding = databinding;
        }

        public void bind(InventoryModel data) {
            this.binding.setVariable(BR.model, data);
            this.binding.setVariable(BR.position, getAdapterPosition());
            this.binding.executePendingBindings();
        }

    }

}