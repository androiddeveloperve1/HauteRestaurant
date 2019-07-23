package com.app.mylibertarestaurant.model;

import android.location.Location;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.mylibertarestaurant.BR;


public class DeliveryAddress extends BaseObservable {

    private String address;
    private String deliveryoption;
    private String _id;
    private DeliveryOption deliveryoptionval;
    private Location loc;

    @Bindable
    public String getAddress() {
        return address;
    }

    @Bindable
    public String getDeliveryoption() {
        return deliveryoption;
    }

    @Bindable
    public String get_id() {
        return _id;
    }

    @Bindable
    public DeliveryOption getDeliveryoptionval() {
        return deliveryoptionval;
    }

    @Bindable
    public Location getLoc() {
        return loc;
    }


    public void setAddress(String address) {
        this.address = address;
        this.notifyPropertyChanged(BR.address);
    }

    public void setDeliveryoption(String deliveryoption) {
        this.deliveryoption = deliveryoption;
        this.notifyPropertyChanged(BR.deliveryoption);
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setDeliveryoptionval(DeliveryOption deliveryoptionval) {
        this.deliveryoptionval = deliveryoptionval;
        this.notifyPropertyChanged(BR.deliveryoptionval);
    }

    public void setLoc(Location loc) {
        this.loc = loc;
        this.notifyPropertyChanged(BR.loc);
    }
}