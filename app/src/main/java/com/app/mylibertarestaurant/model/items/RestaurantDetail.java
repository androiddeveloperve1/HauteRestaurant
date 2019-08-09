package com.app.mylibertarestaurant.model.items;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.mylibertarestaurant.BR;
import com.app.mylibertarestaurant.model.CommisionModel;
import com.app.mylibertarestaurant.model.Location;
import com.app.mylibertarestaurant.model.TimeModel;
import com.app.mylibertarestaurant.model.TimeSlotModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class RestaurantDetail extends BaseObservable {

    private String name;
    private String address;
    private String is_profile_complete;
    private String is_online;
    private String _id;
    private String maxdeliverytime;
    private ArrayList<String> images;

    private String contact_no;
    private String country_code;
    private String deliveryfees;
    private String deliverykm;
    private String pincode;
    private Location location;
    private ArrayList<TimeModel> openForService;
    private CommisionModel comm;
@Bindable
    public CommisionModel getComm() {
        return comm;
    }

    public void setComm(CommisionModel comm) {
        this.comm = comm;
        this.notifyPropertyChanged(BR.comm);
    }

    @Bindable
    public ArrayList<TimeModel> getOpenForService() {
        return openForService;
    }

    public void setOpenForService(ArrayList<TimeModel> openForService) {
        this.openForService = openForService;
        this.notifyPropertyChanged(BR.openForService);
    }

    @Bindable
    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
        this.notifyPropertyChanged(BR.contact_no);
    }

    @Bindable
    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
        this.notifyPropertyChanged(BR.country_code);
    }

    @Bindable
    public String getDeliveryfees() {
        return deliveryfees;
    }

    public void setDeliveryfees(String deliveryfees) {
        this.deliveryfees = deliveryfees;
        this.notifyPropertyChanged(BR.deliveryfees);
    }

    @Bindable
    public String getDeliverykm() {
        return deliverykm;
    }

    public void setDeliverykm(String deliverykm) {
        this.deliverykm = deliverykm;
        this.notifyPropertyChanged(BR.deliverykm);
    }

    @Bindable
    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
        this.notifyPropertyChanged(BR.pincode);
    }

    @Bindable
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        this.notifyPropertyChanged(BR.location);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getMaxdeliverytime() {
        return maxdeliverytime;
    }

    public void setMaxdeliverytime(String maxdeliverytime) {
        this.maxdeliverytime = maxdeliverytime;
        this.notifyPropertyChanged(BR.maxdeliverytime);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        this.notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getIs_profile_complete() {
        return is_profile_complete;
    }

    public void setIs_profile_complete(String is_profile_complete) {
        this.is_profile_complete = is_profile_complete;
        this.notifyPropertyChanged(BR.is_profile_complete);
    }

    @Bindable
    public String getIs_online() {
        return is_online;
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
        this.notifyPropertyChanged(BR.is_online);
    }

    @Bindable
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    @Bindable
    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
        this.notifyPropertyChanged(BR.images);
    }
}
