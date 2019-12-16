package com.app.mylibertarestaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemOptionShowBinding;
import com.app.mylibertarestaurant.model.newP.MainOptionModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class OptionShowAdapter extends RecyclerView.Adapter<OptionShowAdapter.MyViewHolder> {
    private ArrayList<MainOptionModel> list;
    private Context mContext;

    public OptionShowAdapter(Context mContext,ArrayList<MainOptionModel> list) {
        this.list = list;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public OptionShowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemOptionShowBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_option_show, viewGroup, false);
        binding.rvSubOption.setLayoutManager(new LinearLayoutManager(mContext));
        return new OptionShowAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionShowAdapter.MyViewHolder holder, int i) {

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
            this.binding.setVariable(BR.myModel, data);
            this.binding.setVariable(BR.subOptionAdapter, new SubOptionShowAdapter(data.getSubOptionsResult()));
            this.binding.executePendingBindings();
        }

    }
}