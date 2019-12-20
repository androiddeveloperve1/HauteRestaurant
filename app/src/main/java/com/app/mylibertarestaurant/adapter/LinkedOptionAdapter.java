package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemLinkedOptionBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.newP.MainOptionModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class LinkedOptionAdapter extends RecyclerView.Adapter<LinkedOptionAdapter.MyViewHolder> {
    RecycleItemClickListener listenr;
    private ArrayList<MainOptionModel> list;

    public LinkedOptionAdapter(RecycleItemClickListener listenr, ArrayList<MainOptionModel> list) {
        this.listenr = listenr;
        this.list = list;
    }

    @NonNull
    @Override
    public LinkedOptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemLinkedOptionBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_linked_option, viewGroup, false);
        binding.setListener(listenr);
        return new LinkedOptionAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkedOptionAdapter.MyViewHolder holder, int i) {
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

        public void bind(MainOptionModel data) {
            this.binding.setVariable(BR.model, data);
            this.binding.setVariable(BR.position, getAdapterPosition());
            this.binding.executePendingBindings();
        }

    }

}