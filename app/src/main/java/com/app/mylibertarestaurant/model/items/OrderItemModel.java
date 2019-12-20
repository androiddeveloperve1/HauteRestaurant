package com.app.mylibertarestaurant.model.items;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.app.mylibertarestaurant.model.inventorynew.AttributeModelNew;
import com.app.mylibertarestaurant.model.newP.MainOptionModel;

import java.util.ArrayList;


/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class OrderItemModel extends BaseObservable {

    private String quantity;
    private String description;
    private String item_name;
    private String price;
    private String item_type;
    private String _id;
    private ItemDataModel item_id;
    private ArrayList<MainOptionModel> options;


    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.notifyPropertyChanged(BR.description);
    }
    @Bindable
    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
        this.notifyPropertyChanged(BR.item_name);
    }

    @Bindable
    public ArrayList<MainOptionModel> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<MainOptionModel> options) {
        this.options = options;
        this.notifyPropertyChanged(BR.options);
    }


    @Bindable
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
        this.notifyPropertyChanged(BR.quantity);
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
    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
        this.notifyPropertyChanged(BR.item_type);
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
    public ItemDataModel getItem_id() {
        return item_id;
    }

    public void setItem_id(ItemDataModel item_id) {
        this.item_id = item_id;
        this.notifyPropertyChanged(BR.item_id);
    }


}
