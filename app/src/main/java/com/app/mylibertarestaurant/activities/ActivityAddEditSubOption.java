package com.app.mylibertarestaurant.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.LinkedOptionAdapter;
import com.app.mylibertarestaurant.adapter.TagAdapter;
import com.app.mylibertarestaurant.databinding.ActivityAddEditSubOptionBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.newP.LinkedOptionRequestModel;
import com.app.mylibertarestaurant.model.newP.MainOptionModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryItemModel;
import com.app.mylibertarestaurant.model.newP.SubOptionModel;
import com.app.mylibertarestaurant.model.newP.TagModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.AppUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityAddEditSubOption extends AppCompatActivity {
    @Inject
    APIInterface apiInterface;
    TagAdapter tagAdapter = null;
    LinkedOptionAdapter optionAdpter = null;
    private ActivityAddEditSubOptionBinding binder;
    private boolean isEdit = false;
    private SubOptionModel subOptionModel = null;
    private ArrayList<TagModel> listTag = new ArrayList<>();
    private ArrayList<MainOptionModel> mainOptionModelsList;
    private RestaurantCategoryItemModel restaurantCategoryItemModel;
    private String optionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_sub_option);
        binder.setClick(new Click());
        initTagDummy();
        setInitDate();
        isEdit = getIntent().getBooleanExtra("edit", false);
        mainOptionModelsList = new Gson().fromJson(getIntent().getStringExtra("mainOptionList"), new TypeToken<ArrayList<MainOptionModel>>() {
        }.getType());


        restaurantCategoryItemModel = new Gson().fromJson(getIntent().getStringExtra("restaurantCategoryItemModel"), RestaurantCategoryItemModel.class);
        optionId = getIntent().getStringExtra("optionId");
        if (isEdit) {
            binder.tvCatName.setText("Edit sub-option");
            binder.tvDel.setVisibility(View.VISIBLE);
            subOptionModel = new Gson().fromJson(getIntent().getStringExtra("subOptionModel"), SubOptionModel.class);
            binder.etName.setText(subOptionModel.getName());
            binder.etPrice.setText("" + subOptionModel.getBestPrice());
            binder.etRegularPrice.setText("" + subOptionModel.getPrice());
            binder.tvDate.setText("" + AppUtils.getDate(subOptionModel.getDisabledUntilDate()));
            if (subOptionModel.getMarkupStructure().equalsIgnoreCase("y")) {
                binder.etMarkup.setSelection(1);
            }
            setSelectionOnOptionIftheyAdded();
        }
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
        binder.tvDate.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);

    }

    void del() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddEditSubOption.this);
        builder.setTitle("MyLiberta");
        builder.setMessage("Are you sure want to delete this Sub-Option?");
        builder.setPositiveButton("Del", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                final Dialog progressDialog = ResponseDialog.showProgressDialog(ActivityAddEditSubOption.this);
                ((MyApplication) getApplication()).getConfiguration().inject(ActivityAddEditSubOption.this);
                apiInterface.delSubOption(subOptionModel.get_id())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ApiResponseModel>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                progressDialog.dismiss();
                                ResponseDialog.showErrorDialog(ActivityAddEditSubOption.this, throwable.getLocalizedMessage());
                            }

                            @Override
                            public void onNext(ApiResponseModel response) {
                                progressDialog.dismiss();
                                Log.e("@@@@@@@@", new Gson().toJson(response));
                                if (response.getStatus().equals("200")) {
                                    Toast.makeText(ActivityAddEditSubOption.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(ActivityAddEditSubOption.this, ItemDescriptionActivity.class));
                                    finish();
                                } else {
                                    ResponseDialog.showErrorDialog(ActivityAddEditSubOption.this, response.getMessage());
                                }

                            }
                        });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    void setSelectionOnOptionIftheyAdded() {
        try {
            for (int i = 0; i < subOptionModel.getOptionLinked().size(); i++) {
                LinkedOptionRequestModel subModel = subOptionModel.getOptionLinked().get(i);
                for (int j = 0; j < mainOptionModelsList.size(); j++) {
                    if (subModel.getId().equalsIgnoreCase(mainOptionModelsList.get(j).get_id())) {
                        mainOptionModelsList.get(j).setHasSelect1(true);
                    }
                }
            }
        } catch (Exception e) {
        }


    }

    private void addUpdateOption() {

        HashMap<String, Object> param = new HashMap<>();
        param.put("name", binder.etName.getText().toString());
        param.put("bestPrice", binder.etPrice.getText().toString());
        param.put("price", binder.etRegularPrice.getText().toString());
        param.put("markupStructure", (binder.etMarkup.getSelectedItemPosition() == 0) ? "N" : "Y");
        param.put("tags", "");
        param.put("disabledUntilDate", binder.tvDate.getText().toString());
        param.put("option_id", optionId);
        param.put("restaurent_id", MySharedPreference.getInstance(this).getUser().getRestaurants().get_id());
        param.put("item_id", restaurantCategoryItemModel.get_id());
        param.put("category_id", restaurantCategoryItemModel.getCategory_id());
        param.put("location", restaurantCategoryItemModel.getLocation());
        param.put("optionLinked", getLinkedOptionString());
        if (isEdit) {
            param.put("sub_option_id", subOptionModel.get_id());
            param.put("is_update", "1");
        }
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ActivityAddEditSubOption.this);
        ((MyApplication) ActivityAddEditSubOption.this.getApplication()).getConfiguration().inject(ActivityAddEditSubOption.this);
        apiInterface.addUpdateSubOption(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ActivityAddEditSubOption.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@", new Gson().toJson(response));
                        if (response.getStatus().equals("200")) {
                            Toast.makeText(ActivityAddEditSubOption.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivityAddEditSubOption.this, ItemDescriptionActivity.class));
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(ActivityAddEditSubOption.this, response.getMessage());
                        }
                    }
                });

    }

    JSONArray getLinkedOptionString() {
        ArrayList<LinkedOptionRequestModel> list = new ArrayList<>();
        for (int i = 0; i < mainOptionModelsList.size(); i++) {
            if (mainOptionModelsList.get(i).isHasSelect1()) {
                LinkedOptionRequestModel model = new LinkedOptionRequestModel();
                model.setId(mainOptionModelsList.get(i).get_id());
                model.setName(mainOptionModelsList.get(i).getName());
                list.add(model);
            }
        }
        try {
            return list.size() > 0 ? new JSONArray(new Gson().toJson(list)) : new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Click {
        public void onBack(View v) {
            finish();
        }

        public void onSave(View v) {
            if (binder.etName.getText().toString().trim().length() > 0) {
                if (binder.etPrice.getText().toString().trim().length() > 0) {
                    addUpdateOption();
                } else {
                    Toast.makeText(ActivityAddEditSubOption.this, "Please enter the best price", Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(ActivityAddEditSubOption.this, "Please enter the menu name", Toast.LENGTH_SHORT).show();
            }


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
            DatePickerDialog dp = new DatePickerDialog(
                    ActivityAddEditSubOption.this,
                    (view, year, monthOfYear, dayOfMonth) -> binder.tvDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth),
                    mYear, mMonth, mDay);
            dp.show();
            dp.getDatePicker().setMinDate(new Date().getTime());
        }

        public void onDel(View v) {
            del();
        }
    }
}
