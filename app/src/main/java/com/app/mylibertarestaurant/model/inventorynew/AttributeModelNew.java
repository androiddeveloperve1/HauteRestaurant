package com.app.mylibertarestaurant.model.inventorynew;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.mylibertarestaurant.BR;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class AttributeModelNew extends BaseObservable {

    private String _id;
    private String attribute_name;
    private String attribute_price;

    @Bindable
    public String get_id() {
        return _id;
    }

    @Bindable
    public String getAttribute_name() {
        return attribute_name;
    }

    @Bindable
    public String getAttribute_price() {
        return attribute_price;
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setAttribute_name(String attribute_name) {
        this.attribute_name = attribute_name;
        this.notifyPropertyChanged(BR.attribute_name);
    }

    public void setAttribute_price(String attribute_price) {
        this.attribute_price = attribute_price;
        this.notifyPropertyChanged(BR.attribute_price);
    }
}
