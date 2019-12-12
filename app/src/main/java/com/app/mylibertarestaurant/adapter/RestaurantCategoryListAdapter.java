package com.app.mylibertarestaurant.adapter;



import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemRestaurantCategoryBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.inventorynew.InventoryModelNew;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class RestaurantCategoryListAdapter extends RecyclerView.Adapter<RestaurantCategoryListAdapter.MyViewHolder> {
    RecycleItemClickListener listenr;
    private ArrayList<RestaurantCategoryModel> list;

    public RestaurantCategoryListAdapter(RecycleItemClickListener listenr, ArrayList<RestaurantCategoryModel> list) {
        this.listenr = listenr;
        this.list =list;
    }

    @NonNull
    @Override
    public RestaurantCategoryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemRestaurantCategoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_restaurant_category, viewGroup, false);
        binding.setListener(listenr);
        return new RestaurantCategoryListAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantCategoryListAdapter.MyViewHolder holder, int i) {
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

        public void bind(RestaurantCategoryModel data) {
            this.binding.setVariable(BR.model, data);
            this.binding.setVariable(BR.position, getAdapterPosition());
            this.binding.executePendingBindings();
        }

    }

}