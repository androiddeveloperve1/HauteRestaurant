package com.app.mylibertarestaurant.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.mylibertarestaurant.BR;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class TimeSlotModel extends BaseObservable {

    private String openAt;
    private String closeAt;

    @Bindable
    public String getOpenAt() {

      /*  if (openAt.trim().length() <= 0) {
            openAt = "00:00";
        }*/
        return openAt;
    }

    public void setOpenAt(String openAt) {
        this.openAt = openAt;
        this.notifyPropertyChanged(BR.openAt);
    }

    @Bindable
    public String getCloseAt() {

       /* if (closeAt.trim().length() <= 0) {
            closeAt = "00:00";
        }*/
        return closeAt;
    }

    public void setCloseAt(String closeAt) {
        this.closeAt = closeAt;
        this.notifyPropertyChanged(BR.closeAt);
    }
}
