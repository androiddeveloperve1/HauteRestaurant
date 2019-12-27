package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemDayofweekBinding;
import com.app.mylibertarestaurant.databinding.ItemDietaryBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.newP.DayOfWeekModel;
import com.app.mylibertarestaurant.model.newP.DietaryItemModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class WeekdayAdapter extends RecyclerView.Adapter<WeekdayAdapter.MyViewHolder> {
    private ArrayList<DayOfWeekModel> list;
    RecycleItemClickListener listenr;

    public WeekdayAdapter(RecycleItemClickListener listenr,ArrayList<DayOfWeekModel>  list) {
        this.list = list;
        this.listenr=listenr;
    }

    @NonNull
    @Override
    public WeekdayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemDayofweekBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_dayofweek, viewGroup, false);
        binding.setListener(listenr);
        return new WeekdayAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekdayAdapter.MyViewHolder holder, int i) {

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
            this.binding.setVariable(BR.position, getAdapterPosition());
            this.binding.executePendingBindings();
        }

    }
}