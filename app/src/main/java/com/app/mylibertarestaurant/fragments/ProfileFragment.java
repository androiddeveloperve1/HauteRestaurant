package com.app.mylibertarestaurant.fragments;

import android.app.Activity;
import android.app.Dialog;
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
import com.app.mylibertarestaurant.activities.MyApplication;
import com.app.mylibertarestaurant.adapter.TimeAdapter;
import com.app.mylibertarestaurant.databinding.FragmentProfileBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.model.TimeModel;
import com.app.mylibertarestaurant.model.TimeSlotModel;
import com.app.mylibertarestaurant.model.items.RestaurantDetail;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.AppUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;
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

public class ProfileFragment extends Fragment {
    RestaurantDetailModel restaurantDetailModel;
    @Inject
    APIInterface apiInterface;
    private FragmentProfileBinding binder;
    private Resources mResources;

    private int colorRed;
    private int colorGreen;
    private int colorAvailable;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        restaurantDetailModel = MySharedPreference.getInstance(getActivity()).getUser();
        binder.toolbarManu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity act = (MainActivity) getActivity();
                act.navigationClick();
            }
        });
        binder.setClick(new MyClick());
        mResources = getActivity().getResources();

        colorGreen = mResources.getColor(R.color.greenColorAlpha);
        colorRed = mResources.getColor(R.color.redAlpha);

        getProfile();


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


    void setColorToTab() {
        if (restaurantDetailModel.getRestaurants().getOpenForService().get(0).isIs_selected()) {
            colorAvailable = colorGreen;
        } else {
            colorAvailable = colorRed;
        }
        binder.includeLayout.tvMon.setTextColor(colorAvailable);
        binder.includeLayout.lineMon.setBackgroundColor(colorAvailable);


        if (restaurantDetailModel.getRestaurants().getOpenForService().get(1).isIs_selected()) {
            colorAvailable = colorGreen;
        } else {
            colorAvailable = colorRed;

        }
        binder.includeLayout.tvTue.setTextColor(colorAvailable);
        binder.includeLayout.lineTue.setBackgroundColor(colorAvailable);


        if (restaurantDetailModel.getRestaurants().getOpenForService().get(2).isIs_selected()) {
            colorAvailable = colorGreen;
        } else {
            colorAvailable = colorRed;

        }
        binder.includeLayout.tvWed.setTextColor(colorAvailable);
        binder.includeLayout.lineWed.setBackgroundColor(colorAvailable);


        if (restaurantDetailModel.getRestaurants().getOpenForService().get(3).isIs_selected()) {
            colorAvailable = colorGreen;
        } else {
            colorAvailable = colorRed;

        }
        binder.includeLayout.tvThru.setTextColor(colorAvailable);
        binder.includeLayout.lineThu.setBackgroundColor(colorAvailable);


        if (restaurantDetailModel.getRestaurants().getOpenForService().get(4).isIs_selected()) {
            colorAvailable = colorGreen;
        } else {
            colorAvailable = colorRed;

        }
        binder.includeLayout.tvFri.setTextColor(colorAvailable);
        binder.includeLayout.lineFri.setBackgroundColor(colorAvailable);


        if (restaurantDetailModel.getRestaurants().getOpenForService().get(5).isIs_selected()) {
            colorAvailable = colorGreen;
        } else {
            colorAvailable = colorRed;

        }
        binder.includeLayout.tvSat.setTextColor(colorAvailable);
        binder.includeLayout.lineSat.setBackgroundColor(colorAvailable);


        if (restaurantDetailModel.getRestaurants().getOpenForService().get(6).isIs_selected()) {
            colorAvailable = colorGreen;
        } else {
            colorAvailable = colorRed;

        }
        binder.includeLayout.tvSun.setTextColor(colorAvailable);
        binder.includeLayout.lineSun.setBackgroundColor(colorAvailable);
    }


    void mon() {
        binder.includeLayout.lineMon.setVisibility(View.VISIBLE);
        binder.includeLayout.lineTue.setVisibility(View.GONE);
        binder.includeLayout.lineWed.setVisibility(View.GONE);
        binder.includeLayout.lineThu.setVisibility(View.GONE);
        binder.includeLayout.lineFri.setVisibility(View.GONE);
        binder.includeLayout.lineSat.setVisibility(View.GONE);
        binder.includeLayout.lineSun.setVisibility(View.GONE);
        setColorToTab();

    }

    void tue() {


        binder.includeLayout.lineMon.setVisibility(View.GONE);
        binder.includeLayout.lineTue.setVisibility(View.VISIBLE);
        binder.includeLayout.lineWed.setVisibility(View.GONE);
        binder.includeLayout.lineThu.setVisibility(View.GONE);
        binder.includeLayout.lineFri.setVisibility(View.GONE);
        binder.includeLayout.lineSat.setVisibility(View.GONE);
        binder.includeLayout.lineSun.setVisibility(View.GONE);
        setColorToTab();
    }

    void wed() {

        binder.includeLayout.lineMon.setVisibility(View.GONE);
        binder.includeLayout.lineTue.setVisibility(View.GONE);
        binder.includeLayout.lineWed.setVisibility(View.VISIBLE);
        binder.includeLayout.lineThu.setVisibility(View.GONE);
        binder.includeLayout.lineFri.setVisibility(View.GONE);
        binder.includeLayout.lineSat.setVisibility(View.GONE);
        binder.includeLayout.lineSun.setVisibility(View.GONE);
        setColorToTab();
    }

    void thur() {
        binder.includeLayout.lineMon.setVisibility(View.GONE);
        binder.includeLayout.lineTue.setVisibility(View.GONE);
        binder.includeLayout.lineWed.setVisibility(View.GONE);
        binder.includeLayout.lineThu.setVisibility(View.VISIBLE);
        binder.includeLayout.lineFri.setVisibility(View.GONE);
        binder.includeLayout.lineSat.setVisibility(View.GONE);
        binder.includeLayout.lineSun.setVisibility(View.GONE);
        setColorToTab();
    }

    void fri() {
        binder.includeLayout.lineMon.setVisibility(View.GONE);
        binder.includeLayout.lineTue.setVisibility(View.GONE);
        binder.includeLayout.lineWed.setVisibility(View.GONE);
        binder.includeLayout.lineThu.setVisibility(View.GONE);
        binder.includeLayout.lineFri.setVisibility(View.VISIBLE);
        binder.includeLayout.lineSat.setVisibility(View.GONE);
        binder.includeLayout.lineSun.setVisibility(View.GONE);
        setColorToTab();
    }

    void sat() {


        binder.includeLayout.lineMon.setVisibility(View.GONE);
        binder.includeLayout.lineTue.setVisibility(View.GONE);
        binder.includeLayout.lineWed.setVisibility(View.GONE);
        binder.includeLayout.lineThu.setVisibility(View.GONE);
        binder.includeLayout.lineFri.setVisibility(View.GONE);
        binder.includeLayout.lineSat.setVisibility(View.VISIBLE);
        binder.includeLayout.lineSun.setVisibility(View.GONE);
        setColorToTab();

    }

    void sun() {
        binder.includeLayout.lineMon.setVisibility(View.GONE);
        binder.includeLayout.lineTue.setVisibility(View.GONE);
        binder.includeLayout.lineWed.setVisibility(View.GONE);
        binder.includeLayout.lineThu.setVisibility(View.GONE);
        binder.includeLayout.lineFri.setVisibility(View.GONE);
        binder.includeLayout.lineSat.setVisibility(View.GONE);
        binder.includeLayout.lineSun.setVisibility(View.VISIBLE);
        setColorToTab();

    }


    private void getProfile() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(getActivity());
        ((MyApplication) getActivity().getApplication()).getConfiguration().inject(ProfileFragment.this);
        apiInterface.getDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<RestaurantDetail>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(getActivity(), throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<RestaurantDetail> response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            Log.e("---Response", new Gson().toJson(response.getData()));
                            restaurantDetailModel.setRestaurants(response.getData());
                            MySharedPreference.getInstance(getActivity()).setUser(restaurantDetailModel);
                            showInView();
                        } else {
                            ResponseDialog.showErrorDialog(getActivity(), response.getMessage());
                        }

                    }
                });
    }

    void showInView() {
        Log.e("---Show in view", "------------------");

        binder.tvReatsurantName.setText(restaurantDetailModel.getRestaurants().getName());
        binder.tvReatsurantAddress.setText(restaurantDetailModel.getRestaurants().getAddress());
        binder.tvZip.setText(restaurantDetailModel.getRestaurants().getPincode());
        binder.tvDeliveryTime.setText(restaurantDetailModel.getRestaurants().getMaxdeliverytime() + " Mins.");
        binder.tvDeliveryRange.setText(restaurantDetailModel.getRestaurants().getDeliverykm() + " Km.");
        binder.tvDeliveryFee.setText("$ "+restaurantDetailModel.getRestaurants().getDeliveryfees());
        Picasso.with(getActivity()).load(restaurantDetailModel.getRestaurants().getImages().get(0)).resize(200, 200).onlyScaleDown().placeholder(R.drawable.placeholder_squre).into(binder.ivProfile);
        if (!(restaurantDetailModel.getRestaurants().getOpenForService() != null && restaurantDetailModel.getRestaurants().getOpenForService().size() > 0)) {
            restaurantDetailModel.getRestaurants().setOpenForService(AppUtils.initDummyTimeData());
        }

        binder.viewPager.setAdapter(new TimeAdapter(getActivity(), restaurantDetailModel.getRestaurants().getOpenForService()));
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
        binder.viewPager.setCurrentItem(0);
        binder.viewPager.setOffscreenPageLimit(7);
        mon();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            restaurantDetailModel = MySharedPreference.getInstance(getActivity()).getUser();
            showInView();
        }

    }

    private void logOut() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(getActivity());
        ((MyApplication) getActivity().getApplication()).getConfiguration().inject(ProfileFragment.this);
        apiInterface.logOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(getActivity(), throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            MySharedPreference.getInstance(getActivity()).clearMyPreference();
                            getActivity().finish();
                        } else {
                            ResponseDialog.showErrorDialog(getActivity(), response.getMessage());
                        }

                    }
                });
    }


    public class MyClick {
        public void editRestaurant(View v) {
            Intent mintent = new Intent(getActivity(), EditProfileActivity.class);
            startActivityForResult(mintent, 100);
        }

        public void editServices(View v) {
            startActivityForResult(new Intent(getActivity(), EditServiceDaysActivity.class), 100);
        }

        public void logout(View v) {
            logOut();
        }
    }


}
