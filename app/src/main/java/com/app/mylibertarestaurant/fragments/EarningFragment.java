package com.app.mylibertarestaurant.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.activities.MyApplication;
import com.app.mylibertarestaurant.adapter.EarnAdapter;
import com.app.mylibertarestaurant.adapter.MyPagerAdapter;
import com.app.mylibertarestaurant.databinding.FragmentTodayEarningBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.utils.ResponseDialog;
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

public class EarningFragment extends Fragment {
    FragmentTodayEarningBinding binder;
    @Inject
    APIInterface apiInterface;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_today_earning, container, false);
        binder.rvItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        getHistory();

        binder.toolbarManu.setOnClickListener((v) -> {
            MainActivity act = (MainActivity) getActivity();
            act.navigationClick();
        });
        View view = binder.getRoot();
        return view;
    }


    private void getHistory() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(getActivity());
        ((MyApplication) getActivity().getApplication()).getConfiguration().inject(this);
        apiInterface.orderHistory()
                .subscribeOn(Schedulers.io())
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
                        if (response.getStatus().equals("200")) {

                            if (response.getData().size() > 0) {
                                binder.tvNoData.setVisibility(View.GONE);
                                binder.rvItems.setVisibility(View.VISIBLE);
                                binder.setAdp(new EarnAdapter(response.getData(), new RecycleItemClickListener() {
                                    @Override
                                    public void onItemClicked(int position, Object data) {
                                    }
                                }));

                            } else {
                                binder.tvNoData.setVisibility(View.VISIBLE);
                                binder.rvItems.setVisibility(View.GONE);
                            }


                        } else {
                            ResponseDialog.showErrorDialog(getActivity(), response.getMessage());
                        }

                    }
                });
    }
}
