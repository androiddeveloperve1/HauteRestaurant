package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import androidx.databinding.library.baseAdapters.BR;
import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemTodayEarningBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class EarnAdapter extends RecyclerView.Adapter<EarnAdapter.MyViewHolder> {
    RecycleItemClickListener listenr;
    private ArrayList<OrderDetailsModel> list;

    public EarnAdapter(ArrayList<OrderDetailsModel> list, RecycleItemClickListener listenr) {
        this.listenr = listenr;
        this.list = list;
    }

    @NonNull
    @Override
    public EarnAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemTodayEarningBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_today_earning, viewGroup, false);
        binding.setItemClickListener(listenr);
        return new EarnAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EarnAdapter.MyViewHolder holder, int i) {

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

        public void bind(OrderDetailsModel data) {
            this.binding.setVariable(BR.model, data);
            this.binding.setVariable(BR.position, getAdapterPosition());
            this.binding.executePendingBindings();
        }

    }
}