package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import androidx.databinding.library.baseAdapters.BR;
import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemAttributeBinding;
import com.app.mylibertarestaurant.itnerfaces.AttributeAddDeleteListener;
import com.app.mylibertarestaurant.model.inventorynew.AttributeModelNew;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class MultipleAttributeAdapter extends RecyclerView.Adapter<MultipleAttributeAdapter.MyViewHolder> {
    AttributeAddDeleteListener listenr;
    private ArrayList<AttributeModelNew> list;

    public MultipleAttributeAdapter(ArrayList<AttributeModelNew> list, AttributeAddDeleteListener listenr) {
        this.listenr = listenr;
        this.list = list;
    }

    @NonNull
    @Override
    public MultipleAttributeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemAttributeBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_attribute, viewGroup, false);
        binding.setClick(listenr);
        return new MultipleAttributeAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleAttributeAdapter.MyViewHolder holder, int i) {

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

        public void bind(AttributeModelNew data) {
            this.binding.setVariable(BR.model, data);
            this.binding.setVariable(BR.position, getAdapterPosition());
            this.binding.executePendingBindings();
        }

    }
}