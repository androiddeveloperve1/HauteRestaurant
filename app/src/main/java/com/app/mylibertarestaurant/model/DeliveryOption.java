package com.app.mylibertarestaurant.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.databinding.library.baseAdapters.BR;


public class DeliveryOption extends BaseObservable {

    private String addr;
    private String business;
    private String note;

    @Bindable
    public String getAddr() {
        return addr;
    }

    @Bindable
    public String getBusiness() {
        return business;
    }

    @Bindable
    public String getNote() {
        return note;

    }


    public void setAddr(String addr) {
        this.addr = addr;
        this.notifyPropertyChanged(BR.addr);
    }

    public void setBusiness(String business) {
        this.business = business;
        this.notifyPropertyChanged(BR.business);
    }

    public void setNote(String note) {
        this.note = note;
        this.notifyPropertyChanged(BR.note);
    }
}