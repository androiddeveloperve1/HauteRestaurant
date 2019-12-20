package com.app.mylibertarestaurant.adapter;

import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ItemRestaurantCategoryBinding;
import com.app.mylibertarestaurant.databinding.ItemTagPopupBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryModel;
import com.app.mylibertarestaurant.model.newP.TagModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {
    RecycleItemClickListener listenr;
    private ArrayList<TagModel> list;

    public TagAdapter(RecycleItemClickListener listenr, ArrayList<TagModel> list) {
        this.listenr = listenr;
        this.list =list;
    }

    @NonNull
    @Override
    public TagAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemTagPopupBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_tag_popup, viewGroup, false);
        binding.setListener(listenr);
        return new TagAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdapter.MyViewHolder holder, int i) {
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

        public void bind(TagModel data) {
            this.binding.setVariable(BR.model, data);
            this.binding.setVariable(BR.position, getAdapterPosition());
            this.binding.executePendingBindings();
        }

    }

}