package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityEditServiceDaysBinding;

public class EditServiceDaysActivity extends AppCompatActivity {
    ActivityEditServiceDaysBinding binder;
    private TimePickerDialog timeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_edit_service_days);
        binder.setClick(new Click());
        initClick();
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


    void pickTime(TextView tv) {
        String time[] = tv.getText().toString().split(":");
        timeDialog = new TimePickerDialog(EditServiceDaysActivity.this, (timePicker, i, i1) -> {
            tv.setText(String.format("%02d:%02d", i, i1));
        }, Integer.parseInt(time[0]), Integer.parseInt(time[1]), true);
        timeDialog.show();
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
        binder.llMon.tvOpenTime1.setOnClickListener((v) -> {
            pickTime(binder.llMon.tvOpenTime1);
        });
        binder.llMon.tvOpenTime2.setOnClickListener((v) -> {
            pickTime(binder.llMon.tvOpenTime2);
        });
        binder.llMon.tvCloseTime1.setOnClickListener((v) -> {
            pickTime(binder.llMon.tvCloseTime1);
        });
        binder.llMon.tvCloseTime2.setOnClickListener((v) -> {
            pickTime(binder.llMon.tvCloseTime2);
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
        binder.llTue.tvOpenTime1.setOnClickListener((v) -> {
            pickTime(binder.llTue.tvOpenTime1);
        });
        binder.llTue.tvOpenTime2.setOnClickListener((v) -> {
            pickTime(binder.llTue.tvOpenTime2);
        });
        binder.llTue.tvCloseTime1.setOnClickListener((v) -> {
            pickTime(binder.llTue.tvCloseTime1);
        });
        binder.llTue.tvCloseTime2.setOnClickListener((v) -> {
            pickTime(binder.llTue.tvCloseTime2);
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
        binder.llWed.tvOpenTime1.setOnClickListener((v) -> {
            pickTime(binder.llWed.tvOpenTime1);
        });
        binder.llWed.tvOpenTime2.setOnClickListener((v) -> {
            pickTime(binder.llWed.tvOpenTime2);
        });
        binder.llWed.tvCloseTime1.setOnClickListener((v) -> {
            pickTime(binder.llWed.tvCloseTime1);
        });
        binder.llWed.tvCloseTime2.setOnClickListener((v) -> {
            pickTime(binder.llWed.tvCloseTime2);
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
        binder.llThu.tvOpenTime1.setOnClickListener((v) -> {
            pickTime(binder.llThu.tvOpenTime1);
        });
        binder.llThu.tvOpenTime2.setOnClickListener((v) -> {
            pickTime(binder.llThu.tvOpenTime2);
        });
        binder.llThu.tvCloseTime1.setOnClickListener((v) -> {
            pickTime(binder.llThu.tvCloseTime1);
        });
        binder.llThu.tvCloseTime2.setOnClickListener((v) -> {
            pickTime(binder.llThu.tvCloseTime2);
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
        binder.llFri.tvOpenTime1.setOnClickListener((v) -> {
            pickTime(binder.llFri.tvOpenTime1);
        });
        binder.llFri.tvOpenTime2.setOnClickListener((v) -> {
            pickTime(binder.llFri.tvOpenTime2);
        });
        binder.llFri.tvCloseTime1.setOnClickListener((v) -> {
            pickTime(binder.llFri.tvCloseTime1);
        });
        binder.llFri.tvCloseTime2.setOnClickListener((v) -> {
            pickTime(binder.llFri.tvCloseTime2);
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
        binder.llSat.tvOpenTime1.setOnClickListener((v) -> {
            pickTime(binder.llSat.tvOpenTime1);
        });
        binder.llSat.tvOpenTime2.setOnClickListener((v) -> {
            pickTime(binder.llSat.tvOpenTime2);
        });
        binder.llSat.tvCloseTime1.setOnClickListener((v) -> {
            pickTime(binder.llSat.tvCloseTime1);
        });
        binder.llSat.tvCloseTime2.setOnClickListener((v) -> {
            pickTime(binder.llSat.tvCloseTime2);
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
        binder.llSun.tvOpenTime1.setOnClickListener((v) -> {
            pickTime(binder.llSun.tvOpenTime1);
        });
        binder.llSun.tvOpenTime2.setOnClickListener((v) -> {
            pickTime(binder.llSun.tvOpenTime2);
        });
        binder.llSun.tvCloseTime1.setOnClickListener((v) -> {
            pickTime(binder.llSun.tvCloseTime1);
        });
        binder.llSun.tvCloseTime2.setOnClickListener((v) -> {
            pickTime(binder.llSun.tvCloseTime2);
        });
    }

    public class Click {
        public void close(View v) {
            finish();
        }
    }
}
