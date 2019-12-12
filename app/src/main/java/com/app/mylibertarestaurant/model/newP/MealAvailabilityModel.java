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
    private String valueTxt;
    private String txtValue;

    @Bindable
    public String get_id() {
        return _id;
    }

    @Bindable
    public String getValueTxt() {
        return valueTxt;
    }

    @Bindable
    public String getTxtValue() {
        return txtValue;
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setValueTxt(String valueTxt) {
        this.valueTxt = valueTxt;
        this.notifyPropertyChanged(BR.valueTxt);
    }

    public void setTxtValue(String txtValue) {
        this.txtValue = txtValue;
        this.notifyPropertyChanged(BR.txtValue);
    }
}
