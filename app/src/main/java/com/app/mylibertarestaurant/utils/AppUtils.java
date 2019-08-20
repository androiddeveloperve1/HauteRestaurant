package com.app.mylibertarestaurant.utils;

import android.util.Log;

import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.model.TimeModel;
import com.app.mylibertarestaurant.model.TimeSlotModel;
import com.app.mylibertarestaurant.model.items.OrderItemModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

//code improvement, handle permission gps, geofencing , distance and time ,

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public class AppUtils {

    static DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    static DateFormat humanFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
    static SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
    static SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");

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
        } else if (status.equals("6")) {
            flag = Constants.DELIVERY_STATUS_6;
        }
        return flag;
    }

    public static ArrayList<TimeModel> initDummyTimeData() {

        ArrayList<TimeModel> timesSlotList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            TimeModel model = new TimeModel();
            model.setDay(i + 1);
            model.setIs_selected(false);
            model.setIs_two_slot(false);
            ArrayList<TimeSlotModel> timeSlotModels = new ArrayList<>();
            TimeSlotModel time = null;
            time = new TimeSlotModel();
            time.setOpenAt("10:00");
            time.setCloseAt("06:00");
            timeSlotModels.add(time);

            time = new TimeSlotModel();
            time.setOpenAt("10:00");
            time.setCloseAt("06:00");
            timeSlotModels.add(time);
            model.setTimings(timeSlotModels);
            timesSlotList.add(model);
        }
        return timesSlotList;
    }


    public static String get12HoursTimeFormat(String _24HourTime) {
        try {
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            return _12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String get24HoursTimeFormat(String _12HourTime) {
        try {
            Date _12HourDt = _12HourSDF.parse(_12HourTime);
            return _24HourSDF.format(_12HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "10:00";
    }

}
