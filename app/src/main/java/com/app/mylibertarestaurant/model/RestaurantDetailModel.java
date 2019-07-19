package com.app.mylibertarestaurant.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.mylibertarestaurant.BR;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public class RestaurantDetailModel extends BaseObservable {


    private String _id;
    private String is_mobile_verify;
    private String fname;
    private String country_code;
    private String lname;
    private String mobile_no;
    private String email;
    private String avatar;
    private String otp;
    private String user_type;
    private String[] delivery_address;

    @Bindable
    public String get_id() {
        return _id;
    }
    @Bindable
    public String getIs_mobile_verify() {
        return is_mobile_verify;
    }
    @Bindable
    public String getFname() {
        return fname;
    }
    @Bindable
    public String getCountry_code() {
        return country_code;
    }
    @Bindable
    public String getLname() {
        return lname;
    }
    @Bindable
    public String getMobile_no() {
        return mobile_no;
    }
    @Bindable
    public String getEmail() {
        return email;
    }
    @Bindable
    public String getAvatar() {
        return avatar;
    }
    @Bindable
    public String getOtp() {
        return otp;
    }
    @Bindable
    public String getUser_type() {
        return user_type;
    }
    @Bindable
    public String[] getDelivery_address() {
        return delivery_address;
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setIs_mobile_verify(String is_mobile_verify) {
        this.is_mobile_verify = is_mobile_verify;
        this.notifyPropertyChanged(BR.is_mobile_verify);
    }

    public void setFname(String fname) {
        this.fname = fname;
        this.notifyPropertyChanged(BR.fname);
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
        this.notifyPropertyChanged(BR.country_code);
    }

    public void setLname(String lname) {
        this.lname = lname;
        this.notifyPropertyChanged(BR.lname);
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
        this.notifyPropertyChanged(BR.mobile_no);
    }

    public void setEmail(String email) {
        this.email = email;
        this.notifyPropertyChanged(BR.email);
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        this.notifyPropertyChanged(BR.avatar);
    }

    public void setOtp(String otp) {
        this.otp = otp;
        this.notifyPropertyChanged(BR.otp);
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
        this.notifyPropertyChanged(BR.user_type);
    }

    public void setDelivery_address(String[] delivery_address) {
        this.delivery_address = delivery_address;
        this.notifyPropertyChanged(BR.delivery_address);
    }
}
