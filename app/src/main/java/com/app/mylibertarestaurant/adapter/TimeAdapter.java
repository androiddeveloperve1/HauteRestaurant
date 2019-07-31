package com.app.mylibertarestaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.model.TimeModel;

import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class TimeAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<TimeModel> time;

    public TimeAdapter(Context context, ArrayList<TimeModel> time) {
        mContext = context;
        this.time = time;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_view_pager_time, collection, false);
        TextView tv_slot1 = layout.findViewById(R.id.tv_slot1);
        TextView tv_slot2 = layout.findViewById(R.id.tv_slot2);
        TimeModel slots = time.get(position);

        if (slots.isIs_selected()) {
            if (slots.isIs_two_slot()) {
                tv_slot1.setText(slots.getTimings().get(0).getOpenAt() + "-" + slots.getTimings().get(0).getCloseAt());
                tv_slot2.setText(slots.getTimings().get(1).getOpenAt() + "-" + slots.getTimings().get(1).getCloseAt());
                tv_slot2.setVisibility(View.VISIBLE);
            } else {
                tv_slot2.setVisibility(View.GONE);
            }
        } else {
            tv_slot1.setText("Not available this day");
            tv_slot2.setVisibility(View.GONE);
        }


        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}