package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityAddEditOptionBinding;
import com.app.mylibertarestaurant.model.newP.MainOptionModel;
import com.google.gson.Gson;

public class AddEditOptionActivity extends AppCompatActivity {
    private ActivityAddEditOptionBinding binder;
    private MainOptionModel mainOptionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_option);
        binder.setClick(new Click());
        mainOptionModel = new Gson().fromJson(getIntent().getStringExtra("data"), MainOptionModel.class);
        binder.setModel(mainOptionModel);
    }


    public class Click {
        public void onBack(View v) {
            finish();
        }

        public void onSave(View v) {

        }
    }
}
