package com.app.mylibertarestaurant.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.OptionDetailAdapter;
import com.app.mylibertarestaurant.databinding.ActivityOptionDetailBinding;
import com.app.mylibertarestaurant.model.items.OrderItemModel;
import com.app.mylibertarestaurant.utils.AppUtils;
import com.google.gson.Gson;

public class OptionSelectedDetailActivity extends AppCompatActivity {
    private OrderItemModel data;
    private ActivityOptionDetailBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_option_detail);
        binder.setClick(new ClickHandler());
        data = new Gson().fromJson(getIntent().getStringExtra("data"), OrderItemModel.class);
        binder.tvName.setText(data.getItem_name());
        binder.tvDescription.setText(data.getDescription());
        binder.tvPrice.setText("$"+AppUtils.getDecimalFormat(data.getPrice()));
        initData();


    }

    void initData() {
        for (int i = 0; i < data.getOptions().size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_option, null, false);
            view.setId(i);
            TextView title = view.findViewById(R.id.tv_category_name);
            RecyclerView recylerView1 = view.findViewById(R.id.rv_categories_items);
            recylerView1.setLayoutManager(new LinearLayoutManager(this));
            OptionDetailAdapter adapter1 = new OptionDetailAdapter(this, data.getOptions().get(i));
            recylerView1.setAdapter(adapter1);
            title.setText(AppUtils.convertToTitleCaseIteratingChars(data.getOptions().get(i).getCustomerPrompt()));
            binder.items.addView(view);
        }
    }

    public class ClickHandler {
        public void onBack(View v) {
            finish();
        }
    }

}
