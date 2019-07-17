package com.app.mylibertarestaurant.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class StatusbarUtils {
    public static void statusBar(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = activity.getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
