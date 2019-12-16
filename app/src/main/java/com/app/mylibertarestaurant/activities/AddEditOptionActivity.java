package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityAddEditOptionBinding;

public class AddEditOptionActivity extends AppCompatActivity {
    private ActivityAddEditOptionBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_option);
        binder.setClick(new Click());
    }


    public class Click {
        public void onBack(View v) {
            finish();
        }

        public void onSave(View v) {

        }
    }
}
