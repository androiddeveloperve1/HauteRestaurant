package com.app.mylibertarestaurant.model.newP;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class TagModel extends BaseObservable {
    private String _id;
    private String display;
    private String value;

    @Bindable
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    @Bindable
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
        this.notifyPropertyChanged(BR.display);
    }

    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        this.notifyPropertyChanged(BR.value);
    }
}
