package com.app.mylibertarestaurant.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class InventoryAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    public InventoryAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.clear();
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public void addFragment(Fragment frag) {
        fragmentList.add(frag);
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}