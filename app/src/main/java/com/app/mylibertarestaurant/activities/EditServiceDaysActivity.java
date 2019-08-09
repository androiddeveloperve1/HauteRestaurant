package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityEditServiceDaysBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.app.mylibertarestaurant.model.TimeModel;
import com.app.mylibertarestaurant.model.TimeSlotModel;
import com.app.mylibertarestaurant.model.TimeSlotUpdate;
import com.app.mylibertarestaurant.model.items.RestaurantDetail;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.AppUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditServiceDaysActivity extends AppCompatActivity {
    ActivityEditServiceDaysBinding binder;
    RestaurantDetailModel restaurantDetailModel;
    @Inject
    APIInterface apiInterface;

    void pickTime(TextView from, TextView to, int position) {
        restaurantDetailModel.getRestaurants().getOpenForService().get(position);
        from.setOnClickListener((v) -> {
            String time[] = from.getText().toString().split(":");
            TimePickerDialog timeDialog = new TimePickerDialog(from.getContext(), (timePicker, i, i1) -> {
                String timeSelected = String.format("%02d:%02d", i, i1);
                from.setText(timeSelected);
                restaurantDetailModel.getRestaurants().getOpenForService().get(position).getTimings().get(0).setOpenAt(timeSelected);
                restaurantDetailModel.getRestaurants().getOpenForService().get(position).getTimings().get(0).setCloseAt("");
                to.setText("");
            }, Integer.parseInt(time[0]), Integer.parseInt(time[1]), true);
            timeDialog.show();
        });


        to.setOnClickListener((v) -> {
            String time[] = from.getText().toString().split(":");
            TimePickerDialog timeDialog = new TimePickerDialog(to.getContext(), (timePicker, i, i1) -> {
                if (Integer.parseInt(time[0]) > i) {
                    Toast.makeText(this, "Start time can not be before from End time", Toast.LENGTH_SHORT).show();
                    restaurantDetailModel.getRestaurants().getOpenForService().get(position).getTimings().get(0).setCloseAt("");
                    to.setText("");
                } else if (Integer.parseInt(time[1]) > i1) {
                    Toast.makeText(this, "Start time can not be before from End time", Toast.LENGTH_SHORT).show();
                    restaurantDetailModel.getRestaurants().getOpenForService().get(position).getTimings().get(0).setCloseAt("");
                    to.setText("");
                } else {
                    restaurantDetailModel.getRestaurants().getOpenForService().get(position).getTimings().get(0).setCloseAt(String.format("%02d:%02d", i, i1));
                    to.setText(String.format("%02d:%02d", i, i1));
                }


            }, Integer.parseInt(time[0]), Integer.parseInt(time[1]), true);
            timeDialog.show();
        });

    }


    void checkBox(ImageView img, TimeModel model) {
        if (model.isIs_selected()) {
            img.setImageResource(R.drawable.ic_check_box_on);
        } else {
            img.setImageResource(R.drawable.ic_check_box_off);
        }

        img.setOnClickListener((v) -> {
            if (model.isIs_selected()) {
                model.setIs_selected(false);
                img.setImageResource(R.drawable.ic_check_box_off);
            } else {
                model.setIs_selected(true);
                img.setImageResource(R.drawable.ic_check_box_on);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_edit_service_days);
        restaurantDetailModel = MySharedPreference.getInstance(EditServiceDaysActivity.this).getUser();
        initData();
        binder.setClick(new Click());
    }

    void initClick() {
        clickMon();
        clickTue();
        clickWed();
        clickThur();
        clickFri();
        clickSat();
        clickSun();
    }

    void clickMon() {
        binder.llMon.tvServiceDay.setText("Monday");
        binder.llMon.setMyModel(restaurantDetailModel.getRestaurants().getOpenForService().get(0));
        checkBox(binder.llMon.imgCheckbox, restaurantDetailModel.getRestaurants().getOpenForService().get(0));

        binder.llMon.imgAddNewSlot.setOnClickListener((v) -> {
            binder.llMon.llSlot2.setVisibility(View.VISIBLE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(0).setIs_two_slot(true);

        });
        binder.llMon.imgRemoveNewSlot.setOnClickListener((v) -> {
            binder.llMon.llSlot2.setVisibility(View.GONE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(0).setIs_two_slot(false);
        });

        pickTime(binder.llMon.tvOpenTime1, binder.llMon.tvCloseTime1, 0);

    }

    void clickTue() {
        binder.llTue.tvServiceDay.setText("Tuesday");
        binder.llTue.setMyModel(restaurantDetailModel.getRestaurants().getOpenForService().get(1));
        checkBox(binder.llTue.imgCheckbox, restaurantDetailModel.getRestaurants().getOpenForService().get(1));
        binder.llTue.imgAddNewSlot.setOnClickListener((v) -> {
            binder.llTue.llSlot2.setVisibility(View.VISIBLE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(1).setIs_two_slot(true);

        });
        binder.llTue.imgRemoveNewSlot.setOnClickListener((v) -> {
            binder.llTue.llSlot2.setVisibility(View.GONE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(1).setIs_two_slot(false);
        });
        pickTime(binder.llTue.tvOpenTime1, binder.llTue.tvCloseTime1, 1);
    }

    void clickWed() {
        binder.llWed.tvServiceDay.setText("Wednesday");
        binder.llWed.setMyModel(restaurantDetailModel.getRestaurants().getOpenForService().get(2));
        checkBox(binder.llWed.imgCheckbox, restaurantDetailModel.getRestaurants().getOpenForService().get(2));
        binder.llWed.imgAddNewSlot.setOnClickListener((v) -> {
            binder.llWed.llSlot2.setVisibility(View.VISIBLE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(2).setIs_two_slot(true);

        });
        binder.llWed.imgRemoveNewSlot.setOnClickListener((v) -> {
            binder.llWed.llSlot2.setVisibility(View.GONE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(2).setIs_two_slot(false);
        });
        pickTime(binder.llWed.tvOpenTime1, binder.llWed.tvCloseTime1, 2);
    }

    void clickThur() {
        binder.llThu.tvServiceDay.setText("Thursday");
        binder.llThu.setMyModel(restaurantDetailModel.getRestaurants().getOpenForService().get(3));
        checkBox(binder.llThu.imgCheckbox, restaurantDetailModel.getRestaurants().getOpenForService().get(3));
        binder.llThu.imgAddNewSlot.setOnClickListener((v) -> {
            binder.llThu.llSlot2.setVisibility(View.VISIBLE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(3).setIs_two_slot(true);

        });
        binder.llThu.imgRemoveNewSlot.setOnClickListener((v) -> {
            binder.llThu.llSlot2.setVisibility(View.GONE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(3).setIs_two_slot(false);
        });
        pickTime(binder.llThu.tvOpenTime1, binder.llThu.tvCloseTime1, 3);
    }

    void clickFri() {
        binder.llFri.tvServiceDay.setText("Friday");
        binder.llFri.setMyModel(restaurantDetailModel.getRestaurants().getOpenForService().get(4));
        checkBox(binder.llFri.imgCheckbox, restaurantDetailModel.getRestaurants().getOpenForService().get(4));

        binder.llFri.imgAddNewSlot.setOnClickListener((v) -> {
            binder.llFri.llSlot2.setVisibility(View.VISIBLE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(4).setIs_two_slot(true);

        });
        binder.llFri.imgRemoveNewSlot.setOnClickListener((v) -> {
            binder.llFri.llSlot2.setVisibility(View.GONE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(4).setIs_two_slot(false);
        });
        pickTime(binder.llFri.tvOpenTime1, binder.llFri.tvCloseTime1, 4);
    }

    void clickSat() {
        binder.llSat.tvServiceDay.setText("Saturday");
        binder.llSat.setMyModel(restaurantDetailModel.getRestaurants().getOpenForService().get(5));
        checkBox(binder.llSat.imgCheckbox, restaurantDetailModel.getRestaurants().getOpenForService().get(5));
        binder.llSat.imgAddNewSlot.setOnClickListener((v) -> {
            binder.llSat.llSlot2.setVisibility(View.VISIBLE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(5).setIs_two_slot(true);

        });
        binder.llSat.imgRemoveNewSlot.setOnClickListener((v) -> {
            binder.llSat.llSlot2.setVisibility(View.GONE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(5).setIs_two_slot(false);
        });
        pickTime(binder.llSat.tvOpenTime1, binder.llSat.tvCloseTime1, 5);
    }

    void clickSun() {
        binder.llSun.tvServiceDay.setText("Sunday");

        binder.llSun.setMyModel(restaurantDetailModel.getRestaurants().getOpenForService().get(6));
        checkBox(binder.llSun.imgCheckbox, restaurantDetailModel.getRestaurants().getOpenForService().get(6));
        binder.llSun.imgAddNewSlot.setOnClickListener((v) -> {
            binder.llSun.llSlot2.setVisibility(View.VISIBLE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(6).setIs_two_slot(true);

        });
        binder.llSun.imgRemoveNewSlot.setOnClickListener((v) -> {
            binder.llSun.llSlot2.setVisibility(View.GONE);
            restaurantDetailModel.getRestaurants().getOpenForService().get(6).setIs_two_slot(false);
        });
        pickTime(binder.llSun.tvOpenTime1, binder.llSun.tvCloseTime1, 6);
    }

    void initData() {
        if (!(restaurantDetailModel.getRestaurants().getOpenForService() != null && restaurantDetailModel.getRestaurants().getOpenForService().size() > 0)) {
            restaurantDetailModel.getRestaurants().setOpenForService(AppUtils.initDummyTimeData());
        }
        setTimeToTextView();
        initClick();
    }

    void setTimeToTextView() {
        binder.llMon.tvOpenTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(0).getTimings().get(0).getOpenAt());
        binder.llMon.tvCloseTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(0).getTimings().get(0).getCloseAt());

        binder.llTue.tvOpenTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(1).getTimings().get(0).getOpenAt());
        binder.llTue.tvCloseTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(1).getTimings().get(0).getCloseAt());

        binder.llWed.tvOpenTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(2).getTimings().get(0).getOpenAt());
        binder.llWed.tvCloseTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(2).getTimings().get(0).getCloseAt());


        binder.llThu.tvOpenTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(3).getTimings().get(0).getOpenAt());
        binder.llThu.tvCloseTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(3).getTimings().get(0).getCloseAt());

        binder.llFri.tvOpenTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(4).getTimings().get(0).getOpenAt());
        binder.llFri.tvCloseTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(4).getTimings().get(0).getCloseAt());

        binder.llSat.tvOpenTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(5).getTimings().get(0).getOpenAt());
        binder.llSat.tvCloseTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(5).getTimings().get(0).getCloseAt());

        binder.llSun.tvOpenTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(6).getTimings().get(0).getOpenAt());
        binder.llSun.tvCloseTime1.setText(restaurantDetailModel.getRestaurants().getOpenForService().get(6).getTimings().get(0).getCloseAt());

    }

    private void updateTime() {
        TimeSlotUpdate detail = new TimeSlotUpdate();
        detail.setOpenForService(restaurantDetailModel.getRestaurants().getOpenForService());
        detail.setRestaurent_id(restaurantDetailModel.getRestaurants().get_id());
        final Dialog progressDialog = ResponseDialog.showProgressDialog(EditServiceDaysActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(EditServiceDaysActivity.this);
        apiInterface.updateSlot(detail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(EditServiceDaysActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            MySharedPreference.getInstance(EditServiceDaysActivity.this).setUser(restaurantDetailModel);
                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(EditServiceDaysActivity.this, response.getMessage());
                        }

                    }
                });
    }

    public class Click {

        public void finishNow(View v){
            finish();
        }
        public void close(View v) {
            boolean isTrue = true;
            for (int i = 0; i < restaurantDetailModel.getRestaurants().getOpenForService().size(); i++) {

                if (restaurantDetailModel.getRestaurants().getOpenForService().get(i).isIs_selected()) {

                    if (restaurantDetailModel.getRestaurants().getOpenForService().get(i).getTimings().get(0).getOpenAt().trim().length() <= 0 ||
                            restaurantDetailModel.getRestaurants().getOpenForService().get(i).getTimings().get(0).getCloseAt().trim().length() <= 0) {

                        Toast.makeText(EditServiceDaysActivity.this, "Please select the time", Toast.LENGTH_SHORT).show();
                        isTrue = false;
                        break;

                    }


                }

            }
            if (isTrue) {
                updateTime();
            }

            Log.e("@@@@", new Gson().toJson(restaurantDetailModel.getRestaurants().getOpenForService()));
        }
    }
}
