package com.app.mylibertarestaurant.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.ActivitySearchItem;
import com.app.mylibertarestaurant.activities.CopyItemActivity;
import com.app.mylibertarestaurant.activities.EnterOtpActivity;
import com.app.mylibertarestaurant.activities.ItemDescriptionActivity;
import com.app.mylibertarestaurant.activities.ItemModificationActivity;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.activities.MyApplication;
import com.app.mylibertarestaurant.adapter.InventoryItemAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.FragmentInventoryNewBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.InventoryModel;
import com.app.mylibertarestaurant.model.InventoryResponseModel;
import com.app.mylibertarestaurant.model.inventorynew.InventoryModelNew;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class FragmentInventoryNew extends Fragment {
    @Inject
    APIInterface apiInterface;
    private FragmentInventoryNewBinding binder;
    private InventoryItemAdapter inventoryItemAdapter;
    private ArrayList<InventoryModelNew> originalList = new ArrayList<>();
    private ArrayList<InventoryModelNew> list = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_inventory_new, container, false);
        binder.setClick(new Click());
        binder.rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        inventoryItemAdapter = new InventoryItemAdapter(new RecycleItemClickListener<InventoryModelNew>() {
            @Override
            public void onItemClicked(int position, InventoryModelNew data) {
                Intent mIntent = new Intent(getActivity(), ItemDescriptionActivity.class);
                mIntent.putExtra("data", new Gson().toJson(data));
                startActivityForResult(mIntent, 100);
            }
        }, list);
        binder.setAdapter(inventoryItemAdapter);
        binder.pullRefresh.setOnRefreshListener(() -> {
            getInventory();


        });
        getInventory();
        View view = binder.getRoot();
        return view;
    }

    private void getInventory() {
        HashMap<String, String> param = new HashMap<>();
        param.put("category", "");
        binder.pullRefresh.setRefreshing(true);
        ((MyApplication) getActivity().getApplication()).getConfiguration().inject(this);
        apiInterface.getInventoryNew(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<InventoryModelNew>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        binder.pullRefresh.setRefreshing(false);
                        ResponseDialog.showErrorDialog(getActivity(), throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<InventoryModelNew>> response) {
                        Log.e("@@@@@@@@", new Gson().toJson(response));
                        binder.pullRefresh.setRefreshing(false);
                        if (response.getStatus().equals("200")) {
                            originalList.clear();
                            originalList.addAll(response.getData());
                            list.clear();
                            list.addAll(response.getData());
                            inventoryItemAdapter.notifyDataSetChanged();
                            allSelection();
                        } else {
                            ResponseDialog.showErrorDialog(getActivity(), response.getMessage());
                        }

                    }
                });
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

    public class Click {
        public void onNavigationMenu(View v) {
            MainActivity act = (MainActivity) getActivity();
            act.navigationClick();
        }

        public void onSearch(View v) {
            Intent mIntent = new Intent(getActivity(), ActivitySearchItem.class);
            startActivity(mIntent);
        }

        public void onAdd(View v) {
            Intent mIntent = new Intent(getActivity(), ItemModificationActivity.class);
            mIntent.putExtra("flag", Constants.ADD_NEW);
            startActivityForResult(mIntent, 200);

        }

        public void filterAll(View view) {
            allSelection();
            list.clear();
            list.addAll(originalList);
        }

        public void filterVeg(View view) {
            vegSelection();
            list.clear();
            for (int i = 0; i < originalList.size(); i++) {
                if (originalList.get(i).getFood_type().equals("veg")) {
                    list.add(originalList.get(i));
                }
            }
            inventoryItemAdapter.notifyDataSetChanged();
        }

        public void filterNonVeg(View view) {
            nonVegSelection();
            list.clear();
            for (int i = 0; i < originalList.size(); i++) {
                if (originalList.get(i).getFood_type().equals("non-veg")) {
                    list.add(originalList.get(i));
                }
            }
            inventoryItemAdapter.notifyDataSetChanged();
        }
    }
}
