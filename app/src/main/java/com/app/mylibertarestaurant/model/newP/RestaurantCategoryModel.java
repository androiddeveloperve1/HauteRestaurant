package com.app.mylibertarestaurant.model.newP;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class RestaurantCategoryModel extends BaseObservable {

    private String name;
    private String image;
    private String _id;
    private String description;
    private String is_delete;
    private Boolean is_active;

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

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        this.notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    @Bindable
    public String getIs_delete() {
        return is_delete;
    }

    @Bindable
    public Boolean getIs_active() {
        return is_active;
    }


    public void setDescription(String description) {
        this.description = description;
        this.notifyPropertyChanged(BR.description);
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
        this.notifyPropertyChanged(BR.description);
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
        this.notifyPropertyChanged(BR.is_active);
    }
}
