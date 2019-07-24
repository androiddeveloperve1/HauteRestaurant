package com.app.mylibertarestaurant.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityEditProfileBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.model.items.RestaurantDetail;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.permission.PermissionConstants;
import com.app.mylibertarestaurant.permission.PermissionHandlerListener;
import com.app.mylibertarestaurant.permission.PermissionUtils;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.MultipartUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditProfileActivity extends ImageUploadingActivity {

    public static final int LocationTag = 1000;
    ActivityEditProfileBinding binder;
    @Inject
    APIInterface apiInterface;
    RestaurantDetailModel restaurantDetailModel;
    private LatLng mlaLatLng;
    private int range;
    private Bitmap profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        binder.setClick(new Myclick());
        restaurantDetailModel = MySharedPreference.getInstance(EditProfileActivity.this).getUser();
        showInView();
    }

    @Override
    protected void onImageCaptured(Bitmap mBitmap) {
        profilePic = mBitmap;
        binder.ivProfile.setImageBitmap(profilePic);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LocationTag) {
            if (data != null) {
                mlaLatLng = new LatLng(data.getDoubleExtra("lat", 0.0), data.getDoubleExtra("long", 0.0));
                binder.tvReatsurantAddress.setText(data.getStringExtra("address"));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.getInstance().hendlePermission(EditProfileActivity.this, requestCode, permissions, grantResults);
    }


    private void updateRestaurant() {

        MultipartBody.Part image = MultipartUtils.createFile(EditProfileActivity.this, profilePic, "retaurant_image", "retaurant_image.jpg");
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), binder.tvReatsurantName.getText().toString().trim());

        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), binder.tvReatsurantAddress.getText().toString().trim());

        RequestBody pincode = RequestBody.create(MediaType.parse("text/plain"), binder.tvZip.getText().toString().trim());

        RequestBody deliverykm = RequestBody.create(MediaType.parse("text/plain"), binder.tvRange.getText().toString());

        RequestBody restaurant_id = RequestBody.create(MediaType.parse("text/plain"), MySharedPreference.getInstance(EditProfileActivity.this).getUser().get_id());

        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), "" + mlaLatLng.latitude);

        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), "" + mlaLatLng.longitude);

        final Dialog progressDialog = ResponseDialog.showProgressDialog(EditProfileActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(EditProfileActivity.this);
        apiInterface.updateProfile(image, name, address, pincode, deliverykm, restaurant_id, latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<RestaurantDetail>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(EditProfileActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<RestaurantDetail> response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            restaurantDetailModel.setRestaurants(response.getData());
                            MySharedPreference.getInstance(EditProfileActivity.this).setUser(restaurantDetailModel);
                        } else {
                            ResponseDialog.showErrorDialog(EditProfileActivity.this, response.getMessage());
                        }

                    }
                });
    }

    void showInView() {
        binder.tvReatsurantName.setText(restaurantDetailModel.getRestaurants().getName());
        binder.tvReatsurantAddress.setText(restaurantDetailModel.getRestaurants().getAddress());
        mlaLatLng=new LatLng(restaurantDetailModel.getRestaurants().getLocation().getCoordinates().get(0),restaurantDetailModel.getRestaurants().getLocation().getCoordinates().get(1));
        binder.tvZip.setText(restaurantDetailModel.getRestaurants().getPincode());
        try {
            range = Integer.parseInt(restaurantDetailModel.getRestaurants().getDeliverykm());
        } catch (Exception e) {
            range = 0;
        }
        binder.tvRange.setText("" + range);
        new DownloadImage().execute(restaurantDetailModel.getRestaurants().getImages().get(0));

    }

    public class Myclick {

        public void onClose(View v) {
            finish();
        }

        public void onSave(View v) {
            if (profilePic == null) {
                Toast.makeText(EditProfileActivity.this, "Please capture the restaurant image", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvReatsurantName.getText().toString().trim().length() <= 0) {
                Toast.makeText(EditProfileActivity.this, "Please enter the restaurant name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvReatsurantAddress.getText().toString().trim().length() <= 0) {
                Toast.makeText(EditProfileActivity.this, "Please select the restaurant address", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvZip.getText().toString().trim().length() <= 0) {
                Toast.makeText(EditProfileActivity.this, "Please enter the zip code", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvZip.getText().toString().trim().length() < 6) {
                Toast.makeText(EditProfileActivity.this, "Please enter valid  zip code", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvRange.getText().toString().trim().equals("0")) {
                Toast.makeText(EditProfileActivity.this, "Please select the delivery range", Toast.LENGTH_SHORT).show();
                return;
            }

            updateRestaurant();
        }

        public void rangeIncrement(View v) {
            if (range < 10) {
                range++;
            }
            binder.tvRange.setText("" + range);
        }

        public void rangeDecrement(View v) {

            if (range > 0) {
                range--;
            }
            binder.tvRange.setText("" + range);

        }

        public void selectAdress(View v) {
            PermissionUtils.getInstance().checkAllPermission(EditProfileActivity.this, PermissionConstants.permissionArrayForLocation, new PermissionHandlerListener() {
                @Override
                public void onGrant() {
                    startActivityForResult(new Intent(EditProfileActivity.this, LocationActivity.class), LocationTag);
                }

                @Override
                public void onReject(ArrayList<String> remainsPermissonList) {
                }

                @Override
                public void onRationalPermission(ArrayList<String> rationalPermissonList) {
                    PermissionUtils.firePerimisionActivity(EditProfileActivity.this);
                }
            });

        }

        public void captureImage(View v) {
            startCapture();
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
                profilePic = result;
                binder.ivProfile.setImageBitmap(profilePic);
            }
        }
    }
}
