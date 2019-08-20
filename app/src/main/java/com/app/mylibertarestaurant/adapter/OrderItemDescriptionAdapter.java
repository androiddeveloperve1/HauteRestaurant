package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.BR;
import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.OrderItemDescriptionBinding;
import com.app.mylibertarestaurant.model.items.OrderItemModel;

import java.util.ArrayList;


/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class OrderItemDescriptionAdapter extends RecyclerView.Adapter<OrderItemDescriptionAdapter.MyViewHolder> {
    private ArrayList<OrderItemModel> order;

    public OrderItemDescriptionAdapter(ArrayList<OrderItemModel> order) {
        this.order = order;
    }

    @NonNull
    @Override
    public OrderItemDescriptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        OrderItemDescriptionBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_item_description, viewGroup, false);
       /* binding.rvAtribute.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));

        binding.setAdapter(new ShowAttributeAdapter(order.get(i).getItem_id().getItemInfo().getAttribute()));*/
        return new OrderItemDescriptionAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemDescriptionAdapter.MyViewHolder holder, int i) {
        holder.bind(order.get(i));
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

        public void bind(OrderItemModel data) {
            this.binding.setVariable(BR.model, data);


            this.binding.executePendingBindings();
        }

    }
}