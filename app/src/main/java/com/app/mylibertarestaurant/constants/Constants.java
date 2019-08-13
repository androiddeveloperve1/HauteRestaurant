package com.app.mylibertarestaurant.constants;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class Constants {
    public static final int FROM_ORDER_REQUEST_TAG = 0;
    public static final int FROM_ONGOING_REQUEST_TAG = 1;
    public static final int FROM_READY_REQUEST_TAG = 2;
    public static final String NO_INTERNET_CONNECTION_FOUND_TAG = "No address associated with hostname";
    public static final String NO_INTERNET_CONNECTION_FOUND_MESSAGE = "Please make sure, that you are connected to internet.";


    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String TYPE_DETAILS = "/details";
    public static final String OUT_JSON = "/json";
    public static final String API_KEY = "AIzaSyAq8L4mpVsRU0XYfdm4njQJ_Ntpi-KOvyc";


    public static final String BROADCAST_ORDER_ACCEPT = "order_accepted";
    public static final String BROADCAST_ORDER_CANCEL = "order_cancelled";
    public static final String BROADCAST_ORDER_READY_FOR_PICKUP = "order_ready_for_pickup";
    public static final String DRIVER_VERIFIED_WITH_OTP = "driver_verified_with_otp";

    public static String  DELIVERY_STATUS_0 = "Placed";
    public static String  DELIVERY_STATUS_1 = "Accepted";
    public static String  DELIVERY_STATUS_2 = "Rejected";
    public static String  DELIVERY_STATUS_3 = "Cancelled";
    public static String  DELIVERY_STATUS_4 = "Picked up";
    public static String  DELIVERY_STATUS_5 = "Delivered";
    public static String  DELIVERY_STATUS_6 = "Ready for Pickup";

    public static int  ADD_NEW = 0;
    public static int  COPY = 1;
    public static int  EDIT = 2;



    public static int  FILTER_ALL = 0;
    public static int  FILTER_VEG = 1;
    public static int  FILTER_NON_VEG = 2;
}
