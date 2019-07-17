package com.app.mylibertarestaurant.utils;

import android.graphics.Typeface;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.app.mylibertarestaurant.R;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public class BindingAdapters {


    @BindingAdapter("android:fontStyleName")
    public static void setFont(TextView textView, String fontName) {
        Typeface typeface = Typeface.createFromAsset(textView.getContext().getAssets(), fontName);
        textView.setTypeface(typeface);
    }

    @BindingAdapter("android:topCornerBg")
    public static void setBg(TextView textView, int tag) {
        if (tag == 0) {
            textView.setBackgroundResource(R.drawable.top_left_corner_gray);
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.black));
        } else if (tag == 1) {
            textView.setBackgroundResource(R.drawable.top_left_corner_yellow);
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.white));
        } else if (tag == 2) {
            textView.setBackgroundResource(R.drawable.top_left_corner_green);
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.white));
        }
    }

}
