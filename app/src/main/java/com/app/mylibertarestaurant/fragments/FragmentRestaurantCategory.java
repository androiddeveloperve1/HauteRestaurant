package com.app.mylibertarestaurant.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.activities.ActivityAddEditCategory;
import com.app.mylibertarestaurant.activities.ActivityCategoryItem;
import com.app.mylibertarestaurant.activities.MainActivity;
import com.app.mylibertarestaurant.activities.MyApplication;
import com.app.mylibertarestaurant.adapter.RestaurantCategoryListAdapter;
import com.app.mylibertarestaurant.databinding.FragmentRestaurantCategoryBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.io.InputStream;
import java.net.URL;
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

            @Override
            public void onItemClickedWithTag(int position, RestaurantCategoryModel data, int tag) {
                addEditMenu(true, data);
            }

            @Override
            public void onDel(int position, RestaurantCategoryModel data) {

                delAlert(data.get_id(), position);

            }

            @Override
            public void onSwitchOnOff(View v, int position, RestaurantCategoryModel data) {

                if (data.getIs_active() != null) {
                    if (data.getIs_active()) {
                        data.setIs_active(false);
                        v.setBackgroundResource(R.drawable.ic_switch_off);
                    } else {
                        data.setIs_active(true);
                        v.setBackgroundResource(R.drawable.ic_switch_on);
                    }

                } else {
                    data.setIs_active(true);
                    v.setBackgroundResource(R.drawable.ic_switch_on);
                }
                setIsActiveStatus(data.get_id(), data.getIs_active());
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

    private void delMenu(String id, int pos) {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(getActivity());
        ((MyApplication) getActivity().getApplication()).getConfiguration().inject(this);
        apiInterface.delMenu(id)
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
                        Log.e("@@@@@@@@", new Gson().toJson(response));
                        if (response.getStatus().equals("200")) {
                            list.remove(pos);
                            menuListAdapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            ResponseDialog.showErrorDialog(getActivity(), response.getMessage());
                        }

                    }
                });
    }


    void delAlert(String id, int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("MyLiberta");
        builder.setMessage("Are you sure want to delete this catagory?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                delMenu(id, pos);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void setIsActiveStatus(String id, Boolean status) {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(getActivity());
        HashMap<String, String> param = new HashMap<>();
        param.put("cateId", id);
        param.put("status", "" + status);
        ((MyApplication) getActivity().getApplication()).getConfiguration().inject(this);
        apiInterface.updateMenuStatus(param)
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
                        Log.e("@@@@@@@@", new Gson().toJson(response));
                        if (response.getStatus().equals("200")) {
                            Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            ResponseDialog.showErrorDialog(getActivity(), response.getMessage());
                        }

                    }
                });
    }


    void addEditMenu(boolean isEdit, RestaurantCategoryModel data) {
        Intent mIntent = new Intent(getActivity(), ActivityAddEditCategory.class);
        if (isEdit) {
            mIntent.putExtra("data", new Gson().toJson(data));
            mIntent.putExtra("isEdit", true);
        } else {
            mIntent.putExtra("isEdit", false);
        }

        startActivity(mIntent);
    }

    public class Click {
        public void onNavigationMenu(View v) {
            MainActivity act = (MainActivity) getActivity();
            act.navigationClick();
        }

        public void onAdd(View v) {
            addEditMenu(false, null);
        }

    }


}
