package com.app.mylibertarestaurant.model.items;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;


import com.app.mylibertarestaurant.model.UserModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public class OrderDetailsModel extends BaseObservable {
    private ArrayList<OrderItemModel> order;
    private String _id;
    private UserModel user_id;
    private String amount;
    private String deliveryfee;
    private String totalamount;
    private String payment_type;
    private String promo_code;
    private String stripe_charge_id;
    private String address_id;
    private String createdAt;
    private String updatedAt;
    private String address_type;
    private String delivery_status;
    private String distance = "";
    private String order_no;
    private String is_driver_assign;
    private String driver_id;

@Bindable
    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
        this.notifyPropertyChanged(BR.driver_id);
    }

    @Bindable
    public String getIs_driver_assign() {
        return is_driver_assign;
    }

    @Bindable
    public UserModel getUser_id() {
        return user_id;
    }

    public void setUser_id(UserModel user_id) {
        this.user_id = user_id;
        this.notifyPropertyChanged(BR.user_id);
    }

    @Bindable
    public ArrayList<OrderItemModel> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<OrderItemModel> order) {
        this.order = order;
        this.notifyPropertyChanged(BR.order);

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
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
        this.notifyPropertyChanged(BR.amount);
    }

    @Bindable
    public String getDeliveryfee() {
        return deliveryfee;
    }

    public void setDeliveryfee(String deliveryfee) {
        this.deliveryfee = deliveryfee;
        this.notifyPropertyChanged(BR.deliveryfee);
    }

    @Bindable
    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
        this.notifyPropertyChanged(BR.totalamount);
    }

    @Bindable
    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
        this.notifyPropertyChanged(BR.payment_type);
    }

    @Bindable
    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
        this.notifyPropertyChanged(BR.promo_code);
    }

    @Bindable
    public String getStripe_charge_id() {
        return stripe_charge_id;
    }

    public void setStripe_charge_id(String stripe_charge_id) {
        this.stripe_charge_id = stripe_charge_id;
        this.notifyPropertyChanged(BR.stripe_charge_id);
    }

    @Bindable
    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
        this.notifyPropertyChanged(BR.address_id);
    }

    @Bindable
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        this.notifyPropertyChanged(BR.createdAt);
    }

    @Bindable
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        this.notifyPropertyChanged(BR.updatedAt);
    }

    public void setIs_driver_assign(String is_driver_assign) {
        this.is_driver_assign = is_driver_assign;
        this.notifyPropertyChanged(BR.is_driver_assign);
    }

    @Bindable
    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
        this.notifyPropertyChanged(BR.address_type);
    }

    @Bindable
    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
        this.notifyPropertyChanged(BR.delivery_status);
    }

    @Bindable
    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
        this.notifyPropertyChanged(BR.distance);
    }

    @Bindable
    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
        this.notifyPropertyChanged(BR.order_no);
    }
}



