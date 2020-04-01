package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import androidx.databinding.library.baseAdapters.BR;
import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemOrderBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;


import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class RequestOrderItemAdapter extends RecyclerView.Adapter<RequestOrderItemAdapter.MyViewHolder> {
    RecycleItemClickListener listenr;
    private int tag;
    private ArrayList<OrderDetailsModel>  order;

    public RequestOrderItemAdapter(ArrayList<OrderDetailsModel> order, RecycleItemClickListener listenr, int tag) {
        this.listenr = listenr;
        this.tag = tag;
        this.order = order;
    }

    @NonNull
    @Override
    public RequestOrderItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemOrderBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_order, viewGroup, false);
        binding.setItemClickListener(listenr);
        return new RequestOrderItemAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestOrderItemAdapter.MyViewHolder holder, int i) {
        OrderDetailsModel model=order.get(i);
        holder.bind(model);

    }

    @Override
    public int getItemCount() {
        return order.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding databinding) {
            super(databinding.getRoot());
            this.binding = databinding;
        }

        public void bind(OrderDetailsModel model) {
            this.binding.setVariable(BR.model, model);
            this.binding.setVariable(BR.position, getAdapterPosition());
            this.binding.setVariable(BR.tag, tag);
            this.binding.executePendingBindings();
        }

    }
}