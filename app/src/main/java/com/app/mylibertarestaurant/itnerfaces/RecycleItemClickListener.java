package com.app.mylibertarestaurant.itnerfaces;

import android.view.View;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public abstract  class  RecycleItemClickListener<T> {

    public void onItemClicked(int position, T data){}
    public void onItemClickedWithTag(int position, T data,int tag){};
    public void onSwitchOnOff(View v,int position, T data){};
    public void onDel(int position, T data){};
}
