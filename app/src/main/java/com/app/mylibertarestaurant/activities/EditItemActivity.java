package com.app.mylibertarestaurant.activities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.AttributeAdapter;
import com.app.mylibertarestaurant.adapter.CategoryAdapter;
import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.databinding.ActivityEditItemBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.AttributeModel;
import com.app.mylibertarestaurant.model.CategoryModel;
import com.app.mylibertarestaurant.model.InventoryModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.MultipartUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditItemActivity extends ImageUploadingActivity {
    @Inject
    APIInterface apiInterface;
    private ActivityEditItemBinding binder;
    private InventoryModel dataToBeEdit;
    private String attributeId;
    private Bitmap restaurantImage;
    private RestaurantDetailModel restaurantDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_edit_item);
        binder.setClick(new Click());
        attributeId = getIntent().getStringExtra("attribute_id");
        dataToBeEdit = new Gson().fromJson(getIntent().getStringExtra("data"), InventoryModel.class);
        restaurantDetailModel = MySharedPreference.getInstance(EditItemActivity.this).getUser();
        copyItemData();
        getAttribute();
        getCategory();
    }

    @Override
    protected void onImageCaptured(Bitmap mBitmap) {
        restaurantImage = mBitmap;
        binder.imgPic.setImageBitmap(restaurantImage);
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
                        if (response.getStatus().equals("200")) {
                            binder.spnrAttribute.setAdapter(new AttributeAdapter(response.getData()));


                            for (int i = 0; i < response.getData().size(); i++) {
                                if (attributeId.equals(response.getData().get(i).get_id())) {
                                    binder.spnrAttribute.setSelection(i);
                                    break;
                                }
                            }


                        } else {
                            ResponseDialog.showErrorDialog(EditItemActivity.this, response.getMessage());
                        }

                    }
                });
    }

    private void getCategory() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(EditItemActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(EditItemActivity.this);
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
                        ResponseDialog.showErrorDialog(EditItemActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<CategoryModel>> response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            binder.spnrCategory.setAdapter(new CategoryAdapter(response.getData()));
                            for (int i = 0; i < response.getData().size(); i++) {
                                if (dataToBeEdit.getCategory_id().get_id().equals(response.getData().get(i).get_id())) {
                                    binder.spnrCategory.setSelection(i);
                                    break;
                                }
                            }
                        } else {
                            ResponseDialog.showErrorDialog(EditItemActivity.this, response.getMessage());
                        }

                    }
                });
    }


    void copyItemData() {

        binder.etProductName.setText(dataToBeEdit.getItem_id().getName());
        binder.etProductPrice.setText(dataToBeEdit.getFull_price());
        binder.tvDesc.setText(dataToBeEdit.getDescription());
        new DownloadImage().execute(dataToBeEdit.getImage());
        if (dataToBeEdit.getIs_available().equals("0")) {
            binder.tvProductAvailSwitch.setTag(getResources().getString(R.string.un_available));
            binder.tvProductAvailSwitch.setBackgroundResource(R.drawable.ic_toggle_unavailable);
        } else {
            binder.tvProductAvailSwitch.setTag(getResources().getString(R.string.available));
            binder.tvProductAvailSwitch.setBackgroundResource(R.drawable.ic_toggle_available);
        }
        if (dataToBeEdit.getFood_type().equals("non-veg")) {
            binder.imgVegNonvegSwitch.setTag(getResources().getString(R.string.non_veg));
            binder.imgVegNonvegSwitch.setBackgroundResource(R.drawable.ic_toggle_on_veg);
        } else {
            binder.imgVegNonvegSwitch.setTag(getResources().getString(R.string.veg));
            binder.imgVegNonvegSwitch.setBackgroundResource(R.drawable.ic_toggle_veg);
        }
    }

    private void editFoodItem() {

        MultipartBody.Part image = MultipartUtils.createFile(EditItemActivity.this, restaurantImage, "food_image", "food_image.jpg");
        RequestBody item_id = RequestBody.create(MediaType.parse("text/plain"), binder.etProductName.getText().toString().trim());

        RequestBody restaurent_id = RequestBody.create(MediaType.parse("text/plain"), restaurantDetailModel.get_id());

        RequestBody category_id = RequestBody.create(MediaType.parse("text/plain"), dataToBeEdit.getCategory_id().get_id());

        RequestBody price_devide = RequestBody.create(MediaType.parse("text/plain"), "0");

        RequestBody full_price = RequestBody.create(MediaType.parse("text/plain"), binder.etProductPrice.getText().toString().trim());

        RequestBody half_price = RequestBody.create(MediaType.parse("text/plain"), "0");


        RequestBody attribute_id = RequestBody.create(MediaType.parse("text/plain"), attributeId);
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
        RequestBody food_itemId = RequestBody.create(MediaType.parse("text/plain"), dataToBeEdit.getFoodItemId());


        final Dialog progressDialog = ResponseDialog.showProgressDialog(EditItemActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(EditItemActivity.this);
        apiInterface.editFoodItem(image, item_id, restaurent_id, category_id, price_devide, full_price, half_price, food_type, attribute_id, description, is_available, food_itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(EditItemActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            finish();
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
            if (restaurantImage == null) {
                Toast.makeText(EditItemActivity.this, "Please select the restaurant image", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.etProductName.getText().toString().trim().length() <= 0) {
                Toast.makeText(EditItemActivity.this, "Please enter the product name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (binder.etProductPrice.getText().toString().trim().length() <= 0) {
                Toast.makeText(EditItemActivity.this, "Please enter the product price", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvDesc.getText().toString().trim().length() <= 0) {
                Toast.makeText(EditItemActivity.this, "Please enter the product description", Toast.LENGTH_SHORT).show();
                return;
            }
            editFoodItem();
        }


    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private String TAG = "___________";

        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            } catch (Exception e) {
                Log.e(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                restaurantImage = result;
                binder.imgPic.setImageBitmap(restaurantImage);
            }
        }
    }
}
