package com.app.mylibertarestaurant.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.adapter.MyPagerAdapter;
import com.app.mylibertarestaurant.databinding.FragmentOrderBinding;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class FragmentOrders extends Fragment {
    FragmentOrderBinding binder;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("@@@@@@@", "sajdslah");
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        binder.setClick(new Presenter());
        binder.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        View view = binder.getRoot();
        binder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    requestOrderSelect();
                } else if (position == 1) {
                    onGoingOrderSelect();
                } else {
                    readyOrderSelect();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    void requestOrderSelect() {
        binder.lineOngoing.setVisibility(View.GONE);
        binder.lineReadyOrder.setVisibility(View.GONE);
        binder.lineOrderRequest.setVisibility(View.VISIBLE);
        binder.tvOngoing.setTextColor(getResources().getColor(R.color.gray_text));
        binder.tvOrderRequest.setTextColor(getResources().getColor(R.color.black));
        binder.tvReady.setTextColor(getResources().getColor(R.color.gray_text));
    }

    void onGoingOrderSelect() {
        binder.lineOngoing.setVisibility(View.VISIBLE);
        binder.lineReadyOrder.setVisibility(View.GONE);
        binder.lineOrderRequest.setVisibility(View.GONE);
        binder.tvOngoing.setTextColor(getResources().getColor(R.color.black));
        binder.tvOrderRequest.setTextColor(getResources().getColor(R.color.gray_text));
        binder.tvReady.setTextColor(getResources().getColor(R.color.gray_text));
    }

    void readyOrderSelect() {
        binder.lineOngoing.setVisibility(View.GONE);
        binder.lineReadyOrder.setVisibility(View.VISIBLE);
        binder.lineOrderRequest.setVisibility(View.GONE);
        binder.tvOngoing.setTextColor(getResources().getColor(R.color.gray_text));
        binder.tvOrderRequest.setTextColor(getResources().getColor(R.color.gray_text));
        binder.tvReady.setTextColor(getResources().getColor(R.color.black));
    }

    public class Presenter {

        public void onGoingOrder(View v) {
            onGoingOrderSelect();
            binder.viewPager.setCurrentItem(1);
        }

        public void newRequestOrder(View v) {
            requestOrderSelect();
            binder.viewPager.setCurrentItem(0);
        }

        public void readyOrder(View v) {
            readyOrderSelect();
            binder.viewPager.setCurrentItem(2);
        }

        public void onMenuClick(View v) {
            MainActivity act = (MainActivity) getActivity();
            act.navigationClick();
        }
    }
}
