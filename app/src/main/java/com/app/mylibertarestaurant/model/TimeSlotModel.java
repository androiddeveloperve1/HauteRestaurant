package com.app.mylibertarestaurant.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.mylibertarestaurant.BR;
import com.app.mylibertarestaurant.utils.AppUtils;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class TimeSlotModel extends BaseObservable {

    private String openAt;
    private String closeAt;

    @Bindable
    public String getOpenAt() {
        return AppUtils.get12HoursTimeFormat(openAt);
    }

    public void setOpenAt(String openAt) {
        this.openAt = openAt;
        this.notifyPropertyChanged(BR.openAt);
    }

    @Bindable
    public String getCloseAt() {
        return AppUtils.get12HoursTimeFormat(closeAt);
    }

    public void setCloseAt(String closeAt) {
        this.closeAt = closeAt;
        this.notifyPropertyChanged(BR.closeAt);
    }
}
