package com.app.mylibertarestaurant.fragments;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.activities.MyApplication;
import com.app.mylibertarestaurant.adapter.MyPagerAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.FragmentOrderBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public class FragmentOrders extends Fragment {
    FragmentOrderBinding binder;
    @Inject
    APIInterface apiInterface;
    private RestaurantDetailModel restaurantDetailModel;
    private ArrayList<OrderDetailsModel> newOrderRequest = new ArrayList<>();
    private ArrayList<OrderDetailsModel> onGoingOrder = new ArrayList<>();
    private ArrayList<OrderDetailsModel> readyForPickupOrder = new ArrayList<>();
    private MyPagerAdapter myPagerAdapter;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.BROADCAST_ORDER_ACCEPT) || action.equals(Constants.BROADCAST_ORDER_CANCEL)|| action.equals(Constants.DRIVER_VERIFIED_WITH_OTP)) {
                getOrder(0);
            } else if (action.equals(Constants.BROADCAST_ORDER_READY_FOR_PICKUP)) {
                getOrder(1);
            }

        }
    };

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        binder.setClick(new Presenter());
        myPagerAdapter=new  MyPagerAdapter(getChildFragmentManager(), newOrderRequest, onGoingOrder, readyForPickupOrder);
        binder.viewPager.setAdapter(myPagerAdapter);
        binder.viewPager.setOffscreenPageLimit(3);
        getOrder(0);
        registerRecieverNow();
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

    @Override
    public void onResume() {
        super.onResume();

        try {
            restaurantDetailModel = MySharedPreference.getInstance(getActivity()).getUser();
            binder.toolbarTitle.setText(restaurantDetailModel.getRestaurants().getName());
            Picasso.with(getActivity()).load(restaurantDetailModel.getRestaurants().getImages().get(0)).resize(100, 100)
                    .onlyScaleDown().placeholder(R.drawable.placeholder_squre).into(binder.toolbarImage);
        }catch (Exception e){

        }

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

    private void getOrder(int selectposition) {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(getActivity());
        ((MyApplication) getActivity().getApplication()).getConfiguration().inject(this);
        apiInterface.getUpcommingOrder()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<OrderDetailsModel>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(getActivity(), throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<OrderDetailsModel>> response) {
                        progressDialog.dismiss();
                        newOrderRequest.clear();
                        onGoingOrder.clear();
                        readyForPickupOrder.clear();
                        myPagerAdapter.notifyDataSetChanged();
                        if (response.getStatus().equals("200")) {
                            for (int i = 0; i < response.getData().size(); i++) {
                                OrderDetailsModel data = response.getData().get(i);
                                if (data.getDelivery_status().equals("0")) {
                                    newOrderRequest.add(data);
                                } else if (data.getDelivery_status().equals("1")) {
                                    onGoingOrder.add(data);
                                } else if (data.getDelivery_status().equals("6")) {
                                    readyForPickupOrder.add(data);
                                }
                            }
                            Log.e("@@@@@", "Adapter");
                            myPagerAdapter.notifyDataSetChanged();
                            if (selectposition == 0) {
                                requestOrderSelect();
                                binder.viewPager.setCurrentItem(0);
                            } else if (selectposition == 1) {
                                onGoingOrderSelect();
                                binder.viewPager.setCurrentItem(1);
                            } else {
                                readyOrderSelect();
                                binder.viewPager.setCurrentItem(2);
                            }

                        } else {
                            ResponseDialog.showErrorDialog(getActivity(), response.getMessage());
                        }

                    }
                });
    }

    void registerRecieverNow() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BROADCAST_ORDER_CANCEL);
        filter.addAction(Constants.BROADCAST_ORDER_ACCEPT);
        filter.addAction(Constants.BROADCAST_ORDER_READY_FOR_PICKUP);
        filter.addAction(Constants.DRIVER_VERIFIED_WITH_OTP);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, filter);
    }

    void unRegisterRecieverNow() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterRecieverNow();
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
