package com.app.mylibertarestaurant.activities;

import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityAddCategoryBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.items.RestaurantDetail;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.MultipartUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityAddEditCategory extends ImageUploadingActivity {

    @Inject
    APIInterface apiInterface;
    private ActivityAddCategoryBinding binder;
    private Bitmap menuItemImage;
    private RestaurantDetail restaurantDetail;
    private boolean isEdit = false;
    private RestaurantCategoryModel editableRestaurantCategoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_add_category);
        binder.setClick(new MyClick());
        restaurantDetail = MySharedPreference.getInstance(ActivityAddEditCategory.this).getUser().getRestaurants();
        isEdit = getIntent().getBooleanExtra("isEdit", false);

        if (isEdit) {
            binder.tvCatName.setText("Edit menu");
            editableRestaurantCategoryModel = new Gson().fromJson(getIntent().getStringExtra("data"), RestaurantCategoryModel.class);
            initEditableValue();
        } else {
            binder.tvCatName.setText("Add new menu");
        }
    }

    @Override
    protected void onImageCaptured(Bitmap mBitmap) {
        menuItemImage = mBitmap;
        showCaptureView();


    }

    private void saveCategory() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ActivityAddEditCategory.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ActivityAddEditCategory.this);
        MultipartBody.Part image = MultipartUtils.createFile(ActivityAddEditCategory.this, menuItemImage, "category_image", "food_image.jpg");
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
                        ResponseDialog.showErrorDialog(ActivityAddEditCategory.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            Toast.makeText(ActivityAddEditCategory.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(ActivityAddEditCategory.this, response.getMessage());
                        }

                    }
                });
    }

    private void updateCategory() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ActivityAddEditCategory.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ActivityAddEditCategory.this);
        MultipartBody.Part image = MultipartUtils.createFile(ActivityAddEditCategory.this, menuItemImage, "category_image", "food_image.jpg");
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), binder.etName.getText().toString().trim());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), binder.etDescription.getText().toString());
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), restaurantDetail.getAddress());
        RequestBody restaurent_id = RequestBody.create(MediaType.parse("text/plain"), restaurantDetail.get_id());
        RequestBody isActive = RequestBody.create(MediaType.parse("text/plain"), "true");
        RequestBody isImageRemove = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody categoryId = RequestBody.create(MediaType.parse("text/plain"), editableRestaurantCategoryModel.get_id());
        RequestBody isUpdate = RequestBody.create(MediaType.parse("text/plain"), "1");
        apiInterface.updateCategory(image, name, description, location, restaurent_id,isActive,isImageRemove,categoryId,isUpdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ActivityAddEditCategory.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        if (response.getStatus().equals("200")) {
                            Toast.makeText(ActivityAddEditCategory.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(ActivityAddEditCategory.this, response.getMessage());
                        }

                    }
                });
    }

    void initEditableValue() {
        new DownloadImage().execute(editableRestaurantCategoryModel.getImage());
        binder.etDescription.setText(editableRestaurantCategoryModel.getDescription());
        binder.etName.setText(editableRestaurantCategoryModel.getName());
    }

    void showCaptureView() {

        binder.rlMenuImage.setVisibility(View.VISIBLE);
        binder.rlUpload.setVisibility(View.GONE);
        binder.imgMenu.setImageBitmap(menuItemImage);
    }

    void showDeleteView() {
        menuItemImage = null;
        binder.rlMenuImage.setVisibility(View.GONE);
        binder.rlUpload.setVisibility(View.VISIBLE);
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
                        if (isEdit) {
                            updateCategory();
                        } else
                            saveCategory();
                    } else {
                        Toast.makeText(ActivityAddEditCategory.this, "Please select the image for menu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityAddEditCategory.this, "Please enter the description of menu", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ActivityAddEditCategory.this, "Please enter the title of menu", Toast.LENGTH_SHORT).show();
            }
        }

        public void delImage(View v) {
            showDeleteView();
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
            menuItemImage = result;
            if (menuItemImage != null) {
                showCaptureView();
            } else {
                showDeleteView();
            }

        }
    }
}