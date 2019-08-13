package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivitySearchItemBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.inventorynew.InventoryModelNew;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.app.mylibertarestaurant.utils.StatusbarUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivitySearchItem extends AppCompatActivity {
    @Inject
    APIInterface apiInterface;
    private ArrayList<InventoryModelNew> originalList = new ArrayList<>();
    private ArrayList<InventoryModelNew> list = new ArrayList<>();
    private ActivitySearchItemBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusbarUtils.statusBar(this);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_search_item);
        getInventory();
    }


    private void getInventory() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ActivitySearchItem.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ActivitySearchItem.this);
        apiInterface.getInventoryNew()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<InventoryModelNew>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ActivitySearchItem.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<InventoryModelNew>> response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@", new Gson().toJson(response));
                        if (response.getStatus().equals("200")) {
                            originalList.clear();
                            originalList.addAll(response.getData());
                            list.clear();
                            list.addAll(response.getData());

                        } else {
                            ResponseDialog.showErrorDialog(ActivitySearchItem.this, response.getMessage());
                        }

                    }
                });
    }
}
