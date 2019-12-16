package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemOptionShowBinding;
import com.app.mylibertarestaurant.databinding.ItemSubOptionShowBinding;
import com.app.mylibertarestaurant.model.newP.MainOptionModel;
import com.app.mylibertarestaurant.model.newP.SubOptionModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class SubOptionShowAdapter extends RecyclerView.Adapter<SubOptionShowAdapter.MyViewHolder> {
    private ArrayList<SubOptionModel> list;

    public SubOptionShowAdapter(ArrayList<SubOptionModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SubOptionShowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemSubOptionShowBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_sub_option_show, viewGroup, false);
        return new SubOptionShowAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubOptionShowAdapter.MyViewHolder holder, int i) {

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

        public void bind(SubOptionModel data) {
            this.binding.setVariable(BR.myModel, data);
            this.binding.executePendingBindings();
        }

    }
}
