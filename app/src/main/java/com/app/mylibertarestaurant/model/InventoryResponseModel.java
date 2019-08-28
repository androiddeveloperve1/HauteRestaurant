package com.app.mylibertarestaurant.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.databinding.library.baseAdapters.BR;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class InventoryResponseModel extends BaseObservable {
    private String _id;
    private String attribute_name;
    private ArrayList<InventoryModel> attribute_data;

    @Bindable
    public String get_id() {
        return _id;
    }

    @Bindable
    public String getAttribute_name() {
        return attribute_name;
    }

    @Bindable
    public ArrayList<InventoryModel> getAttribute_data() {
        return attribute_data;
    }


    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setAttribute_name(String attribute_name) {
        this.attribute_name = attribute_name;
        this.notifyPropertyChanged(BR.attribute_name);
    }

    public void setAttribute_data(ArrayList<InventoryModel> attribute_data) {
        this.attribute_data = attribute_data;
        this.notifyPropertyChanged(BR.attribute_data);
    }
}
