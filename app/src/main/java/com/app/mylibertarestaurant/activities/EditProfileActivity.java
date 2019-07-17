package com.app.mylibertarestaurant.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityEditProfileBinding;
import com.app.mylibertarestaurant.permission.PermissionConstants;
import com.app.mylibertarestaurant.permission.PermissionHandlerListener;
import com.app.mylibertarestaurant.permission.PermissionUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class EditProfileActivity extends ImageUploadingActivity {

    public static final int LocationTag = 1000;
    ActivityEditProfileBinding binder;
    private LatLng mlaLatLng;
    private int range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        binder.setClick(new Myclick());
    }

    @Override
    protected void onImageCaptured(Bitmap mBitmap) {
        binder.ivProfile.setImageBitmap(mBitmap);
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

    public class Myclick {

        public void onClose(View v) {
            finish();
        }

        public void onSave(View v) {
            finish();

        }

        public void rangeIncrement(View v) {
            if (range < 10) {
                range++;
            }
            binder.tvRange.setText(""+range);
        }

        public void rangeDecrement(View v) {

            if (range > 0) {
                range--;
            }
            binder.tvRange.setText(""+range);

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
