package com.app.mylibertarestaurant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.model.AttributeModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class AttributeAdapter extends BaseAdapter {
    private ArrayList<AttributeModel> list;

    public AttributeAdapter(ArrayList<AttributeModel> list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_category, null);
        TextView names = (TextView) view.findViewById(R.id.name);
        names.setText(list.get(i).getName());
        return view;
    }


}