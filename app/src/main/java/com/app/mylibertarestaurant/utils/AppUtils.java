package com.app.mylibertarestaurant.utils;

import android.util.Log;

import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.model.items.OrderItemModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

//code improvement, handle permission gps, geofencing , distance and time ,

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public class AppUtils {

    static DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    static DateFormat humanFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");

    public static boolean eMailValidation(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public static String getHumanReadableTimeFromUTCString(String status) {
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String time = null;
        try {
            time = humanFormat.format(utcFormat.parse(status));
        } catch (ParseException e) {
            e.printStackTrace();
            time = "no time";
        }
        return time;
    }

    public static String getItemListInAppendMode(ArrayList<OrderItemModel> order) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < order.size(); i++) {
            builder.append(order.get(i).getItem_id().getName()).append(",");
        }
        return builder.toString();
    }


    public static String getTextFromStatus(String status) {
        String flag = null;
        if (status.equals("0")) {
            flag = Constants.DELIVERY_STATUS_0;
        } else if (status.equals("1")) {
            flag = Constants.DELIVERY_STATUS_1;
        } else if (status.equals("2")) {
            flag = Constants.DELIVERY_STATUS_2;
        } else if (status.equals("3")) {
            flag = Constants.DELIVERY_STATUS_3;
        } else if (status.equals("4")) {
            flag = Constants.DELIVERY_STATUS_4;
        } else if (status.equals("5")) {
            flag = Constants.DELIVERY_STATUS_5;
        }
        return flag;
    }

}
