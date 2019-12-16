package com.app.mylibertarestaurant.model.newP;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class MainOptionModel extends BaseObservable {

    private String _id;
    private String name;
    private String customerPrompt;
    private String minSelection;
    private String maxSelection;
    private String is_delete;
    private String location;
    private String display_position;
    private Boolean isActive;
    private String category_id;
    private String restaurent_id;
    private String item_id;
    private ArrayList<SubOptionModel> subOptionsResult;


    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setName(String name) {
        this.name = name;
        this.notifyPropertyChanged(BR.name);
    }

    public void setCustomerPrompt(String customerPrompt) {
        this.customerPrompt = customerPrompt;
        this.notifyPropertyChanged(BR.customerPrompt);
    }

    public void setMinSelection(String minSelection) {
        this.minSelection = minSelection;
        this.notifyPropertyChanged(BR.minSelection);
    }

    public void setMaxSelection(String maxSelection) {
        this.maxSelection = maxSelection;
        this.notifyPropertyChanged(BR.maxSelection);
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
        this.notifyPropertyChanged(BR.is_delete);
    }

    public void setLocation(String location) {
        this.location = location;
        this.notifyPropertyChanged(BR.location);
    }

    public void setDisplay_position(String display_position) {
        this.display_position = display_position;
        this.notifyPropertyChanged(BR.display_position);
    }

    public void setActive(Boolean active) {
        isActive = active;
        this.notifyPropertyChanged(BR.isActive);
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
        this.notifyPropertyChanged(BR.category_id);
    }

    public void setRestaurent_id(String restaurent_id) {
        this.restaurent_id = restaurent_id;
        this.notifyPropertyChanged(BR.restaurent_id);
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
        this.notifyPropertyChanged(BR.item_id);
    }

    public void setSubOptionsResult(ArrayList<SubOptionModel> subOptionsResult) {
        this.subOptionsResult = subOptionsResult;
        this.notifyPropertyChanged(BR.subOptionsResult);
    }

    @Bindable
    public String get_id() {
        return _id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getCustomerPrompt() {
        return customerPrompt;
    }

    @Bindable
    public String getMinSelection() {
        return minSelection;
    }

    @Bindable
    public String getMaxSelection() {
        return maxSelection;
    }

    @Bindable
    public String getIs_delete() {
        return is_delete;
    }

    @Bindable
    public String getLocation() {
        return location;
    }

    @Bindable
    public String getDisplay_position() {
        return display_position;
    }

    @Bindable
    public Boolean getActive() {
        return isActive;
    }

    @Bindable
    public String getCategory_id() {
        return category_id;
    }

    @Bindable
    public String getRestaurent_id() {
        return restaurent_id;
    }

    @Bindable
    public String getItem_id() {
        return item_id;
    }

    @Bindable
    public ArrayList<SubOptionModel> getSubOptionsResult() {
        return subOptionsResult;
    }
}
