package com.app.mylibertarestaurant.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.mylibertarestaurant.BR;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class AttributeModel extends BaseObservable {
    private String name;
    private String _id;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }
}
