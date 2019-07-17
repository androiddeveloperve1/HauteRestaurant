package com.app.mylibertarestaurant.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.app.mylibertarestaurant.itnerfaces.ImageOrGalarySelector;
import com.app.mylibertarestaurant.permission.PermissionConstants;
import com.app.mylibertarestaurant.permission.PermissionHandlerListener;
import com.app.mylibertarestaurant.permission.PermissionUtils;
import com.app.mylibertarestaurant.utils.ImageCaptureDialog;
import com.mindorks.paracamera.Camera;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Create By Rahul Mangal
 * Project SignupLibrary Screen
 */

public abstract class ImageUploadingActivity extends AppCompatActivity {
    private ImageCaptureDialog mImageCaptureDialog;
    private boolean isGalary = true;
    private Camera camera;
    private ImageOrGalarySelector listener = new ImageOrGalarySelector() {
        @Override
        public void imageSelect() {
            isGalary = false;
            mImageCaptureDialog.dismissDoalog();
            handleImagesPick();
        }

        @Override
        public void gallerySelect() {
            isGalary = true;
            mImageCaptureDialog.dismissDoalog();
            handleImagesPick();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageCaptureDialog = new ImageCaptureDialog(this, listener);

    }

    void handleImagesPick() {
        if (isGalary) {
            try {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, PermissionConstants.GALLERY_REQUEST_CODE);
            } catch (Exception e) {
                Toast.makeText(this, "No Gallery Found", Toast.LENGTH_SHORT).show();
            }
        } else {
            camera = new Camera.Builder()
                    .resetToCorrectOrientation(true)
                    .setTakePhotoRequestCode(PermissionConstants.CAMERA_REQUEST_CODE)
                    .setDirectory("pics")
                    .setName("capture_image")
                    .setImageFormat(Camera.IMAGE_JPEG)
                    .setCompression(75)
                    .setImageHeight(1000)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                    .build(this);
            try {
                camera.takePicture();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    protected void startCapture() {
        PermissionUtils.getInstance().checkAllPermission(ImageUploadingActivity.this, PermissionConstants.permissionArrayForImageCapture, new PermissionHandlerListener() {
            @Override
            public void onGrant() {
                mImageCaptureDialog.showDoalog();
            }

            @Override
            public void onReject(ArrayList<String> remainsPermissonList) {
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onRequestPermissionNow(String[] arr, int req) {
                requestPermissions(PermissionConstants.permissionArrayForImageCapture, PermissionUtils.RequestCode);
            }

            @Override
            public void onRationalPermission(ArrayList<String> rationalPermissonList) {
                PermissionUtils.firePerimisionActivity(ImageUploadingActivity.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionConstants.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            onImageCaptured(camera.getCameraBitmap());
        } else if (requestCode == PermissionConstants.GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            try {
                Bitmap mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                if (mBitmap != null) {
                    onImageCaptured(mBitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this.getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.getInstance().hendlePermission(ImageUploadingActivity.this, requestCode, permissions, grantResults);
    }

    protected abstract void onImageCaptured(Bitmap mBitmap);


}
