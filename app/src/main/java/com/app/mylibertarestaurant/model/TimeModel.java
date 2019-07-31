package com.app.mylibertarestaurant.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


import com.app.mylibertarestaurant.BR;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class TimeModel extends BaseObservable {
    private int day;
    private boolean is_selected;
    private boolean is_two_slot;
    private ArrayList<TimeSlotModel> timings;

    @Bindable
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
        this.notifyPropertyChanged(BR.day);
    }

    @Bindable
    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
        this.notifyPropertyChanged(BR.is_selected);
    }

    @Bindable
    public boolean isIs_two_slot() {
        return is_two_slot;
    }

    public void setIs_two_slot(boolean is_two_slot) {
        this.is_two_slot = is_two_slot;
        this.notifyPropertyChanged(BR.is_two_slot);
    }

    @Bindable
    public ArrayList<TimeSlotModel> getTimings() {
        return timings;
    }

    public void setTimings(ArrayList<TimeSlotModel> timings) {
        this.timings = timings;
        this.notifyPropertyChanged(BR.timings);
    }


}
