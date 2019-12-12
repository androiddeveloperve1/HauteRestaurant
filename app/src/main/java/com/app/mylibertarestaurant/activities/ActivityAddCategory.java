package com.app.mylibertarestaurant.activities;

import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityAddCategoryBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.inventorynew.InventoryModelNew;
import com.app.mylibertarestaurant.model.items.RestaurantDetail;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.MultipartUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityAddCategory extends ImageUploadingActivity {

    @Inject
    APIInterface apiInterface;
    private ActivityAddCategoryBinding binder;
    private Bitmap menuItemImage;
    private RestaurantDetail restaurantDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_add_category);
        binder.setClick(new MyClick());
        restaurantDetail = MySharedPreference.getInstance(ActivityAddCategory.this).getUser().getRestaurants();
    }

    @Override
    protected void onImageCaptured(Bitmap mBitmap) {
        menuItemImage = mBitmap;
        binder.rlMenuImage.setVisibility(View.VISIBLE);
        binder.rlUpload.setVisibility(View.GONE);
        binder.imgMenu.setImageBitmap(menuItemImage);

    }

    private void saveCategory(boolean isUpdate) {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ActivityAddCategory.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ActivityAddCategory.this);

        MultipartBody.Part image = MultipartUtils.createFile(ActivityAddCategory.this, menuItemImage, "category_image", "food_image.jpg");
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), binder.etName.getText().toString().trim());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), binder.etDescription.getText().toString());
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), restaurantDetail.getAddress());
        RequestBody restaurent_id = RequestBody.create(MediaType.parse("text/plain"), restaurantDetail.get_id());

        apiInterface.saveCategory(image, name, description, location, restaurent_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ActivityAddCategory.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            Toast.makeText(ActivityAddCategory.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(ActivityAddCategory.this, response.getMessage());
                        }

                    }
                });
    }

    public class MyClick {
        public void onBack(View v) {
            finish();
        }

        public void selectImage(View v) {
            startCapture();
        }

        public void save(View v) {
            if (binder.etName.getText().toString().trim().length() > 0) {
                if (binder.etDescription.getText().toString().trim().length() > 0) {
                    if (menuItemImage != null) {
                        //saveCategory(false);
                    } else {
                        Toast.makeText(ActivityAddCategory.this, "Please select the image for menu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityAddCategory.this, "Please enter the description of menu", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ActivityAddCategory.this, "Please enter the title of menu", Toast.LENGTH_SHORT).show();
            }
        }


        public void delImage(View v) {
            menuItemImage = null;
            binder.rlMenuImage.setVisibility(View.GONE);
            binder.rlUpload.setVisibility(View.VISIBLE);
        }
    }
}
