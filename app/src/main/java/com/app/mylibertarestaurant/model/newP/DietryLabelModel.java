package com.app.mylibertarestaurant.model.newP;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class DietryLabelModel extends BaseObservable {

    private String _id;
    private String dietary_id;
    private String name;
    private String value;
    private boolean hasSelect;
@Bindable
    public boolean isHasSelect() {
        return hasSelect;
    }

    public void setHasSelect(boolean hasSelect) {
        this.hasSelect = hasSelect;
        this.notifyPropertyChanged(BR.hasSelect);
    }

    @Bindable
    public String get_id() {
        return _id;
    }

    @Bindable
    public String getDietary_id() {
        return dietary_id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getValue() {
        return value;
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setDietary_id(String dietary_id) {
        this.dietary_id = dietary_id;
        this.notifyPropertyChanged(BR.dietary_id);
    }

    public void setName(String name) {
        this.name = name;
        this.notifyPropertyChanged(BR.name);
    }

    public void setValue(String value) {
        this.value = value;
        this.notifyPropertyChanged(BR.value);
    }
}
