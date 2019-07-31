package com.app.mylibertarestaurant.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.CopyItemActivity;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.activities.MyApplication;
import com.app.mylibertarestaurant.adapter.EarnAdapter;
import com.app.mylibertarestaurant.adapter.InventoryAdapter;
import com.app.mylibertarestaurant.adapter.InventoryItemAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.FragmentInventoryBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.InventoryModel;
import com.app.mylibertarestaurant.model.InventoryResponseModel;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.app.mylibertarestaurant.network.APIInterface;
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
    @Inject
    APIInterface apiInterface;
    private TabLayout tabLayout;
    private ArrayList<InventoryResponseModel> inventoryList;

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
                getInventory();
            }
        }
    }

    public void initData(ArrayList<InventoryResponseModel> inventoryListFiltered) {
        tabLayout.removeAllTabs();
        FragmentInventoryListChild frag;
        InventoryAdapter inventoryAdapter = new InventoryAdapter(getChildFragmentManager());
        for (int i = 0; i < inventoryListFiltered.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(inventoryListFiltered.get(i).getAttribute_name()));
            Bundle data = new Bundle();
            data.putString("data", new Gson().toJson(inventoryListFiltered.get(i)));
            frag = new FragmentInventoryListChild();
            frag.setArguments(data);
            inventoryAdapter.addFragment(frag);
        }
        binder.viewPager.setAdapter(inventoryAdapter);
        binder.viewPager.setOffscreenPageLimit(3);
        inventoryAdapter.notifyDataSetChanged();
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
                        Log.e("Inventory->>>>>>", "" + new Gson().toJson(response.getData()));
                        inventoryList = response.getData();
                        initData(response.getData());
                        if (response.getStatus().equals("200")) {
                        } else {
                            ResponseDialog.showErrorDialog(getActivity(), response.getMessage());
                        }

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
            startActivityForResult(mIntent, 200);
        }

        public void filterAll(View view) {
            initData(inventoryList);
        }

        public void filterVeg(View view) {
            //initData(inventoryList);

        }

        public void filterNonVeg(View view) {
            //initData(inventoryList);
        }
    }

}
