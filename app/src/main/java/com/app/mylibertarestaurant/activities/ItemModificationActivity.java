package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.AttributeAdapter;
import com.app.mylibertarestaurant.adapter.CategoryAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.ActivityItemModificationBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.AttributeModel;
import com.app.mylibertarestaurant.model.CategoryModel;
import com.app.mylibertarestaurant.model.InventoryModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.model.inventorynew.AttributeModelNew;
import com.app.mylibertarestaurant.model.inventorynew.InventoryModelNew;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryItemModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.MultipartUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ItemModificationActivity extends ImageUploadingActivity {

    @Inject
    APIInterface apiInterface;
    private Bitmap restaurantImage;
    private ActivityItemModificationBinding binder;
    private boolean isEdit;
    private RestaurantCategoryItemModel data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_item_modification);
        binder.setClick(new Click());
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        if (isEdit) {
            data = new Gson().fromJson(getIntent().getStringExtra("data"), RestaurantCategoryItemModel.class);
            binder.header.setText(data.getName());
            binder.tvSave.setText("Update");
        } else {
            binder.header.setText("Add Item");
            binder.tvSave.setText("Save");
        }
    }

    @Override
    protected void onImageCaptured(Bitmap mBitmap) {
        restaurantImage = mBitmap;
        binder.imgPic.setImageBitmap(restaurantImage);
    }


    private Target getTarget(final String url) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap result, Picasso.LoadedFrom from) {

                Log.e("@@@@@@", "Image loded");
                restaurantImage = result;
                binder.imgPic.setImageBitmap(restaurantImage);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e("@@@@@@", "Image failed");
                restaurantImage = null;
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.e("@@@@@@", "prepare Image loded");

            }
        };
        return target;
    }


    private void addFoodItem() {

        MultipartBody.Part image = MultipartUtils.createFile(ItemModificationActivity.this, restaurantImage, "food_image", "food_image.jpg");
        RequestBody item_id = RequestBody.create(MediaType.parse("text/plain"), binder.etProductName.getText().toString().trim());

        RequestBody restaurent_id = RequestBody.create(MediaType.parse("text/plain"), "");

        RequestBody category_id = RequestBody.create(MediaType.parse("text/plain"), "");

        RequestBody price_devide = RequestBody.create(MediaType.parse("text/plain"), "0");

        RequestBody full_price = RequestBody.create(MediaType.parse("text/plain"), binder.etProductPrice.getText().toString().trim());

        RequestBody half_price = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody attribute = RequestBody.create(MediaType.parse("text/plain"), "");

        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), binder.tvDesc.getText().toString().trim());

        String foodType = "veg";

        RequestBody food_type = RequestBody.create(MediaType.parse("text/plain"), foodType);


        String isAvailbale = "1";
        if (binder.tvProductAvailSwitch.getTag().equals(getResources().getString(R.string.un_available))) {
            isAvailbale = "0";
        }
        RequestBody is_available = RequestBody.create(MediaType.parse("text/plain"), isAvailbale);

        final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemModificationActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ItemModificationActivity.this);
        apiInterface.addFoodItem(image, item_id, restaurent_id, category_id, price_devide, full_price, half_price, food_type, attribute, description, is_available)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ItemModificationActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            Toast.makeText(ItemModificationActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(ItemModificationActivity.this, response.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            ArrayList<AttributeModelNew> arayList = new Gson().fromJson(data.getStringExtra("data"), new TypeToken<ArrayList<AttributeModelNew>>() {
            }.getType());
        }
    }

    private void editFoodItem() {
        MultipartBody.Part image = MultipartUtils.createFile(ItemModificationActivity.this, restaurantImage, "food_image", "food_image.jpg");
        RequestBody item_id = RequestBody.create(MediaType.parse("text/plain"), binder.etProductName.getText().toString().trim());
        RequestBody restaurent_id = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody category_id = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody price_devide = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody full_price = RequestBody.create(MediaType.parse("text/plain"), binder.etProductPrice.getText().toString().trim());
        RequestBody half_price = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), binder.tvDesc.getText().toString().trim());
        String foodType = "veg";

        RequestBody food_type = RequestBody.create(MediaType.parse("text/plain"), foodType);


        String isAvailbale = "1";
        if (binder.tvProductAvailSwitch.getTag().equals(getResources().getString(R.string.un_available))) {
            isAvailbale = "0";
        }
        RequestBody is_available = RequestBody.create(MediaType.parse("text/plain"), isAvailbale);
        RequestBody food_itemId = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody attribute = RequestBody.create(MediaType.parse("text/plain"), "");


        final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemModificationActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ItemModificationActivity.this);
        apiInterface.editFoodItem(image, item_id, restaurent_id, category_id, price_devide, full_price, half_price, food_type, attribute, description, is_available, food_itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ItemModificationActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            Toast.makeText(ItemModificationActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();

                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(ItemModificationActivity.this, response.getMessage());
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
            if (restaurantImage == null) {
                Toast.makeText(ItemModificationActivity.this, "Please select the restaurant image", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.etProductName.getText().toString().trim().length() <= 0) {
                Toast.makeText(ItemModificationActivity.this, "Please enter the product name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (binder.etProductPrice.getText().toString().trim().length() <= 0) {
                Toast.makeText(ItemModificationActivity.this, "Please enter the product price", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvDesc.getText().toString().trim().length() <= 0) {
                Toast.makeText(ItemModificationActivity.this, "Please enter the product description", Toast.LENGTH_SHORT).show();
                return;
            }
            addFoodItem();
        }


    }
}
