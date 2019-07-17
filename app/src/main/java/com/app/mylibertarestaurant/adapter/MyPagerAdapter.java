package com.app.mylibertarestaurant.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.mylibertarestaurant.fragments.OngoingRequestFragment;
import com.app.mylibertarestaurant.fragments.OrderRequestFragment;
import com.app.mylibertarestaurant.fragments.ReadyOrderFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment frag = null;
        switch (pos) {
            case 0:
                return new OrderRequestFragment();
            case 1:
                return new OngoingRequestFragment();
            case 2:
                return new ReadyOrderFragment();

        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }
}