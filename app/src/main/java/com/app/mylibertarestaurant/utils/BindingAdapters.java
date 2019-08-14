package com.app.mylibertarestaurant.utils;

import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.model.TimeModel;
import com.squareup.picasso.Picasso;

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

    @BindingAdapter("android:timePicker")
    public static void pickTime(TextView tv, Integer in) {
        tv.setOnClickListener((v) -> {
            String time[] = tv.getText().toString().split(":");
            TimePickerDialog timeDialog = new TimePickerDialog(tv.getContext(), (timePicker, i, i1) -> {
                tv.setText(String.format("%02d:%02d", i, i1));
            }, Integer.parseInt(time[0]), Integer.parseInt(time[1]), true);
            timeDialog.show();
        });

    }


    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext()).load(imageUrl).resize(250, 250)
                .onlyScaleDown().placeholder(R.drawable.placeholder_squre).into(view);
    }


    @BindingAdapter({"bind:imageUrl2"})
    public static void loadImage2(ImageView view, String imageUrl) {
        Picasso.with(view.getContext()).load(imageUrl).resize(500, 400)
                .onlyScaleDown().placeholder(R.drawable.placeholder_squre).into(view);
    }


    @BindingAdapter("android:setAvailable")
    public static void setAvailable(TextView textView, String tag) {
        if (tag.equals("1")) {
            textView.setBackgroundResource(R.drawable.available_bg);
            textView.setText("Available");
        } else {
            textView.setBackgroundResource(R.drawable.un_available_bg);
            textView.setText("Unavailable");
        }
    }


    @BindingAdapter("android:setFoodType")
    public static void setFoodType(TextView textView, String food) {
        textView.setText(food);
        if (food.equals("veg")) {
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.greencolor));
        } else {
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.red));
        }
    }


}
