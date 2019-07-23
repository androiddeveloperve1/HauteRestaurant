package com.app.mylibertarestaurant.fragments;

import android.content.Intent;
import android.content.res.Resources;
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
import com.app.mylibertarestaurant.activities.EditProfileActivity;
import com.app.mylibertarestaurant.activities.EditServiceDaysActivity;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.adapter.MyPagerAdapter;
import com.app.mylibertarestaurant.adapter.TimeAdapter;
import com.app.mylibertarestaurant.databinding.FragmentProfileBinding;
import com.app.mylibertarestaurant.prefes.MySharedPreference;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binder;
    private Resources mResources;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binder.toolbarManu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity act = (MainActivity) getActivity();
                act.navigationClick();
            }
        });
        binder.setClick(new MyClick());
        mResources = getActivity().getResources();
        binder.viewPager.setAdapter(new TimeAdapter(getActivity()));
        binder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mon();
                } else if (position == 1) {
                    tue();
                } else if (position == 2) {
                    wed();
                } else if (position == 3) {
                    thur();
                } else if (position == 4) {
                    fri();
                } else if (position == 5) {
                    sat();
                } else if (position == 6) {
                    sun();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setClickListener();
        View view = binder.getRoot();
        return view;
    }

    void setClickListener() {
        binder.includeLayout.rlMon.setOnClickListener((v) -> {
            binder.viewPager.setCurrentItem(0);
            mon();
        });
        binder.includeLayout.rlTue.setOnClickListener((v) -> {
            binder.viewPager.setCurrentItem(1);
            tue();
        });
        binder.includeLayout.rlWed.setOnClickListener((v) -> {
            binder.viewPager.setCurrentItem(2);
            wed();
        });
        binder.includeLayout.rlThru.setOnClickListener((v) -> {
            binder.viewPager.setCurrentItem(3);
            thur();
        });
        binder.includeLayout.rlFri.setOnClickListener((v) -> {
            binder.viewPager.setCurrentItem(4);
            fri();
        });
        binder.includeLayout.rlSat.setOnClickListener((v) -> {
            binder.viewPager.setCurrentItem(5);
            sat();
        });
        binder.includeLayout.rlSun.setOnClickListener((v) -> {
            binder.viewPager.setCurrentItem(6);
            sun();
        });
    }

    void sun() {
        binder.includeLayout.tvSun.setTextColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineSun.setBackgroundColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineSun.setVisibility(View.VISIBLE);

        binder.includeLayout.tvMon.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setVisibility(View.GONE);

        binder.includeLayout.tvTue.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setVisibility(View.GONE);

        binder.includeLayout.tvWed.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setVisibility(View.GONE);

        binder.includeLayout.tvThru.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setVisibility(View.GONE);

        binder.includeLayout.tvFri.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setVisibility(View.GONE);

        binder.includeLayout.tvSat.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setVisibility(View.GONE);

    }

    void mon() {
        binder.includeLayout.tvSun.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setVisibility(View.GONE);

        binder.includeLayout.tvMon.setTextColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineMon.setBackgroundColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineMon.setVisibility(View.VISIBLE);

        binder.includeLayout.tvTue.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setVisibility(View.GONE);

        binder.includeLayout.tvWed.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setVisibility(View.GONE);

        binder.includeLayout.tvThru.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setVisibility(View.GONE);

        binder.includeLayout.tvFri.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setVisibility(View.GONE);

        binder.includeLayout.tvSat.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setVisibility(View.GONE);

    }

    void tue() {
        binder.includeLayout.tvSun.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setVisibility(View.GONE);

        binder.includeLayout.tvMon.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setVisibility(View.GONE);

        binder.includeLayout.tvTue.setTextColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineTue.setBackgroundColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineTue.setVisibility(View.VISIBLE);

        binder.includeLayout.tvWed.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setVisibility(View.GONE);

        binder.includeLayout.tvThru.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setVisibility(View.GONE);

        binder.includeLayout.tvFri.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setVisibility(View.GONE);

        binder.includeLayout.tvSat.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setVisibility(View.GONE);
    }

    void wed() {
        binder.includeLayout.tvSun.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setVisibility(View.GONE);

        binder.includeLayout.tvMon.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setVisibility(View.GONE);

        binder.includeLayout.tvTue.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setVisibility(View.GONE);

        binder.includeLayout.tvWed.setTextColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineWed.setBackgroundColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineWed.setVisibility(View.VISIBLE);

        binder.includeLayout.tvThru.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setVisibility(View.GONE);

        binder.includeLayout.tvFri.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setVisibility(View.GONE);

        binder.includeLayout.tvSat.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setVisibility(View.GONE);
    }

    void thur() {
        binder.includeLayout.tvSun.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setVisibility(View.GONE);

        binder.includeLayout.tvMon.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setVisibility(View.GONE);

        binder.includeLayout.tvTue.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setVisibility(View.GONE);

        binder.includeLayout.tvWed.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setVisibility(View.GONE);

        binder.includeLayout.tvThru.setTextColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineThu.setBackgroundColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineThu.setVisibility(View.VISIBLE);

        binder.includeLayout.tvFri.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setVisibility(View.GONE);

        binder.includeLayout.tvSat.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setVisibility(View.GONE);
    }

    void fri() {
        binder.includeLayout.tvSun.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setVisibility(View.GONE);

        binder.includeLayout.tvMon.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setVisibility(View.GONE);

        binder.includeLayout.tvTue.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setVisibility(View.GONE);

        binder.includeLayout.tvWed.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setVisibility(View.GONE);

        binder.includeLayout.tvThru.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setVisibility(View.GONE);

        binder.includeLayout.tvFri.setTextColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineFri.setBackgroundColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineFri.setVisibility(View.VISIBLE);

        binder.includeLayout.tvSat.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSat.setVisibility(View.GONE);
    }

    void sat() {
        binder.includeLayout.tvSun.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineSun.setVisibility(View.GONE);

        binder.includeLayout.tvMon.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineMon.setVisibility(View.GONE);

        binder.includeLayout.tvTue.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineTue.setVisibility(View.GONE);

        binder.includeLayout.tvWed.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineWed.setVisibility(View.GONE);

        binder.includeLayout.tvThru.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineThu.setVisibility(View.GONE);

        binder.includeLayout.tvFri.setTextColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setBackgroundColor(mResources.getColor(R.color.gray_text));
        binder.includeLayout.lineFri.setVisibility(View.GONE);

        binder.includeLayout.tvSat.setTextColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineSat.setBackgroundColor(mResources.getColor(R.color.greencolor));
        binder.includeLayout.lineSat.setVisibility(View.VISIBLE);
    }

    public class MyClick {
        public void editRestaurant(View v) {
            startActivity(new Intent(getActivity(), EditProfileActivity.class));
        }

        public void editServices(View v) {
            startActivity(new Intent(getActivity(), EditServiceDaysActivity.class));
        }

        public void logout(View v) {

            MySharedPreference.getInstance(getActivity()).clearMyPreference();
            getActivity().finish();
        }
    }
}
