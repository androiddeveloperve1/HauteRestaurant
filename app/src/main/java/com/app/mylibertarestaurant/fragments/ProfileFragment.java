package com.app.mylibertarestaurant.fragments;

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
import com.app.mylibertarestaurant.adapter.MyPagerAdapter;
import com.app.mylibertarestaurant.adapter.TimeAdapter;
import com.app.mylibertarestaurant.databinding.FragmentProfileBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.app.mylibertarestaurant.model.items.RestaurantDetail;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
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

        getProfile();

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
                        Log.e("@@----@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
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
        binder.tvReatsurantName.setText(restaurantDetailModel.getRestaurants().getName());
        binder.tvReatsurantAddress.setText(restaurantDetailModel.getRestaurants().getAddress());
        binder.tvZip.setText(restaurantDetailModel.getRestaurants().getPincode());
        binder.tvDeliveryTime.setText(restaurantDetailModel.getRestaurants().getMaxdeliverytime());
        binder.tvDeliveryRange.setText(restaurantDetailModel.getRestaurants().getDeliverykm() + " Km.");
        Picasso.with(getActivity()).load(restaurantDetailModel.getRestaurants().getImages().get(0)).placeholder(R.drawable.placeholder_squre).into(binder.ivProfile);

    }



    public class MyClick {
        public void editRestaurant(View v) {
            Intent mintent=new Intent(getActivity(), EditProfileActivity.class);
            startActivityForResult(mintent,100);
        }

        public void editServices(View v) {
            startActivity(new Intent(getActivity(), EditServiceDaysActivity.class));
        }

        public void logout(View v) {

            MySharedPreference.getInstance(getActivity()).clearMyPreference();
            getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
