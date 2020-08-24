package com.app.mylibertarestaurant.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
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
import com.app.mylibertarestaurant.utils.AppUtils;
import com.app.mylibertarestaurant.utils.MultipartUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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



    private class DownloadTask extends AsyncTask<URL, Void, Bitmap> {
        ProgressDialog mProgressDialog;
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(EditProfileActivity.this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Image Loading...");
            mProgressDialog.show();
        }

        protected Bitmap doInBackground(URL... urls) {
            URL url = urls[0];
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                return BitmapFactory.decodeStream(bufferedInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // When all async task done
        protected void onPostExecute(Bitmap result) {
            // Hide the progress dialog
            Log.e("@@@@@@@@Image Loaded", "" + result);
            mProgressDialog.dismiss();
            if (result != null) {
                profilePic = result;
                binder.ivProfile.setImageBitmap(profilePic);
            } else {
                profilePic = result;
                Toast.makeText(EditProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

/*    private Target getTarget(final String url) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                profilePic = bitmap;
                binder.ivProfile.setImageBitmap(profilePic);
                Log.e("@@@@@@", "Image loded");
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e("@@@@@@", "Image failed");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.e("@@@@@@", "prepare Image loded");

            }
        };
        return target;
    }*/

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
        RequestBody deliveryTime = RequestBody.create(MediaType.parse("text/plain"), binder.tvDeliveryTime.getText().toString().trim());
        RequestBody deliveryFee = RequestBody.create(MediaType.parse("text/plain"), binder.tvDeliveryFee.getText().toString().trim());
        RequestBody open_time = RequestBody.create(MediaType.parse("text/plain"), AppUtils.get24HoursTimeFormat(binder.tvOpenTime.getText().toString().trim()));
        RequestBody close_time = RequestBody.create(MediaType.parse("text/plain"), AppUtils.get24HoursTimeFormat(binder.tvCloseTime.getText().toString().trim()));

        Log.e("@@@@@@@@", "" + binder.tvOpenTime.getText().toString().trim() + "--" + binder.tvCloseTime.getText().toString().trim());
        final Dialog progressDialog = ResponseDialog.showProgressDialog(EditProfileActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(EditProfileActivity.this);
        apiInterface.updateProfile(image, name, address, pincode, deliverykm, restaurant_id, latitude, longitude, deliveryTime, deliveryFee, open_time, close_time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(EditProfileActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(EditProfileActivity.this, response.getMessage());
                        }

                    }
                });
    }

    void showInView() {
        binder.tvReatsurantName.setText(restaurantDetailModel.getRestaurants().getName());
        binder.tvReatsurantAddress.setText(restaurantDetailModel.getRestaurants().getAddress());
        binder.tvDeliveryFee.setText(restaurantDetailModel.getRestaurants().getDeliveryfees());
        binder.tvDeliveryTime.setText(restaurantDetailModel.getRestaurants().getMaxdeliverytime());
        binder.tvOpenTime.setText(AppUtils.get12HoursTimeFormat(restaurantDetailModel.getRestaurants().getOpen_time().split(" ")[0]));
        binder.tvCloseTime.setText(AppUtils.get12HoursTimeFormat(restaurantDetailModel.getRestaurants().getClose_time().split(" ")[0]));
        mlaLatLng = new LatLng(restaurantDetailModel.getRestaurants().getLocation().getCoordinates().get(0), restaurantDetailModel.getRestaurants().getLocation().getCoordinates().get(1));
        binder.tvZip.setText(restaurantDetailModel.getRestaurants().getPincode());
        try {
            range = Integer.parseInt(restaurantDetailModel.getRestaurants().getDeliverykm());
        } catch (Exception e) {
            range = 0;
        }
        binder.tvRange.setText("" + range);
        Picasso.with(EditProfileActivity.this).load(restaurantDetailModel.getRestaurants().getImages().get(0)).resize(200, 200).onlyScaleDown().placeholder(R.drawable.placeholder_squre).into(binder.ivProfile);
        try {
            new DownloadTask().execute(new URL(restaurantDetailModel.getRestaurants().getImages().get(0)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public class Myclick {

        public void onClose(View v) {
            finish();
        }

        public void selectOpenTime(View v) {
            String arr[] = AppUtils.get24HoursTimeFormat(binder.tvOpenTime.getText().toString()).split(":");
            new TimePickerDialog(EditProfileActivity.this, (view, hourOfDay, minutes) -> {
                String hours = "00";
                String mins = "00";
                if (hourOfDay < 10) {
                    hours = "0" + hourOfDay;
                } else {
                    hours = "" + hourOfDay;
                }
                if (minutes < 10) {
                    mins = "0" + minutes;
                } else {
                    mins = "" + minutes;
                }

                binder.tvOpenTime.setText(AppUtils.get12HoursTimeFormat(hours + ":" + mins));
                binder.tvCloseTime.setText("00:00 AM");
            }, Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), false).show();
        }

        public void selectCloseTime(View v) {
            String arr[] = AppUtils.get24HoursTimeFormat(binder.tvCloseTime.getText().toString()).split(":");
            //String arr[] = binder.tvCloseTime.getText().toString().split(":");
            new TimePickerDialog(EditProfileActivity.this, (view, hourOfDay, minutes) -> {
                //String arr2[] = binder.tvOpenTime.getText().toString().split(":");
                String arr2[] = AppUtils.get24HoursTimeFormat(binder.tvOpenTime.getText().toString()).split(":");
                if (Integer.parseInt(arr2[0]) > hourOfDay) {
                    Toast.makeText(EditProfileActivity.this, "Close time can not be later from Open time", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    if (Integer.parseInt(arr2[0]) == hourOfDay) {
                        if (Integer.parseInt(arr2[1]) > minutes) {
                            Toast.makeText(EditProfileActivity.this, "Close time can not be later from Open time", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                String hours = "00";
                String mins = "00";
                if (hourOfDay < 10) {
                    hours = "0" + hourOfDay;
                } else {
                    hours = "" + hourOfDay;
                }
                if (minutes < 10) {
                    mins = "0" + minutes;
                } else {
                    mins = "" + minutes;
                }


                binder.tvCloseTime.setText(AppUtils.get12HoursTimeFormat(hours + ":" + mins));

            }, Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), false).show();
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
            if (binder.tvZip.getText().toString().trim().length() < 5) {
                Toast.makeText(EditProfileActivity.this, "Please enter valid  zip code", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvDeliveryTime.getText().toString().trim().length() <= 0) {
                Toast.makeText(EditProfileActivity.this, "Please enter max delivery time", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvDeliveryFee.getText().toString().trim().length() <= 0) {
                Toast.makeText(EditProfileActivity.this, "Please enter delivery fee", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvRange.getText().toString().trim().equals("0")) {
                Toast.makeText(EditProfileActivity.this, "Please select the delivery range", Toast.LENGTH_SHORT).show();
                return;
            }

            updateRestaurant();
        }

        public void rangeIncrement(View v) {
            if (range < 15) {
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


}
