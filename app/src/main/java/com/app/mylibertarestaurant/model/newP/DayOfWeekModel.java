package com.app.mylibertarestaurant.model.newP;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class DayOfWeekModel extends BaseObservable {
    private String _id;
    private String label;
    private String value;

    @Bindable
    public String get_id() {
        return _id;
    }

    @Bindable
    public String getLabel() {
        return label;
    }

    @Bindable
    public String getValue() {
        return value;
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setLabel(String label) {
        this.label = label;
        this.notifyPropertyChanged(BR.label);
    }

    public void setValue(String value) {
        this.value = value;
        this.notifyPropertyChanged(BR.value);
    }
}
