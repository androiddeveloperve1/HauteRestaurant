package com.app.mylibertarestaurant.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.mylibertarestaurant.BR;
import com.app.mylibertarestaurant.model.items.ItemDataModel;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class InventoryModel extends BaseObservable {



    private String price_devide;
    private String half_price;
    private String image;
    private String foodItemId;

    private String food_type;
    private String is_available;
    private String description;
    private ItemDataModel item_id;
    private String full_price;
    private ItemDataModel category_id;
    private ItemDataModel attribute_id;
@Bindable
    public String getFoodItemId() {
        return foodItemId;
    }

    @Bindable
    private String getPrice_devide() {
        return price_devide;
    }
@Bindable
    public ItemDataModel getAttribute_id() {
        return attribute_id;
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


    public void setPrice_devide(String price_devide) {
        this.price_devide = price_devide;
        this.notifyPropertyChanged(BR.price_devide);
    }

    public void setAttribute_id(ItemDataModel attribute_id) {
        this.attribute_id = attribute_id;
        this.notifyPropertyChanged(BR.attribute_id);
    }

    public void setHalf_price(String half_price) {
        this.half_price = half_price;
        this.notifyPropertyChanged(BR.half_price);
    }

    public void setFoodItemId(String foodItemId) {
        this.foodItemId = foodItemId;
        this.notifyPropertyChanged(BR.foodItemId);
    }

    public void setImage(String image) {
        this.image = image;
        this.notifyPropertyChanged(BR.image);
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
}
