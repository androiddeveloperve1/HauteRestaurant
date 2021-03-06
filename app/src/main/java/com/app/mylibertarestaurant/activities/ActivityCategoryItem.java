package com.app.mylibertarestaurant.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.CategoryItemListAdapter;
import com.app.mylibertarestaurant.databinding.ActivityCategoryItemBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryItemModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityCategoryItem extends AppCompatActivity {

    @Inject
    APIInterface apiInterface;
    PopupMenu popup;
    private ActivityCategoryItemBinding binder;
    private String catId;
    private String catName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_category_item);
        binder.setClick(new MyClick());
        catId = getIntent().getStringExtra("id");
        catName = getIntent().getStringExtra("catName");
        binder.tvCatName.setText(catName);
        initMenu();


    }

    void initMenu() {
        popup = new PopupMenu(ActivityCategoryItem.this, binder.more);
        popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent mIntent;
                switch (item.getItemId()) {
                    case R.id.add:
                        mIntent = new Intent(ActivityCategoryItem.this, ItemModificationActivity.class);
                        mIntent.putExtra("isEdit", false);
                        mIntent.putExtra("catId", catId);
                        startActivity(mIntent);
                        break;


                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getItem();
    }

    private void getItem() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ActivityCategoryItem.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ActivityCategoryItem.this);
        apiInterface.restaurantCategoryItemById(catId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<RestaurantCategoryItemModel>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ActivityCategoryItem.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<RestaurantCategoryItemModel>> response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@", new Gson().toJson(response));
                        if (response.getStatus().equals("200")) {
                            if (response.getData().size() > 0) {
                                binder.nodata.setVisibility(View.GONE);
                                binder.rvItem.setVisibility(View.VISIBLE);
                                binder.rvItem.setLayoutManager(new LinearLayoutManager(ActivityCategoryItem.this));
                                binder.rvItem.setAdapter(new CategoryItemListAdapter(new RecycleItemClickListener<RestaurantCategoryItemModel>() {
                                    @Override
                                    public void onItemClicked(int position, RestaurantCategoryItemModel data) {
                                        Intent mIntent = new Intent(ActivityCategoryItem.this, ItemDescriptionActivity.class);
                                        mIntent.putExtra("id", data.get_id());
                                        startActivity(mIntent);
                                    }
                                }, response.getData()));
                            } else {
                                binder.nodata.setVisibility(View.VISIBLE);
                                binder.rvItem.setVisibility(View.GONE);
                            }
                        } else {
                            ResponseDialog.showErrorDialog(ActivityCategoryItem.this, response.getMessage());
                        }
                    }
                });
    }

    public class MyClick {
        public void more(View v) {
            popup.show();
        }
        public void onBack(View v) {
            finish();
        }
    }

}
