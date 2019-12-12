package com.app.mylibertarestaurant.fragments;

import android.content.Intent;
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
import com.app.mylibertarestaurant.activities.ActivityAddCategory;
import com.app.mylibertarestaurant.activities.ActivityCategoryItem;
import com.app.mylibertarestaurant.activities.ItemDescriptionActivity;
import com.app.mylibertarestaurant.activities.ItemModificationActivity;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.activities.MyApplication;
import com.app.mylibertarestaurant.adapter.RestaurantCategoryListAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.FragmentRestaurantCategoryBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
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

public class FragmentRestaurantCategory extends Fragment {

    @Inject
    APIInterface apiInterface;
    private FragmentRestaurantCategoryBinding binder;
    private RestaurantCategoryListAdapter menuListAdapter;
    private ArrayList<RestaurantCategoryModel> list = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_category, container, false);
        binder.setClick(new Click());
        binder.rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        menuListAdapter = new RestaurantCategoryListAdapter(new RecycleItemClickListener<RestaurantCategoryModel>() {
            @Override
            public void onItemClicked(int position, RestaurantCategoryModel data) {
                Intent mIntent = new Intent(getActivity(), ActivityCategoryItem.class);
                mIntent.putExtra("id", data.get_id());
                mIntent.putExtra("catName", data.getName());
                startActivityForResult(mIntent, 100);
            }
        }, list);

        binder.setAdapter(menuListAdapter);
        binder.pullRefresh.setOnRefreshListener(() -> {
            getCategoryList();

        });
        getCategoryList();
        View view = binder.getRoot();
        return view;
    }

    private void getCategoryList() {
        binder.pullRefresh.setRefreshing(true);
        ((MyApplication) getActivity().getApplication()).getConfiguration().inject(this);
        apiInterface.restaurantCategory(MySharedPreference.getInstance(getActivity()).getUser().getRestaurants().get_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<RestaurantCategoryModel>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        binder.pullRefresh.setRefreshing(false);
                        ResponseDialog.showErrorDialog(getActivity(), throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<RestaurantCategoryModel>> response) {
                        Log.e("@@@@@@@@", new Gson().toJson(response));
                        binder.pullRefresh.setRefreshing(false);
                        if (response.getStatus().equals("200")) {
                            list.clear();
                            list.addAll(response.getData());
                            if (response.getData().size() > 0) {
                                binder.tvNoData.setVisibility(View.GONE);
                                binder.pullRefresh.setVisibility(View.VISIBLE);
                                menuListAdapter.notifyDataSetChanged();
                            } else {
                                binder.tvNoData.setVisibility(View.VISIBLE);
                                binder.pullRefresh.setVisibility(View.GONE);
                            }

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

        public void onAdd(View v) {
            Intent mIntent = new Intent(getActivity(), ActivityAddCategory.class);
            startActivity(mIntent);
        }

    }
}
