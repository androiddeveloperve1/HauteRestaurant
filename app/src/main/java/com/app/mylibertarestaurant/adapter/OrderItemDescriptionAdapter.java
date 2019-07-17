package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.OrderItemDescriptionBinding;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class OrderItemDescriptionAdapter extends RecyclerView.Adapter<OrderItemDescriptionAdapter.MyViewHolder> {

    public OrderItemDescriptionAdapter() {
    }

    @NonNull
    @Override
    public OrderItemDescriptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        OrderItemDescriptionBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_item_description, viewGroup, false);
        return new OrderItemDescriptionAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemDescriptionAdapter.MyViewHolder holder, int i) {
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
            this.binding.executePendingBindings();
        }

    }
}