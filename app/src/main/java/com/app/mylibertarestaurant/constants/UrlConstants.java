package com.app.mylibertarestaurant.constants;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public class UrlConstants {
    public static final String DEVICE_TYPE_KEY = "android";
    public static final String ADMIN = "admin";
    public static final String LOGIN = ADMIN + "/login";
    public static final String ONLINE_OFFLINE_STATUS = ADMIN + "/updateOnlineStatus";
    public static final String UPCOMMING = ADMIN + "/restaurant/upcomming-listing";
    public static final String ACCEPT_ORDER = ADMIN + "/accept-order/";
    public static final String REJECT_ORDER = ADMIN + "/reject-order/";
    public static final String ORDER_HISTORY = ADMIN + "/restaurant/order-listing";
    public static final String ATTRIBUTE_LIST = ADMIN + "/attribute";
    public static final String CATEGORY_LIST = "restaurent/categories";
    public static final String RESTAURANT_DETAIL = ADMIN + "/get-restaurant-detail";
    public static final String DELETE_FOOD_ITEM = ADMIN + "/food-item/";
    public static final String EDIT_FOOD = ADMIN + "/restaurant/updatefooditem";
    public static final String UPDATE_RESTAURANT = ADMIN + "/update-restaurant";
    public static final String READY_FOR_PICKUP = ADMIN + "/updateReadyForPickupStatus";
    public static final String READY_FOR_PICKUP_VERIFY = "restaurent/otpVerificationForOrderPickUp";
    public static final String ORDER_DETAIL = ADMIN + "/order-detail/";
    public static final String INVENTORY = ADMIN + "/restaurant/food-list";
    public static final String LOGOUT = "/restaurent/logout";
    public static final String UPDATE_TIME_SLOT = "/restaurent/updateTimeSlot";
    public static final String FORGOT_PASSWORD = "/restaurent/forgot";
/*-------------------------------------------------------*/

    public static final String RESTAURANT_CATEGORY = ADMIN+"/get-categories/";
    public static final String RESTAURANT_CATEGORY_ITEM = ADMIN+"/get-item-list/";
    public static final String ADD_EDIT_RESTAURANT_CATEGORY = ADMIN+"/add-update-category";
    public static final String MENU_ITEM_DETAIL = ADMIN+"/itemDetailsByid/";
    public static final String UPDATE_MENU_STATUS = ADMIN+"/updatMenuStatus";
    public static final String DELETE_MENU = ADMIN+"/categories/";
    public static final String DELETE_MENU_ITEM = ADMIN+"/deleteItem/";

    public static final String DELETE_OPTION = ADMIN+"/del-option/";
    public static final String DELETE_SUB_OPTION = ADMIN+"/del-sub-option/";

    public static final String ADD_UPDATE_OPTION = ADMIN+"/add-update-option";
    public static final String ADD_UPDATE_SUB_OPTION = ADMIN+"/add-update-sub-option";
    public static final String ADD_UPDATE_MENU_ITEM = ADMIN+"/add-menu-item";
    public static final String GET_FOOD_AVAILABILITY = ADMIN+"/getFoodAvailability";
    public static final String GET_DIETARY_LIST = "/users/get-dietary-list";
}
