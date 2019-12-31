package com.app.mylibertarestaurant.model.newP;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class MealAvailabilityModel extends BaseObservable {

    private String _id;
    private String name;
    private String value;
    private boolean hasSelect;

@Bindable
    public String get_id() {
        return _id;
    }
    @Bindable
    public String getName() {
        return name;
    }
    @Bindable
    public String getValue() {
        return value;
    }
    @Bindable
    public boolean isHasSelect() {
        return hasSelect;
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setName(String name) {
        this.name = name;
        this.notifyPropertyChanged(BR.name);
    }

    public void setValue(String value) {
        this.value = value;
        this.notifyPropertyChanged(BR.value);
    }

    public void setHasSelect(boolean hasSelect) {
        this.hasSelect = hasSelect;
        this.notifyPropertyChanged(BR.hasSelect);
    }
}
