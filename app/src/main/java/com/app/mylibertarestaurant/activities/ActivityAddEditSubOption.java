package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityAddEditSubOptionBinding;
import com.app.mylibertarestaurant.model.newP.SubOptionModel;
import com.google.gson.Gson;

public class ActivityAddEditSubOption extends AppCompatActivity {
    private ActivityAddEditSubOptionBinding binder;
    private boolean isEdit = false;
    private SubOptionModel subOptionModel = new SubOptionModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_sub_option);
        binder.setClick(new Click());

        isEdit = getIntent().getBooleanExtra("edit", false);

        if (isEdit) {
            subOptionModel = new Gson().fromJson(getIntent().getStringExtra("data"), SubOptionModel.class);
        }
        binder.setModel(subOptionModel);
    }

    public class Click {
        public void onBack(View v) {
            finish();
        }

        public void onSave(View v) {

        }
    }
}
