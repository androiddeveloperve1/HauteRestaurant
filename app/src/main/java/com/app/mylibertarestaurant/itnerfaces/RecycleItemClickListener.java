package com.app.mylibertarestaurant.itnerfaces;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public abstract  class  RecycleItemClickListener<T> {

    public void onItemClicked(int position, T data){}
   public void onItemClickedWithTag(int position, T data,int tag){};
}
