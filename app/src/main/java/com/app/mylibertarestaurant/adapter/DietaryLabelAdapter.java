package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemDietaryLabelBinding;
import com.app.mylibertarestaurant.databinding.ItemFoodTypeAvailableBinding;
import com.app.mylibertarestaurant.model.newP.DietryLabelModel;
import com.app.mylibertarestaurant.model.newP.MealAvailabilityModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class DietaryLabelAdapter extends RecyclerView.Adapter<DietaryLabelAdapter.MyViewHolder> {
    private ArrayList<DietryLabelModel> list;

    public DietaryLabelAdapter(ArrayList<DietryLabelModel>  list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DietaryLabelAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemDietaryLabelBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_dietary_label, viewGroup, false);
        return new DietaryLabelAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DietaryLabelAdapter.MyViewHolder holder, int i) {

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

        public void bind(DietryLabelModel data) {
            this.binding.setVariable(BR.model, data);
            this.binding.executePendingBindings();
        }

    }
}