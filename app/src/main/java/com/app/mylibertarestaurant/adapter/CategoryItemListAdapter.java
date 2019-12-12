package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemMenuOptionBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryItemModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class CategoryItemListAdapter extends RecyclerView.Adapter<CategoryItemListAdapter.MyViewHolder> {
    RecycleItemClickListener listenr;
    private ArrayList<RestaurantCategoryItemModel> list;

    public CategoryItemListAdapter(RecycleItemClickListener listenr, ArrayList<RestaurantCategoryItemModel> list) {
        this.listenr = listenr;
        this.list =list;
    }

    @NonNull
    @Override
    public CategoryItemListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemMenuOptionBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_menu_option, viewGroup, false);
        binding.setClickListener(listenr);
        return new CategoryItemListAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemListAdapter.MyViewHolder holder, int i) {
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

        public void bind(RestaurantCategoryItemModel data) {
            this.binding.setVariable(BR.model, data);
            this.binding.setVariable(BR.position, getAdapterPosition());
            this.binding.executePendingBindings();
        }

    }

}