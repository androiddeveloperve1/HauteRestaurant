package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.BR;
import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemTodayEarningBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class EarnAdapter extends RecyclerView.Adapter<EarnAdapter.MyViewHolder> {
    RecycleItemClickListener listenr;

    public EarnAdapter(RecycleItemClickListener listenr) {
        this.listenr = listenr;
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
        // holder.bind(list.get(i));
        holder.bind(new Object());
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding databinding) {
            super(databinding.getRoot());
            this.binding = databinding;
        }

        public void bind(Object data) {
            // this.binding.setVariable(BR.data, data);
            this.binding.setVariable(BR.position, getAdapterPosition());
            this.binding.executePendingBindings();
        }

    }
}