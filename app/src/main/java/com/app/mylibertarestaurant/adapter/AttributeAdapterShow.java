package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemAttributeShowBinding;
import com.app.mylibertarestaurant.model.inventorynew.AttributeModelNew;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class AttributeAdapterShow extends RecyclerView.Adapter<AttributeAdapterShow.MyViewHolder> {
    private ArrayList<AttributeModelNew> list;

    public AttributeAdapterShow(ArrayList<AttributeModelNew> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AttributeAdapterShow.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemAttributeShowBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_attribute_show, viewGroup, false);
        return new AttributeAdapterShow.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AttributeAdapterShow.MyViewHolder holder, int i) {

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
            this.binding.executePendingBindings();
        }

    }
}