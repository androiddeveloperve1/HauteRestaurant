package com.app.mylibertarestaurant.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.CopyItemActivity;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.adapter.InventoryAdapter;
import com.app.mylibertarestaurant.adapter.InventoryItemAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.FragmentInventoryBinding;
import com.google.android.material.tabs.TabLayout;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class InventoryFragment extends Fragment {
    FragmentInventoryBinding binder;
    private TabLayout tabLayout;
    private InventoryAdapter inventoryAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_inventory, container, false);
        binder.setClick(new Click());
        tabLayout = binder.tabLayout;
        tabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.gray_text), ContextCompat.getColor(getActivity(), R.color.black));
        View view = binder.getRoot();
        clickListener();
        initData();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // refresh here
        }
    }

    public void initData() {
        inventoryAdapter = new InventoryAdapter(getChildFragmentManager());
        for (int i = 0; i < 10; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("Tab" + i));
            inventoryAdapter.addFragment(new FragmentInventoryListChild());
        }
        binder.viewPager.setAdapter(inventoryAdapter);

    }

    void clickListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binder.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        binder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public class Click {
        public void onNavigationMenu(View v) {
            MainActivity act = (MainActivity) getActivity();
            act.navigationClick();
        }

        public void onSearch(View v) {
        }

        public void onAdd(View v) {
            Intent mIntent = new Intent(getActivity(), CopyItemActivity.class);
            mIntent.putExtra("flag", Constants.ADD_NEW);
            startActivity(mIntent);
        }
    }

}
