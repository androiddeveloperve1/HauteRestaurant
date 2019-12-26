package com.app.mylibertarestaurant.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.AvailibilityDayAdapter;
import com.app.mylibertarestaurant.adapter.DietaryLabelAdapter;
import com.app.mylibertarestaurant.adapter.FoodTypeAvailabilityAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.customui.Utils;
import com.app.mylibertarestaurant.databinding.ActivityItemDescriptionBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.newP.MainOptionModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryItemModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.utils.AppUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.zip.Inflater;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ItemDescriptionActivity extends AppCompatActivity {
    ActivityItemDescriptionBinding binder;
    PopupMenu popup;
    @Inject
    APIInterface apiInterface;
    private RestaurantCategoryItemModel data;
    private String menuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_item_description);
        binder.setClick(new Click());
        menuId = getIntent().getStringExtra("id");
        initMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMenuDetail();
    }

    void initData() {
        binder.setModel(data);
        binder.rvAvail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binder.rvAvail2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binder.rvDietLabel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        Collections.reverse(data.getDaysOfWeek());
        binder.setDayAdapter(new AvailibilityDayAdapter(data.getDaysOfWeek()));
        binder.setFoodTypeAdapter(new FoodTypeAvailabilityAdapter(data.getMealAvailability()));
        binder.setDietaryAdapter(new DietaryLabelAdapter(data.getDietaryLabels()));
        initOptionAndSubOption();
    }


    void initMenu() {
        popup = new PopupMenu(ItemDescriptionActivity.this, binder.ivThreeDot);
        popup.getMenuInflater().inflate(R.menu.pop_up, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent mIntent;
                switch (item.getItemId()) {
                    case R.id.edit:
                        mIntent = new Intent(ItemDescriptionActivity.this, ItemModificationActivity.class);
                        mIntent.putExtra("data", new Gson().toJson(data));
                        mIntent.putExtra("isEdit", true);
                        startActivity(mIntent);
                        break;
                    case R.id.del:
                        del();
                        break;


                }
                return false;
            }
        });

    }


    void del() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemDescriptionActivity.this);
        builder.setTitle("MyLiberta");
        builder.setMessage("Are you sure want to delete this Item?");
        builder.setPositiveButton("Del", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemDescriptionActivity.this);
                ((MyApplication) getApplication()).getConfiguration().inject(ItemDescriptionActivity.this);
                apiInterface.delMenuItem(menuId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ApiResponseModel>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                progressDialog.dismiss();
                                ResponseDialog.showErrorDialog(ItemDescriptionActivity.this, throwable.getLocalizedMessage());
                            }

                            @Override
                            public void onNext(ApiResponseModel response) {
                                progressDialog.dismiss();
                                Log.e("@@@@@@@@", new Gson().toJson(response));
                                if (response.getStatus().equals("200")) {
                                    finish();
                                } else {
                                    ResponseDialog.showErrorDialog(ItemDescriptionActivity.this, response.getMessage());
                                }

                            }
                        });
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


    void initOptionAndSubOption() {
        binder.llOption.removeAllViews();
        for (int i = 0; i < data.getOptionsResult().size(); i++) {
            View mainOption = getLayoutInflater().inflate(R.layout.item_option_show, null);
            mainOption.setId(i);
            TextView tv = mainOption.findViewById(R.id.tv_text);
            MainOptionModel mainOptionModel = data.getOptionsResult().get(i);
            tv.setText(mainOptionModel.getName());
            LinearLayout ll_sub_option = mainOption.findViewById(R.id.ll_sub_option);
            for (int j = 0; j < mainOptionModel.getSubOptionsResult().size(); j++) {
                View subOption = getLayoutInflater().inflate(R.layout.item_sub_option_show, null);
                subOption.setId(j);
                TextView tv_name = subOption.findViewById(R.id.tv_name);
                tv_name.setText(mainOptionModel.getSubOptionsResult().get(j).getName() + " ($" + AppUtils.getDecimalFormat(mainOptionModel.getSubOptionsResult().get(j).getBestPrice()) + ")");
                ll_sub_option.addView(subOption);
            }
            binder.llOption.addView(mainOption);

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            finish();
        }

    }

    private void getMenuDetail() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemDescriptionActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ItemDescriptionActivity.this);
        apiInterface.getMenuDetail(menuId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<RestaurantCategoryItemModel>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ItemDescriptionActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<RestaurantCategoryItemModel>> response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@", new Gson().toJson(response));
                        if (response.getStatus().equals("200")) {
                            data = response.getData().get(0);
                            initData();
                        } else {
                            ResponseDialog.showErrorDialog(ItemDescriptionActivity.this, response.getMessage());
                        }
                    }
                });
    }

    public class Click {
        public void onBack(View v) {
            Log.e("@@@@@@@", "Clicked");
            finish();
        }

        public void goOptionDetail(View v) {
            Intent mIntent = new Intent(ItemDescriptionActivity.this, OptionDetailsActivity.class);
            mIntent.putExtra("data", new Gson().toJson(data));
            startActivity(mIntent);

        }

        public void onPopupClick(View v) {
            popup.show();
        }
    }
}
