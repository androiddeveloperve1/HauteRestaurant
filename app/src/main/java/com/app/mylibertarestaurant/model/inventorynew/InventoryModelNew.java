package com.app.mylibertarestaurant.model.inventorynew;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.databinding.library.baseAdapters.BR;
import com.app.mylibertarestaurant.model.items.ItemDataModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class InventoryModelNew extends BaseObservable {

    private String price_devide;
    private String notes;
    private String _id;
    private String restaurent_id;
    private String half_price;
    private String image;
    private String foodItemId;
    private String food_type;
    private String is_available;
    private String description;
    private ItemDataModel item_id;
    private String full_price;
    private ItemDataModel category_id;
    private ArrayList<AttributeModelNew> attribute;

    @Bindable
    public String getPrice_devide() {
        return price_devide;
    }

    @Bindable
    public String getNotes() {
        return notes;
    }

    @Bindable
    public String get_id() {
        return _id;
    }

    @Bindable
    public String getRestaurent_id() {
        return restaurent_id;
    }

    @Bindable
    public String getHalf_price() {
        return half_price;
    }

    @Bindable
    public String getImage() {
        return image;
    }

    @Bindable
    public String getFoodItemId() {
        return foodItemId;
    }

    @Bindable
    public String getFood_type() {
        return food_type;
    }

    @Bindable
    public String getIs_available() {
        return is_available;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    @Bindable
    public ItemDataModel getItem_id() {
        return item_id;
    }

    @Bindable
    public String getFull_price() {
        return full_price;
    }

    @Bindable
    public ItemDataModel getCategory_id() {
        return category_id;
    }

    @Bindable
    public ArrayList<AttributeModelNew> getAttribute() {
        return attribute;
    }

    public void setPrice_devide(String price_devide) {
        this.price_devide = price_devide;
        this.notifyPropertyChanged(BR.price_devide);
    }

    public void setNotes(String notes) {
        this.notes = notes;
        this.notifyPropertyChanged(BR.notes);
    }

    public void set_id(String _id) {
        this._id = _id;
        this.notifyPropertyChanged(BR._id);
    }

    public void setRestaurent_id(String restaurent_id) {
        this.restaurent_id = restaurent_id;
        this.notifyPropertyChanged(BR.restaurent_id);
    }

    public void setHalf_price(String half_price) {
        this.half_price = half_price;
        this.notifyPropertyChanged(BR.half_price);
    }

    public void setImage(String image) {
        this.image = image;
        this.notifyPropertyChanged(BR.image);
    }

    public void setFoodItemId(String foodItemId) {
        this.foodItemId = foodItemId;
        this.notifyPropertyChanged(BR.foodItemId);
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
        this.notifyPropertyChanged(BR.food_type);
    }

    public void setIs_available(String is_available) {
        this.is_available = is_available;
        this.notifyPropertyChanged(BR.is_available);
    }

    public void setDescription(String description) {
        this.description = description;
        this.notifyPropertyChanged(BR.description);
    }

    public void setItem_id(ItemDataModel item_id) {
        this.item_id = item_id;
        this.notifyPropertyChanged(BR.item_id);
    }

    public void setFull_price(String full_price) {
        this.full_price = full_price;
        this.notifyPropertyChanged(BR.full_price);
    }

    public void setCategory_id(ItemDataModel category_id) {
        this.category_id = category_id;
        this.notifyPropertyChanged(BR.category_id);
    }

    public void setAttribute(ArrayList<AttributeModelNew> attribute) {
        this.attribute = attribute;
        this.notifyPropertyChanged(BR.attribute);
    }
}
