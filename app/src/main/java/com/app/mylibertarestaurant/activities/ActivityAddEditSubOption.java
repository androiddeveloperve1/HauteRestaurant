package com.app.mylibertarestaurant.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.LinkedOptionAdapter;
import com.app.mylibertarestaurant.adapter.TagAdapter;
import com.app.mylibertarestaurant.databinding.ActivityAddEditSubOptionBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.newP.MainOptionModel;
import com.app.mylibertarestaurant.model.newP.SubOptionModel;
import com.app.mylibertarestaurant.model.newP.TagModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityAddEditSubOption extends AppCompatActivity {

    TagAdapter tagAdapter = null;
    LinkedOptionAdapter optionAdpter = null;
    private ActivityAddEditSubOptionBinding binder;
    private boolean isEdit = false;
    private SubOptionModel subOptionModel = new SubOptionModel();
    private ArrayList<TagModel> listTag = new ArrayList<>();
    private ArrayList<MainOptionModel> mainOptionModelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_sub_option);
        binder.setClick(new Click());
        initTagDummy();
        isEdit = getIntent().getBooleanExtra("edit", false);
        mainOptionModelsList = new Gson().fromJson(getIntent().getStringExtra("option"), new TypeToken<ArrayList<MainOptionModel>>() {
        }.getType());
        if (isEdit) {
            subOptionModel = new Gson().fromJson(getIntent().getStringExtra("data"), SubOptionModel.class);
        }
        binder.setModel(subOptionModel);
        binder.etMarkup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    binder.llRegularPrice.setVisibility(View.GONE);
                } else {
                    binder.llRegularPrice.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    void initTagDummy() {
        setInitDate();
        listTag.clear();
        TagModel model;

        model = new TagModel();
        model.setDisplay("Classic comfort food");
        model.setValue("Classic comfort food");
        listTag.add(model);

        model = new TagModel();
        model.setDisplay("Local ingredients");
        model.setValue("Local ingredients");
        listTag.add(model);


        model = new TagModel();
        model.setDisplay("Classic comfort food");
        model.setValue("Classic comfort food");
        listTag.add(model);

        model = new TagModel();
        model.setDisplay("Gourmet versions of classics");
        model.setValue("Gourmet versions of classics");
        listTag.add(model);

        model = new TagModel();
        model.setDisplay("CBD-infused foods/drinks");
        model.setValue("CBD-infused foods/drinks");
        listTag.add(model);

    }

    void setInitDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        binder.tvDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);

    }

    public class Click {
        public void onBack(View v) {
            finish();
        }

        public void onSave(View v) {
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void onLinkedOptionClicked(View v) {
            PopupWindow mPopupWindow;
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.tag_popup_view, null);
            mPopupWindow = new PopupWindow(customView, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindow.setElevation(5.0f);
            }
            RecyclerView rv = customView.findViewById(R.id.rv_tag);
            rv.setLayoutManager(new LinearLayoutManager(ActivityAddEditSubOption.this));
            optionAdpter = new LinkedOptionAdapter(new RecycleItemClickListener<MainOptionModel>() {
                @Override
                public void onItemClicked(int position, MainOptionModel data) {
                    data.setHasSelect1(!data.isHasSelect1());
                    optionAdpter.notifyDataSetChanged();
                    binder.tvLinkedOption.setText("");
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mainOptionModelsList.size(); i++) {
                        if (mainOptionModelsList.get(i).isHasSelect1()) {
                            sb.append(mainOptionModelsList.get(i).getName()).append(",");
                        }
                    }
                    binder.tvLinkedOption.setText(sb.toString());
                }
            }, mainOptionModelsList);
            rv.setAdapter(optionAdpter);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
            mPopupWindow.showAsDropDown(v, Gravity.CENTER, 0, 0);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void onTagClick(View v) {
            PopupWindow mPopupWindow;
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.tag_popup_view, null);
            mPopupWindow = new PopupWindow(customView, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindow.setElevation(5.0f);
            }
            RecyclerView rv = customView.findViewById(R.id.rv_tag);
            rv.setLayoutManager(new LinearLayoutManager(ActivityAddEditSubOption.this));

            tagAdapter = new TagAdapter(new RecycleItemClickListener<TagModel>() {
                @Override
                public void onItemClicked(int position, TagModel data) {
                    data.setHasSelect1(!data.isHasSelect1());
                    tagAdapter.notifyDataSetChanged();
                    binder.tvTag.setText("");
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < listTag.size(); i++) {
                        if (listTag.get(i).isHasSelect1()) {
                            sb.append(listTag.get(i).getDisplay()).append(",");
                        }
                    }
                    binder.tvTag.setText(sb.toString());
                }
            }, listTag);
            rv.setAdapter(tagAdapter);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
            mPopupWindow.showAsDropDown(v, Gravity.CENTER, 0, 0);

        }

        public void onMarkupClick(View v) {
            binder.etMarkup.performClick();
        }

        public void onCalender(View v) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dp = new DatePickerDialog(ActivityAddEditSubOption.this, (view, year, monthOfYear, dayOfMonth) -> {
                binder.tvDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }, mYear, mMonth, mDay);
            dp.show();
            dp.getDatePicker().setMinDate(new Date().getTime());

        }
    }
}
