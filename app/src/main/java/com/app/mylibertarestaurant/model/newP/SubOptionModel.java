package com.app.mylibertarestaurant.model.newP;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.util.ArrayList;


/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class SubOptionModel extends BaseObservable {

    private String name;
    private String price;
    private String bestPrice;
    private String markupStructure;
    private String disabledUntilDate;
    private String is_delete;
    private String location;
    private String display_position;
    private Boolean isActive;
    private String _id;
    private String category_id;
    private ArrayList<LinkedOptionRequestModel> optionLinked;
    private String restaurent_id;
    private String option_id;
    private String item_id;


    private String description;

    public ArrayList<LinkedOptionRequestModel> getOptionLinked() {
        return optionLinked;
    }

    public void setOptionLinked(ArrayList<LinkedOptionRequestModel> optionLinked) {
        this.optionLinked = optionLinked;
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        this.notifyPropertyChanged(BR.price);
    }

    @Bindable
    public String getBestPrice() {
        return bestPrice;
    }

    public void setBestPrice(String bestPrice) {
        this.bestPrice = bestPrice;
        this.notifyPropertyChanged(BR.bestPrice);
    }

    @Bindable
    public String getMarkupStructure() {
        return markupStructure;
    }

    public void setMarkupStructure(String markupStructure) {
        this.markupStructure = markupStructure;
        this.notifyPropertyChanged(BR.markupStructure);
    }

    @Bindable
    public String getDisabledUntilDate() {
        return disabledUntilDate;
    }

    public void setDisabledUntilDate(String disabledUntilDate) {
        this.disabledUntilDate = disabledUntilDate;
        this.notifyPropertyChanged(BR.disabledUntilDate);
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
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
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
    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
        this.notifyPropertyChanged(BR.option_id);
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
