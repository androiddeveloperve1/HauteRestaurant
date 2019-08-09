package com.app.mylibertarestaurant.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.mylibertarestaurant.BR;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class CommisionModel extends BaseObservable {

    private String admincom;
    private String _id;
    private String taxrate;

    @Bindable
    public String getAdmincom() {
        return admincom;
    }
    @Bindable
    public String get_id() {
        return _id;
    }
    @Bindable
    public String getTaxrate() {
        return taxrate;
    }

    public void setAdmincom(String admincom) {
        this.admincom = admincom;
        this.notifyPropertyChanged(BR.admincom);
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setTaxrate(String taxrate) {
        this.taxrate = taxrate;
        this.notifyPropertyChanged(BR.taxrate);
    }
}
