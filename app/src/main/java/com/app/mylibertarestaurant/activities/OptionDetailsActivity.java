package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityOptionDetailsBinding;
import com.app.mylibertarestaurant.model.newP.MainOptionModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryItemModel;
import com.app.mylibertarestaurant.utils.AppUtils;
import com.google.gson.Gson;

public class OptionDetailsActivity extends AppCompatActivity {
    private ActivityOptionDetailsBinding binder;
    private RestaurantCategoryItemModel restaurantCategoryItemModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_option_details);
        binder.setClick(new MyClick());
        restaurantCategoryItemModel = new Gson().fromJson(getIntent().getStringExtra("data"), RestaurantCategoryItemModel.class);
        initOptionAndSubOption();
    }

    void initOptionAndSubOption() {
        for (int i = 0; i < restaurantCategoryItemModel.getOptionsResult().size(); i++) {
            View mainOption = getLayoutInflater().inflate(R.layout.item_option_green, null);
            ConstraintLayout.LayoutParams param = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            param.setMargins(0, getResources().getDimensionPixelOffset(R.dimen._4_px), 0, 0);
            mainOption.setLayoutParams(param);
            mainOption.setId(i);
            TextView tv_text_option = mainOption.findViewById(R.id.tv_text_option);
            TextView tv_min = mainOption.findViewById(R.id.tv_min);
            TextView tv_max = mainOption.findViewById(R.id.tv_max);
            TextView tv_text_edit = mainOption.findViewById(R.id.tv_text_edit);
            TextView tv_add_sub_option = mainOption.findViewById(R.id.tv_add_sub_option);
            MainOptionModel mainOptionModel = restaurantCategoryItemModel.getOptionsResult().get(i);
            tv_text_option.setText(mainOptionModel.getName());
            tv_min.setText(mainOptionModel.getMinSelection());
            tv_max.setText(mainOptionModel.getMaxSelection());
            tv_text_edit.setOnClickListener((v) -> {
                Intent mIntent = new Intent(OptionDetailsActivity.this, AddEditOptionActivity.class);
                mIntent.putExtra("data", new Gson().toJson(mainOptionModel));
                mIntent.putExtra("edit", true);
                startActivity(mIntent);

            });
            tv_add_sub_option.setOnClickListener((v) -> {
                Intent mIntent = new Intent(OptionDetailsActivity.this, ActivityAddEditSubOption.class);
                mIntent.putExtra("edit", false);
                startActivity(mIntent);
            });
            LinearLayout ll_sub_option = mainOption.findViewById(R.id.ll_sub_option);
            for (int j = 0; j < mainOptionModel.getSubOptionsResult().size(); j++) {
                final int k = j;
                View subOption = getLayoutInflater().inflate(R.layout.item_suboption_with_arrow, null);


                subOption.setId(j);


                subOption.setOnClickListener((v) -> {
                    Intent mIntent = new Intent(OptionDetailsActivity.this, ActivityAddEditSubOption.class);
                    mIntent.putExtra("data", new Gson().toJson(mainOptionModel.getSubOptionsResult().get(k)));
                    mIntent.putExtra("edit", true);
                    startActivity(mIntent);
                });


                TextView tv_name = subOption.findViewById(R.id.tv_name);
                tv_name.setText(mainOptionModel.getSubOptionsResult().get(j).getName() + " ($" + AppUtils.getDecimalFormat(mainOptionModel.getSubOptionsResult().get(j).getBestPrice()) + ")");
                ll_sub_option.addView(subOption);
            }
            binder.llManu.addView(mainOption);

        }


    }

    public class MyClick {
        public void onBack(View v) {
            finish();
        }


        public void onAdd(View v) {
        }

    }
}
