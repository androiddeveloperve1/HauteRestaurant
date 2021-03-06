package com.app.mylibertarestaurant.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.AvailibilityDayAdapter;
import com.app.mylibertarestaurant.adapter.DietaryLabelAdapter;
import com.app.mylibertarestaurant.adapter.FoodTypeAvailabilityAdapter;
import com.app.mylibertarestaurant.databinding.ActivityItemDescriptionBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.newP.DayOfWeekModel;
import com.app.mylibertarestaurant.model.newP.DietryLabelModel;
import com.app.mylibertarestaurant.model.newP.MainOptionModel;
import com.app.mylibertarestaurant.model.newP.MealAvailabilityModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryItemModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.utils.AppUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ItemDescriptionActivity extends AppCompatActivity {
    ActivityItemDescriptionBinding binder;
    PopupMenu popup;
    @Inject
    APIInterface apiInterface;
    private RestaurantCategoryItemModel data;
    private String menuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_item_description);
        binder.setClick(new Click());
        menuId = getIntent().getStringExtra("id");
        initMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMenuDetail();
    }

    void initData() {
        binder.setModel(data);
        binder.rvAvail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binder.rvAvail2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binder.rvDietLabel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        binder.setDayAdapter(new AvailibilityDayAdapter(getAvailableWeekDays()));
        binder.setFoodTypeAdapter(new FoodTypeAvailabilityAdapter(getDietary()));
        binder.setDietaryAdapter(new DietaryLabelAdapter(getDietaryLabel()));

        setAvailable(data.getIsActive());

        initOptionAndSubOption();
    }

    public void setAvailable(String tag) {
        if (tag != null) {
            if (tag.equals("true")) {
                binder.tvAvailablity.setBackgroundResource(R.drawable.available_bg);
                binder.tvAvailablity.setText("Available");
            } else {
                binder.tvAvailablity.setBackgroundResource(R.drawable.un_available_bg);
                binder.tvAvailablity.setText("Unavailable");
            }
        } else {
            binder.tvAvailablity.setBackgroundResource(R.drawable.un_available_bg);
            binder.tvAvailablity.setText("Unavailable");
        }


    }


    ArrayList<DayOfWeekModel> getAvailableWeekDays() {

        ArrayList<DayOfWeekModel> list = new ArrayList<>();
        for (int i = 0; i < data.getDaysOfWeek().size(); i++) {
            if (data.getDaysOfWeek().get(i).getValue().equals("true")) {
                list.add(data.getDaysOfWeek().get(i));
            }
        }
        return list;
    }


    ArrayList<MealAvailabilityModel> getDietary() {

        ArrayList<MealAvailabilityModel> list = new ArrayList<>();
        for (int i = 0; i < data.getMealAvailability().size(); i++) {
            try{
                if (data.getMealAvailability().get(i).getValue().equals("true")) {
                    list.add(data.getMealAvailability().get(i));
                }
            }catch (Exception e){}

        }
        return list;
    }
    ArrayList<DietryLabelModel> getDietaryLabel() {

        ArrayList<DietryLabelModel> list = new ArrayList<>();
        for (int i = 0; i < data.getDietaryLabels().size(); i++) {
            try{
                if (data.getDietaryLabels().get(i).getValue().equals("true")) {
                    list.add(data.getDietaryLabels().get(i));
                }
            }catch (Exception e){}

        }
        return list;
    }




    void initMenu() {
        popup = new PopupMenu(ItemDescriptionActivity.this, binder.ivThreeDot);
        popup.getMenuInflater().inflate(R.menu.pop_up, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent mIntent;
                switch (item.getItemId()) {
                    case R.id.edit:
                        mIntent = new Intent(ItemDescriptionActivity.this, ItemModificationActivity.class);
                        mIntent.putExtra("data", new Gson().toJson(data));
                        mIntent.putExtra("isEdit", true);
                        startActivity(mIntent);
                        break;
                    case R.id.del:
                        del();
                        break;
                }
                return false;
            }
        });

    }


    void del() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemDescriptionActivity.this);
        builder.setTitle("MyLiberta");
        builder.setMessage("Are you sure want to delete this Item?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemDescriptionActivity.this);
                ((MyApplication) getApplication()).getConfiguration().inject(ItemDescriptionActivity.this);
                apiInterface.delMenuItem(menuId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ApiResponseModel>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                progressDialog.dismiss();
                                ResponseDialog.showErrorDialog(ItemDescriptionActivity.this, throwable.getLocalizedMessage());
                            }

                            @Override
                            public void onNext(ApiResponseModel response) {
                                progressDialog.dismiss();
                                Log.e("@@@@@@@@", new Gson().toJson(response));
                                if (response.getStatus().equals("200")) {
                                    finish();
                                } else {
                                    ResponseDialog.showErrorDialog(ItemDescriptionActivity.this, response.getMessage());
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


    void initOptionAndSubOption() {
        binder.llOption.removeAllViews();
        if (data.getOptions() != null) {
            for (int i = 0; i < data.getOptions().size(); i++) {
                View mainOption = getLayoutInflater().inflate(R.layout.item_option_show, null);
                mainOption.setId(i);
                TextView tv = mainOption.findViewById(R.id.tv_text);
                MainOptionModel mainOptionModel = data.getOptions().get(i);
                tv.setText(mainOptionModel.getName());
                LinearLayout ll_sub_option = mainOption.findViewById(R.id.ll_sub_option);
                for (int j = 0; j < mainOptionModel.getSuboptions().size(); j++) {
                    View subOption = getLayoutInflater().inflate(R.layout.item_sub_option_show, null);
                    subOption.setId(j);
                    TextView tv_name = subOption.findViewById(R.id.tv_name);
                    tv_name.setText(mainOptionModel.getSuboptions().get(j).getName() + " ($" + AppUtils.getDecimalFormat(mainOptionModel.getSuboptions().get(j).getBestPrice()) + ")");
                    ll_sub_option.addView(subOption);
                }
                binder.llOption.addView(mainOption);

            }
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            finish();
        }

    }

    private void getMenuDetail() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemDescriptionActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ItemDescriptionActivity.this);
        apiInterface.getMenuDetail(menuId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<RestaurantCategoryItemModel>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ItemDescriptionActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<RestaurantCategoryItemModel>> response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@", new Gson().toJson(response));
                        if (response.getStatus().equals("200")) {
                            data = response.getData().get(0);
                            initData();
                        } else {
                            ResponseDialog.showErrorDialog(ItemDescriptionActivity.this, response.getMessage());
                        }
                    }
                });
    }

    public class Click {
        public void onBack(View v) {
            finish();
        }

        public void goOptionDetail(View v) {
            Intent mIntent = new Intent(ItemDescriptionActivity.this, OptionDetailsActivity.class);
            mIntent.putExtra("data", new Gson().toJson(data));
            startActivity(mIntent);

        }

        public void onPopupClick(View v) {
            popup.show();
        }
    }
}
