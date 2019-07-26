package com.app.mylibertarestaurant.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.ActivityItemDescriptionBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.InventoryModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ItemDescriptionActivity extends AppCompatActivity {
    ActivityItemDescriptionBinding binder;
    PopupMenu popup;
    @Inject
    APIInterface apiInterface;
    String ID;
    private InventoryModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_item_description);
        binder.setClick(new Click());
        data = new Gson().fromJson(getIntent().getStringExtra("data"), InventoryModel.class);
        binder.setModel(data);
        ID = getIntent().getStringExtra("id");
        initMenu();
    }

    void initMenu() {
        popup = new PopupMenu(ItemDescriptionActivity.this, binder.ivThreeDot);
        popup.getMenuInflater().inflate(R.menu.pop_up, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.edit:
                        //startActivity(new Intent(ItemDescriptionActivity.this, EditItemActivity.class));
                        break;
                    case R.id.copy:
                        /*Intent mIntent = new Intent(ItemDescriptionActivity.this, CopyItemActivity.class);
                        mIntent.putExtra("data", new Gson().toJson(data));
                        mIntent.putExtra("flag", Constants.COPY);
                        startActivity(mIntent);*/
                        break;
                    case R.id.delete:
                        //deleteAlert();
                        break;
                }
                return false;
            }
        });
    }

    private void deleteItem() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemDescriptionActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ItemDescriptionActivity.this);
        apiInterface.deleteFoodItem(ID)
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

    public class Click {
        public void onBack(View v) {
            finish();

        }

        public void onPopupClick(View v) {
            popup.show();
        }
    }
}
