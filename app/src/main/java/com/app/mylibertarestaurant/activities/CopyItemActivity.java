package com.app.mylibertarestaurant.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityCopyItemBinding;

public class CopyItemActivity extends ImageUploadingActivity {
    ActivityCopyItemBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_copy_item);
        binder.setClick(new Click());
    }

    @Override
    protected void onImageCaptured(Bitmap mBitmap) {
        binder.imgPic.setImageBitmap(mBitmap);
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
