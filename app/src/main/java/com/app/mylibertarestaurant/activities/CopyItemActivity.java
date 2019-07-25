package com.app.mylibertarestaurant.activities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.AttributeAdapter;
import com.app.mylibertarestaurant.adapter.CategoryAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.ActivityCopyItemBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.AttributeModel;
import com.app.mylibertarestaurant.model.CategoryModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.model.items.RestaurantDetail;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.MultipartUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CopyItemActivity extends ImageUploadingActivity {
    ActivityCopyItemBinding binder;
    @Inject
    APIInterface apiInterface;
    private RestaurantDetailModel restaurantDetailModel;
    private int flag;
    private Bitmap restaurantImage;


    private ArrayList<CategoryModel> categoryList = new ArrayList<>();
    private ArrayList<AttributeModel> attributeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_copy_item);
        binder.setClick(new Click());
        flag = getIntent().getIntExtra("flag", 0);
        restaurantDetailModel = MySharedPreference.getInstance(CopyItemActivity.this).getUser();
        getAttribute();
        getCategory();
    }

    @Override
    protected void onImageCaptured(Bitmap mBitmap) {
        restaurantImage = mBitmap;
        binder.imgPic.setImageBitmap(restaurantImage);
    }

    private void getAttribute() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(CopyItemActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(CopyItemActivity.this);
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
                        ResponseDialog.showErrorDialog(CopyItemActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<AttributeModel>> response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            attributeList.clear();
                            attributeList.addAll(response.getData());
                            binder.spnrAttribute.setAdapter(new AttributeAdapter(attributeList));

                            if (flag == Constants.ADD_NEW) {
                                //binder.spnrAttribute.setSelection(0);
                            }
                        } else {
                            ResponseDialog.showErrorDialog(CopyItemActivity.this, response.getMessage());
                        }
                    }
                });
    }

    private void getCategory() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(CopyItemActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(CopyItemActivity.this);
        apiInterface.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<CategoryModel>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(CopyItemActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<CategoryModel>> response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            categoryList.clear();
                            categoryList.addAll(response.getData());
                            binder.spnrCategory.setAdapter(new CategoryAdapter(categoryList));
                            if (flag == Constants.ADD_NEW) {
                                //binder.spnrCategory.setSelection(0);
                            }
                        } else {
                            ResponseDialog.showErrorDialog(CopyItemActivity.this, response.getMessage());
                        }

                    }
                });
    }

    private void addFoodItem() {

        MultipartBody.Part image = MultipartUtils.createFile(CopyItemActivity.this, restaurantImage, "food_image", "food_image.jpg");
        RequestBody item_id = RequestBody.create(MediaType.parse("text/plain"), binder.etProductName.getText().toString().trim());

        RequestBody restaurent_id = RequestBody.create(MediaType.parse("text/plain"), restaurantDetailModel.getRestaurants().get_id());

        RequestBody category_id = RequestBody.create(MediaType.parse("text/plain"), categoryList.get(binder.spnrCategory.getSelectedItemPosition()).get_id());

        RequestBody price_devide = RequestBody.create(MediaType.parse("text/plain"), "0");

        RequestBody full_price = RequestBody.create(MediaType.parse("text/plain"), binder.etProductPrice.getText().toString().trim());

        RequestBody half_price = RequestBody.create(MediaType.parse("text/plain"), "0");


        RequestBody attribute_id = RequestBody.create(MediaType.parse("text/plain"), attributeList.get(binder.spnrCategory.getSelectedItemPosition()).get_id());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), binder.tvDesc.getText().toString().trim());

        String foodType = "veg";
        if (binder.imgVegNonvegSwitch.getTag().equals(getResources().getString(R.string.non_veg))) {
            foodType = "non-veg";
        }
        RequestBody food_type = RequestBody.create(MediaType.parse("text/plain"), foodType);


        String isAvailbale = "1";
        if (binder.tvProductAvailSwitch.getTag().equals(getResources().getString(R.string.un_available))) {
            isAvailbale = "0";
        }
        RequestBody is_available = RequestBody.create(MediaType.parse("text/plain"), isAvailbale);

        final Dialog progressDialog = ResponseDialog.showProgressDialog(CopyItemActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(CopyItemActivity.this);
        apiInterface.addFoodItem(image, item_id, restaurent_id, category_id, price_devide, full_price, half_price, food_type, attribute_id, description, is_available)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(CopyItemActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(CopyItemActivity.this, response.getMessage());
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
                Toast.makeText(CopyItemActivity.this, "Please select the restaurant image", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.etProductName.getText().toString().trim().length() <= 0) {
                Toast.makeText(CopyItemActivity.this, "Please enter the product name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (binder.etProductPrice.getText().toString().trim().length() <= 0) {
                Toast.makeText(CopyItemActivity.this, "Please enter the product price", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvDesc.getText().toString().trim().length() <= 0) {
                Toast.makeText(CopyItemActivity.this, "Please enter the product description", Toast.LENGTH_SHORT).show();
                return;
            }
            addFoodItem();
        }

    }
}
