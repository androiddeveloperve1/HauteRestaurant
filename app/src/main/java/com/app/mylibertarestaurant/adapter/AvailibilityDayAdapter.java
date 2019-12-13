package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemAvailDayBinding;
import com.app.mylibertarestaurant.model.newP.DayOfWeekModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class AvailibilityDayAdapter extends RecyclerView.Adapter<AvailibilityDayAdapter.MyViewHolder> {
    private ArrayList<DayOfWeekModel>  list;

    public AvailibilityDayAdapter(ArrayList<DayOfWeekModel>  list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AvailibilityDayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemAvailDayBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_avail_day, viewGroup, false);
        return new AvailibilityDayAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailibilityDayAdapter.MyViewHolder holder, int i) {

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

        public void bind(DayOfWeekModel data) {
            this.binding.setVariable(BR.model, data);
            this.binding.executePendingBindings();
        }

    }
}