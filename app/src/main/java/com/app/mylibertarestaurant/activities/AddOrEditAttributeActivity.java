package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.MultipleAttributeAdapter;
import com.app.mylibertarestaurant.databinding.ActivityAddOrEditAttributeBinding;
import com.app.mylibertarestaurant.itnerfaces.AttributeAddDeleteListener;
import com.app.mylibertarestaurant.model.inventorynew.AttributeModelNew;
import com.app.mylibertarestaurant.model.items.OrderDetailsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class AddOrEditAttributeActivity extends AppCompatActivity implements AttributeAddDeleteListener {

    private ArrayList<AttributeModelNew> arayList;
    private ActivityAddOrEditAttributeBinding binder;
    private MultipleAttributeAdapter multipleAttributeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_add_or_edit_attribute);


        arayList = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<ArrayList<AttributeModelNew>>() {
        }.getType());

        binder.setClick(new MyClick());
        multipleAttributeAdapter = new MultipleAttributeAdapter(arayList, this);
        binder.rvItem.setLayoutManager(new LinearLayoutManager(this));
        binder.setAdapter(multipleAttributeAdapter);

    }

    @Override
    public void onAttributeAdd(int position) {

    }

    @Override
    public void onAttributeDelete(int position) {
        arayList.remove(position);
        multipleAttributeAdapter.notifyDataSetChanged();
    }


    public class MyClick {

        public void addNewAttribute(View v) {
            AttributeModelNew data = new AttributeModelNew();
            arayList.add(data);
            multipleAttributeAdapter.notifyDataSetChanged();
        }

        public void back(View v) {
            finish();
        }

        public void save(View v) {
            for (int i = 0; i < arayList.size(); i++) {
                if (arayList.get(i).getAttribute_name() == null || arayList.get(i).getAttribute_name().trim().length() <= 0) {
                    Toast.makeText(AddOrEditAttributeActivity.this, "Please fill the attribute or remove the attribute", Toast.LENGTH_LONG).show();
                    return;
                }
                if (arayList.get(i).getAttribute_price() == null || arayList.get(i).getAttribute_price().trim().length() <= 0) {
                    Toast.makeText(AddOrEditAttributeActivity.this, "Please fill the attribute price", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            Intent mIntent = new Intent();
            mIntent.putExtra("data", new Gson().toJson(arayList));
            setResult(Activity.RESULT_OK, mIntent);
            finish();

        }


    }
}
