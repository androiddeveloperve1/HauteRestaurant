package com.app.mylibertarestaurant.model.newP;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class RestaurantCategoryItemModel extends BaseObservable {

    private String _id;
    private String name;
    private String is_delete;
    private String price;
    private String description;
    private String location;
    private String image;
    private String display_position;
    private String isActive;
    private String minQuantity;
    private String maxQuantity;
    private String isHidden;
    private String hiddenDate;
    private String category_id;
    private String createdAt;
    private String updatedAt;
    private String __v;
    private ArrayList<MealAvailabilityModel> mealAvailability;
    private ArrayList<DayOfWeekModel> daysOfWeek;
    private ArrayList<DietryLabelModel> dietaryLabels;
    private ArrayList<TagModel> tags;

    @Bindable
    public String get_id() {
        return _id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getIs_delete() {
        return is_delete;
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    @Bindable
    public String getLocation() {
        return location;
    }

    @Bindable
    public String getImage() {
        return image;
    }

    @Bindable
    public String getDisplay_position() {
        return display_position;
    }

    @Bindable
    public String getIsActive() {
        return isActive;
    }

    @Bindable
    public String getMinQuantity() {
        return minQuantity;
    }

    @Bindable
    public String getMaxQuantity() {
        return maxQuantity;
    }

    @Bindable
    public String getIsHidden() {
        return isHidden;
    }

    @Bindable
    public String getHiddenDate() {
        return hiddenDate;
    }

    @Bindable
    public String getCategory_id() {
        return category_id;
    }

    @Bindable
    public String getCreatedAt() {
        return createdAt;
    }

    @Bindable
    public String getUpdatedAt() {
        return updatedAt;
    }

    @Bindable
    public String get__v() {
        return __v;
    }

    @Bindable
    public ArrayList<MealAvailabilityModel> getMealAvailability() {
        return mealAvailability;
    }

    @Bindable
    public ArrayList<DayOfWeekModel> getDaysOfWeek() {
        return daysOfWeek;
    }

    @Bindable
    public ArrayList<DietryLabelModel> getDietaryLabels() {
        return dietaryLabels;
    }

    @Bindable
    public ArrayList<TagModel> getTags() {
        return tags;
    }


    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setName(String name) {
        this.name = name;
        this.notifyPropertyChanged(BR.name);
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
        this.notifyPropertyChanged(BR.is_delete);
    }

    public void setPrice(String price) {
        this.price = price;
        this.notifyPropertyChanged(BR.price);
    }

    public void setDescription(String description) {
        this.description = description;
        this.notifyPropertyChanged(BR.description);
    }

    public void setLocation(String location) {
        this.location = location;
        this.notifyPropertyChanged(BR.location);
    }

    public void setImage(String image) {
        this.image = image;
        this.notifyPropertyChanged(BR.image);
    }

    public void setDisplay_position(String display_position) {
        this.display_position = display_position;
        this.notifyPropertyChanged(BR.display_position);
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
        this.notifyPropertyChanged(BR.isActive);
    }

    public void setMinQuantity(String minQuantity) {
        this.minQuantity = minQuantity;
        this.notifyPropertyChanged(BR.minQuantity);
    }

    public void setMaxQuantity(String maxQuantity) {
        this.maxQuantity = maxQuantity;
        this.notifyPropertyChanged(BR.maxQuantity);
    }

    public void setIsHidden(String isHidden) {
        this.isHidden = isHidden;
        this.notifyPropertyChanged(BR.isHidden);
    }

    public void setHiddenDate(String hiddenDate) {
        this.hiddenDate = hiddenDate;
        this.notifyPropertyChanged(BR.hiddenDate);
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
        this.notifyPropertyChanged(BR.category_id);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        this.notifyPropertyChanged(BR.createdAt);
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        this.notifyPropertyChanged(BR.updatedAt);
    }

    public void set__v(String __v) {
        this.__v = __v;
        this.notifyPropertyChanged(BR.__v);
    }

    public void setMealAvailability(ArrayList<MealAvailabilityModel> mealAvailability) {
        this.mealAvailability = mealAvailability;
        this.notifyPropertyChanged(BR.mealAvailability);
    }

    public void setDaysOfWeek(ArrayList<DayOfWeekModel> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
        this.notifyPropertyChanged(BR.daysOfWeek);
    }

    public void setDietaryLabels(ArrayList<DietryLabelModel> dietaryLabels) {
        this.dietaryLabels = dietaryLabels;
        this.notifyPropertyChanged(BR.dietaryLabels);
    }

    public void setTags(ArrayList<TagModel> tags) {
        this.tags = tags;
        this.notifyPropertyChanged(BR.tags);
    }
}
