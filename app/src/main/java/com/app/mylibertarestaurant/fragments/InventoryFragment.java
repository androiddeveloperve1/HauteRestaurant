package com.app.mylibertarestaurant.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.CopyItemActivity;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.activities.MyApplication;
import com.app.mylibertarestaurant.adapter.InventoryAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.FragmentInventoryBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.InventoryResponseModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class InventoryFragment extends Fragment {
    FragmentInventoryBinding binder;
    InventoryAdapter inventoryAdapter;
    @Inject
    APIInterface apiInterface;
    private TabLayout tabLayout;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_inventory, container, false);
        binder.setClick(new Click());
        tabLayout = binder.tabLayout;
        tabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.gray_text), ContextCompat.getColor(getActivity(), R.color.black));
        getInventory();
        clickListener();
        View view = binder.getRoot();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (resultCode == Activity.RESULT_OK) {
                tabLayout.getTabAt(0).select();
                getInventory();
            }
        }
    }

    public void initData(ArrayList<InventoryResponseModel> inventoryListFiltered) {
        tabLayout.removeAllTabs();
        FragmentInventoryListChild frag;
        MySharedPreference.getInstance(getActivity()).setFilter(Constants.FILTER_ALL);
        allSelection();
        inventoryAdapter = new InventoryAdapter(getChildFragmentManager());
        for (int i = 0; i < inventoryListFiltered.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(inventoryListFiltered.get(i).getAttribute_name()));
            Bundle data = new Bundle();
            data.putString("data", new Gson().toJson(inventoryListFiltered.get(i)));
            frag = new FragmentInventoryListChild();
            frag.setArguments(data);
            inventoryAdapter.addFragment(frag);
        }
        binder.viewPager.setAdapter(inventoryAdapter);
        binder.viewPager.setOffscreenPageLimit(inventoryListFiltered.size());
        inventoryAdapter.notifyDataSetChanged();
        onListen();
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

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
                onListen();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getInventory() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(getActivity());
        ((MyApplication) getActivity().getApplication()).getConfiguration().inject(this);
        apiInterface.getInventory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<InventoryResponseModel>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(getActivity(), throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<InventoryResponseModel>> response) {
                        progressDialog.dismiss();
                        initData(response.getData());
                        if (response.getStatus().equals("200")) {
                        } else {
                            ResponseDialog.showErrorDialog(getActivity(), response.getMessage());
                        }

                    }
                });
    }

    public void onListen() {
        FragmentInventoryListChild frag = inventoryAdapter.getItem(binder.viewPager.getCurrentItem());
        frag.doFilter(binder.viewPager.getCurrentItem());
    }

    public class Click {
        public void onNavigationMenu(View v) {
            MainActivity act = (MainActivity) getActivity();
            act.navigationClick();
        }

        public void onSearch(View v) {
            /*mIntent.putExtra("data", new Gson().toJson(data));
            mIntent.putExtra("attribute_id", model.get_id());*/
        }

        public void onAdd(View v) {
            Intent mIntent = new Intent(getActivity(), CopyItemActivity.class);
            mIntent.putExtra("flag", Constants.ADD_NEW);
            startActivityForResult(mIntent, 200);
        }

        public void filterAll(View view) {
            MySharedPreference.getInstance(getActivity()).setFilter(Constants.FILTER_ALL);
            onListen();
            allSelection();
        }

        public void filterVeg(View view) {
            MySharedPreference.getInstance(getActivity()).setFilter(Constants.FILTER_VEG);
            onListen();
            vegSelection();
        }

        public void filterNonVeg(View view) {
            MySharedPreference.getInstance(getActivity()).setFilter(Constants.FILTER_NON_VEG);
            onListen();
            nonVegSelection();
        }
    }


    void allSelection() {
        binder.tvAll.setBackgroundResource(R.drawable.foof_type_bg_green);
        binder.tvNonVeg.setBackgroundResource(R.drawable.food_type_bg_white);
        binder.tvVeg.setBackgroundResource(R.drawable.food_type_bg_white);

        binder.tvVeg.setTextColor(getResources().getColor(R.color.black));
        binder.tvNonVeg.setTextColor(getResources().getColor(R.color.black));
        binder.tvAll.setTextColor(getResources().getColor(R.color.white));
    }

    void vegSelection() {
        binder.tvAll.setBackgroundResource(R.drawable.food_type_bg_white);
        binder.tvNonVeg.setBackgroundResource(R.drawable.food_type_bg_white);
        binder.tvVeg.setBackgroundResource(R.drawable.foof_type_bg_green);

        binder.tvVeg.setTextColor(getResources().getColor(R.color.white));
        binder.tvNonVeg.setTextColor(getResources().getColor(R.color.black));
        binder.tvAll.setTextColor(getResources().getColor(R.color.black));
    }

    void nonVegSelection() {
        binder.tvAll.setBackgroundResource(R.drawable.food_type_bg_white);
        binder.tvNonVeg.setBackgroundResource(R.drawable.foof_type_bg_green);
        binder.tvVeg.setBackgroundResource(R.drawable.food_type_bg_white);

        binder.tvVeg.setTextColor(getResources().getColor(R.color.black));
        binder.tvNonVeg.setTextColor(getResources().getColor(R.color.white));
        binder.tvAll.setTextColor(getResources().getColor(R.color.black));

    }

}
