package com.app.mylibertarestaurant.model;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class TimeSlotUpdate {
    private String restaurent_id;
    private ArrayList<TimeModel> openForService;


    public String getRestaurent_id() {
        return restaurent_id;
    }

    public void setRestaurent_id(String restaurent_id) {
        this.restaurent_id = restaurent_id;
    }

    public ArrayList<TimeModel> getOpenForService() {
        return openForService;
    }

    public void setOpenForService(ArrayList<TimeModel> openForService) {
        this.openForService = openForService;
    }
}
