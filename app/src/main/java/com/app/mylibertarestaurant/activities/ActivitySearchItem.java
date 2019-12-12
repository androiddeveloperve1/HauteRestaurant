package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.CategoryItemListAdapter;
import com.app.mylibertarestaurant.databinding.ActivitySearchItemBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.inventorynew.InventoryModelNew;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.app.mylibertarestaurant.utils.StatusbarUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

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
    private CategoryItemListAdapter inventoryItemAdapter;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusbarUtils.statusBar(this);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_search_item);
        binder.setClick(new MyClick());
       /* inventoryItemAdapter = new CategoryItemListAdapter(new RecycleItemClickListener<InventoryModelNew>() {
            @Override
            public void onItemClicked(int position, InventoryModelNew data) {
                Intent mIntent = new Intent(ActivitySearchItem.this, ItemDescriptionActivity.class);
                mIntent.putExtra("data", new Gson().toJson(data));
                startActivity(mIntent);
            }
        }, list);*/
        binder.rvItems.setLayoutManager(new LinearLayoutManager(this));
        binder.setAdapter(inventoryItemAdapter);
        binder.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // user typed: start the timer
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binder.tvNoResult.setVisibility(View.GONE);
                                if (editable.toString().trim().length() > 0) {
                                    binder.tvCancel.setVisibility(View.VISIBLE);
                                    filterNow(editable.toString().trim());
                                } else {
                                    binder.tvCancel.setVisibility(View.GONE);
                                    list.clear();
                                    list.addAll(originalList);
                                    inventoryItemAdapter.notifyDataSetChanged();
                                }

                            }
                        });

                    }
                }, 300);
            }
        });
        getInventory();
    }


    private void getInventory() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ActivitySearchItem.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ActivitySearchItem.this);
        HashMap<String, String> param = new HashMap<>();
        param.put("category", "");
        apiInterface.getInventoryNew(param)
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
                        if (response.getStatus().equals("200")) {
                            originalList.clear();
                            originalList.addAll(response.getData());
                            list.clear();
                            list.addAll(response.getData());
                            inventoryItemAdapter.notifyDataSetChanged();

                        } else {
                            ResponseDialog.showErrorDialog(ActivitySearchItem.this, response.getMessage());
                        }

                    }
                });
    }


    void filterNow(String itemName) {
        list.clear();
        for (int i = 0; i < originalList.size(); i++) {
            if (originalList.get(i).getItem_id().getName().toLowerCase().contains(itemName.toLowerCase())) {
                list.add(originalList.get(i));
            }
        }
        if (list.size() <= 0) {
            binder.tvNoResult.setVisibility(View.VISIBLE);
        }
        inventoryItemAdapter.notifyDataSetChanged();

    }

    public class MyClick {

        public void onCancel(View v) {
            binder.tvNoResult.setVisibility(View.GONE);
            try {
                binder.etSearch.setText("");
                binder.tvCancel.setVisibility(View.GONE);
                list.clear();
                list.addAll(originalList);
                inventoryItemAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }

        }

        public void onBack(View v) {
            finish();
        }


    }
}
