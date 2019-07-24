package com.app.mylibertarestaurant.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.mylibertarestaurant.BR;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class CategoryModel extends BaseObservable {

    private String name;
    private String _id;
    private String image;

    @Bindable
    public String getName() {
        return name;
    }
    @Bindable
    public String get_id() {
        return _id;
    }
    @Bindable
    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
        this.notifyPropertyChanged(BR.name);
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setImage(String image) {
        this.image = image;
        this.notifyPropertyChanged(BR.image);
    }
}
