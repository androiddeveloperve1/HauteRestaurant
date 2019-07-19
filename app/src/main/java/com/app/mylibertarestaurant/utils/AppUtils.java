package com.app.mylibertarestaurant.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

//code improvement, handle permission gps, geofencing , distance and time ,

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public class AppUtils {

    public static boolean eMailValidation(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}
