package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityEditServiceDaysBinding;

public class EditServiceDaysActivity extends AppCompatActivity  {
    ActivityEditServiceDaysBinding binder;
    private TimePickerDialog timeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_edit_service_days);
        initClick();
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
        binder.llMon.imgCheckbox.setOnClickListener((v) -> {
            if (binder.llMon.imgCheckbox.getTag().equals(getResources().getString(R.string.check_box_tag_no))) {
                binder.llMon.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_yes));
                binder.llMon.imgCheckbox.setImageResource(R.drawable.ic_check_box_on);
            } else {
                binder.llMon.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_no));
                binder.llMon.imgCheckbox.setImageResource(R.drawable.ic_check_box_off);
            }
        });


    }

    void clickTue() {
        binder.llTue.tvServiceDay.setText("Tuesday");
        binder.llTue.imgCheckbox.setOnClickListener((v) -> {
            if (binder.llTue.imgCheckbox.getTag().equals(getResources().getString(R.string.check_box_tag_no))) {
                binder.llTue.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_yes));
                binder.llTue.imgCheckbox.setImageResource(R.drawable.ic_check_box_on);
            } else {
                binder.llTue.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_no));
                binder.llTue.imgCheckbox.setImageResource(R.drawable.ic_check_box_off);
            }
        });


    }

    void clickWed() {
        binder.llWed.tvServiceDay.setText("Wednesday");
        binder.llWed.imgCheckbox.setOnClickListener((v) -> {
            if (binder.llWed.imgCheckbox.getTag().equals(getResources().getString(R.string.check_box_tag_no))) {
                binder.llWed.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_yes));
                binder.llWed.imgCheckbox.setImageResource(R.drawable.ic_check_box_on);
            } else {
                binder.llWed.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_no));
                binder.llWed.imgCheckbox.setImageResource(R.drawable.ic_check_box_off);
            }
        });


    }

    void clickThur() {
        binder.llThu.tvServiceDay.setText("Thursday");
        binder.llThu.imgCheckbox.setOnClickListener((v) -> {
            if (binder.llThu.imgCheckbox.getTag().equals(getResources().getString(R.string.check_box_tag_no))) {
                binder.llThu.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_yes));
                binder.llThu.imgCheckbox.setImageResource(R.drawable.ic_check_box_on);
            } else {
                binder.llThu.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_no));
                binder.llThu.imgCheckbox.setImageResource(R.drawable.ic_check_box_off);
            }
        });


    }

    void clickFri() {
        binder.llFri.tvServiceDay.setText("Friday");
        binder.llFri.imgCheckbox.setOnClickListener((v) -> {
            if (binder.llFri.imgCheckbox.getTag().equals(getResources().getString(R.string.check_box_tag_no))) {
                binder.llFri.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_yes));
                binder.llFri.imgCheckbox.setImageResource(R.drawable.ic_check_box_on);
            } else {
                binder.llFri.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_no));
                binder.llFri.imgCheckbox.setImageResource(R.drawable.ic_check_box_off);
            }
        });


    }

    void clickSat() {
        binder.llSat.tvServiceDay.setText("Saturday");
        binder.llSat.imgCheckbox.setOnClickListener((v) -> {
            if (binder.llSat.imgCheckbox.getTag().equals(getResources().getString(R.string.check_box_tag_no))) {
                binder.llSat.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_yes));
                binder.llSat.imgCheckbox.setImageResource(R.drawable.ic_check_box_on);
            } else {
                binder.llSat.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_no));
                binder.llSat.imgCheckbox.setImageResource(R.drawable.ic_check_box_off);
            }

        });


    }

    void clickSun() {
        binder.llSun.tvServiceDay.setText("Sunday");
        binder.llSun.imgCheckbox.setOnClickListener((v) -> {
            if (binder.llSun.imgCheckbox.getTag().equals(getResources().getString(R.string.check_box_tag_no))) {
                binder.llSun.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_yes));
                binder.llSun.imgCheckbox.setImageResource(R.drawable.ic_check_box_on);
            } else {
                binder.llSun.imgCheckbox.setTag(getResources().getString(R.string.check_box_tag_no));
                binder.llSun.imgCheckbox.setImageResource(R.drawable.ic_check_box_off);
            }
        });
    }



    public class Click {
        public void close(View v) {
            finish();
        }
    }
}
