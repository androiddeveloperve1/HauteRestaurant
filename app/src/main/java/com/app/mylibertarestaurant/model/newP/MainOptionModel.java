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
    private ArrayList<SubOptionModel> suboptions;
    private String description;
    private boolean hasSelect1;

    @Bindable
    public boolean isHasSelect1() {
        return hasSelect1;
    }

    public void setHasSelect1(boolean hasSelect1) {
        this.hasSelect1 = hasSelect1;
        this.notifyPropertyChanged(BR.hasSelect1);
    }

    @Bindable
    public ArrayList<SubOptionModel> getSuboptions() {
        return suboptions;
    }

    public void setSuboptions(ArrayList<SubOptionModel> suboptions) {
        this.suboptions = suboptions;
        this.notifyPropertyChanged(BR.suboptions);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.notifyPropertyChanged(BR.description);
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getCustomerPrompt() {
        return customerPrompt;
    }

    public void setCustomerPrompt(String customerPrompt) {
        this.customerPrompt = customerPrompt;
        this.notifyPropertyChanged(BR.customerPrompt);
    }

    @Bindable
    public String getMinSelection() {
        return minSelection;
    }

    public void setMinSelection(String minSelection) {
        this.minSelection = minSelection;
        this.notifyPropertyChanged(BR.minSelection);
    }

    @Bindable
    public String getMaxSelection() {
        return maxSelection;
    }

    public void setMaxSelection(String maxSelection) {
        this.maxSelection = maxSelection;
        this.notifyPropertyChanged(BR.maxSelection);
    }

    @Bindable
    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
        this.notifyPropertyChanged(BR.is_delete);
    }

    @Bindable
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        this.notifyPropertyChanged(BR.location);
    }

    @Bindable
    public String getDisplay_position() {
        return display_position;
    }

    public void setDisplay_position(String display_position) {
        this.display_position = display_position;
        this.notifyPropertyChanged(BR.display_position);
    }

    @Bindable
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
        this.notifyPropertyChanged(BR.isActive);
    }

    @Bindable
    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
        this.notifyPropertyChanged(BR.category_id);
    }

    @Bindable
    public String getRestaurent_id() {
        return restaurent_id;
    }

    public void setRestaurent_id(String restaurent_id) {
        this.restaurent_id = restaurent_id;
        this.notifyPropertyChanged(BR.restaurent_id);
    }

    @Bindable
    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
        this.notifyPropertyChanged(BR.item_id);
    }

   
}
