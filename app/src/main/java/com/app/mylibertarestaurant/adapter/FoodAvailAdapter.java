package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemFoodAvailBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.newP.FoodAvailModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class FoodAvailAdapter extends RecyclerView.Adapter<FoodAvailAdapter.MyViewHolder> {
    RecycleItemClickListener listenr;
    private ArrayList<FoodAvailModel> list;

    public FoodAvailAdapter(RecycleItemClickListener listenr, ArrayList<FoodAvailModel> list) {
        this.listenr = listenr;
        this.list = list;
    }

    @NonNull
    @Override
    public FoodAvailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemFoodAvailBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_food_avail, viewGroup, false);
        binding.setListener(listenr);
        return new FoodAvailAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAvailAdapter.MyViewHolder holder, int i) {
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

        public void bind(FoodAvailModel data) {
            this.binding.setVariable(BR.model, data);
            this.binding.setVariable(BR.position, getAdapterPosition());
            this.binding.executePendingBindings();
        }

    }

}