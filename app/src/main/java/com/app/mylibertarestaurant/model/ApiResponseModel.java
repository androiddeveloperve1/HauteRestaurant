package com.app.mylibertarestaurant.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.mylibertarestaurant.BR;
import com.google.gson.annotations.SerializedName;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public class ApiResponseModel<T> extends BaseObservable {
    private String message;

    @SerializedName("Status")
    private String status;
    private String is_profile_complete;



    private T data;


    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.notifyPropertyChanged(BR.message);
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.notifyPropertyChanged(BR.status);

    }

    @Bindable
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        this.notifyPropertyChanged(BR.data);
    }
@Bindable
    public String getIs_profile_complete() {
        return is_profile_complete;
    }

    public void setIs_profile_complete(String is_profile_complete) {
        this.is_profile_complete = is_profile_complete;
        this.notifyPropertyChanged(BR.is_profile_complete);
    }
}
