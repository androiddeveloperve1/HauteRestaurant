package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.AttributeAdapter;
import com.app.mylibertarestaurant.databinding.ActivityEditItemBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.AttributeModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditItemActivity extends ImageUploadingActivity {
    @Inject
    APIInterface apiInterface;
    private ActivityEditItemBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_edit_item);

        binder.setClick(new Click());
        getAttribute();
    }

    @Override
    protected void onImageCaptured(Bitmap mBitmap) {
        binder.imgPic.setImageBitmap(mBitmap);
    }

    private void getAttribute() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(EditItemActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(EditItemActivity.this);
        apiInterface.attribute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<AttributeModel>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(EditItemActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<AttributeModel>> response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            binder.spnrAttribute.setAdapter(new AttributeAdapter(response.getData()));
                        } else {
                            ResponseDialog.showErrorDialog(EditItemActivity.this, response.getMessage());
                        }

                    }
                });
    }

    public class Click {
        public void onClose(View v) {
            finish();
        }

        public void onImageCapture(View v) {
            startCapture();
        }

        public void availbale(View v) {
            if (v.getTag().equals(getResources().getString(R.string.available))) {
                v.setTag(getResources().getString(R.string.un_available));
                v.setBackgroundResource(R.drawable.ic_toggle_unavailable);
            } else {
                v.setTag(getResources().getString(R.string.available));
                v.setBackgroundResource(R.drawable.ic_toggle_available);
            }
        }

        public void ontoogleVegNonVeg(View v) {

            if (v.getTag().equals(getResources().getString(R.string.veg))) {
                v.setTag(getResources().getString(R.string.non_veg));
                v.setBackgroundResource(R.drawable.ic_toggle_on_veg);
            } else {
                v.setTag(getResources().getString(R.string.veg));
                v.setBackgroundResource(R.drawable.ic_toggle_veg);
            }


        }

        public void save(View v) {
        }


    }
}
