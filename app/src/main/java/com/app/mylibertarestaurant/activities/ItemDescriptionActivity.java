package com.app.mylibertarestaurant.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.AvailibilityDayAdapter;
import com.app.mylibertarestaurant.adapter.FoodTypeAvailabilityAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.ActivityItemDescriptionBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryItemModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.util.Collections;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_item_description);
        binder.setClick(new Click());
        data = new Gson().fromJson(getIntent().getStringExtra("data"), RestaurantCategoryItemModel.class);
        binder.setModel(data);
        binder.rvAvail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binder.rvAvail2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        Log.e("@@@@@@@@", "" + new Gson().toJson(data));
        Collections.reverse(data.getDaysOfWeek());
        binder.setDayAdapter(new AvailibilityDayAdapter(data.getDaysOfWeek()));
        binder.setFoodTypeAdapter(new FoodTypeAvailabilityAdapter(data.getMealAvailability()));
        initMenu();

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
                        mIntent.putExtra("flag", Constants.EDIT);
                        startActivityForResult(mIntent, 100);
                        break;
                   /* case R.id.copy:
                        mIntent = new Intent(ItemDescriptionActivity.this, ItemModificationActivity.class);
                        mIntent.putExtra("data", new Gson().toJson(data));
                        mIntent.putExtra("flag", Constants.COPY);
                        startActivityForResult(mIntent, 100);
                        break;
                    case R.id.delete:
                        deleteAlert();
                        break;*/
                }
                return false;
            }
        });

    }

    private void deleteItem() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemDescriptionActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ItemDescriptionActivity.this);
        apiInterface.deleteFoodItem(data.get_id())
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
                        if (response.getStatus().equals("200")) {
                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(ItemDescriptionActivity.this, response.getMessage());
                        }
                    }
                });
    }

    void deleteAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure, want to delete this item ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteItem();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            finish();
        }

    }

    public class Click {
        public void onBack(View v) {
            finish();
        }

        public void onPopupClick(View v) {
            popup.show();
        }
    }
}
