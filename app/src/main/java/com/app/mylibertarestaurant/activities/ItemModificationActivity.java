package com.app.mylibertarestaurant.activities;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.adapter.DietaryItemAdapter;
import com.app.mylibertarestaurant.adapter.FoodAvailAdapter;
import com.app.mylibertarestaurant.adapter.WeekdayAdapter;
import com.app.mylibertarestaurant.databinding.ActivityItemModificationBinding;
import com.app.mylibertarestaurant.itnerfaces.RecycleItemClickListener;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.newP.DayOfWeekModel;
import com.app.mylibertarestaurant.model.newP.DietryLabelModel;
import com.app.mylibertarestaurant.model.newP.MealAvailabilityModel;
import com.app.mylibertarestaurant.model.newP.RestaurantCategoryItemModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.AppUtils;
import com.app.mylibertarestaurant.utils.MultipartUtils;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ItemModificationActivity extends ImageUploadingActivity {
    @Inject
    APIInterface apiInterface;
    private Bitmap restaurantImage;
    private ActivityItemModificationBinding binder;
    private boolean isEdit;
    private RestaurantCategoryItemModel data;
    private ArrayList<MealAvailabilityModel> foodAvailModelArrayList;
    private ArrayList<DietryLabelModel> dietaryItemModelArrayList;
    private FoodAvailAdapter foodAvailAdapter = null;
    private DietaryItemAdapter dietaryItemAdapter = null;
    private ArrayList<DayOfWeekModel> dayOfWeekModelArrayList;
    private WeekdayAdapter weekdayAdapter;
    private String catId;
    private Bitmap emptyBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_item_modification);
        binder.setClick(new Click());
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        dayOfWeekModelArrayList = AppUtils.getWeekDayArrayList();
        if (isEdit) {
            data = new Gson().fromJson(getIntent().getStringExtra("data"), RestaurantCategoryItemModel.class);
            catId = data.getCategory_id();
            Log.e("@@@@@@@", getIntent().getStringExtra("data"));
            binder.header.setText(data.getName());
            binder.tvSave.setText("Update");
            binder.etMax.setText(data.getMaxQuantity());
            binder.etMin.setText(data.getMinQuantity());
        } else {
            catId = getIntent().getStringExtra("catId");
            binder.header.setText("Add Item");
            binder.tvSave.setText("Save");
        }
        getFoodAvailibility();
        createEmptyBitmap();
    }

    @Override
    protected void onImageCaptured(Bitmap mBitmap) {
        restaurantImage = mBitmap;
        binder.imgPic.setImageBitmap(restaurantImage);
    }

    void fillData() {
        binder.etProductName.setText(data.getName());
        binder.tvDesc.setText(data.getDescription());
        binder.etProductPrice.setText(data.getPrice());
        try {
            Picasso.with(this).load(data.getImage()).into(getTarget());
        } catch (Exception e) {
        }
        if (data.getIsActive().equalsIgnoreCase("true")) {
            binder.tvProductAvailSwitch.setTag(getResources().getString(R.string.available));
            binder.tvProductAvailSwitch.setBackgroundResource(R.drawable.ic_toggle_available);
        } else {
            binder.tvProductAvailSwitch.setTag(getResources().getString(R.string.un_available));
            binder.tvProductAvailSwitch.setBackgroundResource(R.drawable.ic_toggle_unavailable);
        }

        if (data.getIsHidden().equalsIgnoreCase("true")) {
            binder.ivDisableDate.setTag(getResources().getString(R.string.available));
            binder.ivDisableDate.setBackgroundResource(R.drawable.ic_toggle_available);
            binder.llHideDateSection.setVisibility(View.VISIBLE);
            binder.tvDisableDate.setText(AppUtils.getDate(data.getHiddenDate()));
        } else {
            binder.ivDisableDate.setTag(getResources().getString(R.string.un_available));
            binder.ivDisableDate.setBackgroundResource(R.drawable.ic_toggle_unavailable);
            binder.llHideDateSection.setVisibility(View.GONE);
        }


        fillFoodavailibility();
        fillDietary();
        fillDayofWeek();
    }


    void fillDayofWeek() {
        StringBuilder workDay = new StringBuilder();
        for (int i = 0; i < dayOfWeekModelArrayList.size(); i++) {
            for (int j = 0; j < data.getDaysOfWeek().size(); j++) {
                if (dayOfWeekModelArrayList.get(i).getLabel().equalsIgnoreCase(data.getDaysOfWeek().get(j).getLabel())) {
                    if (data.getDaysOfWeek().get(j).getValue().equalsIgnoreCase("true")) {
                        dayOfWeekModelArrayList.get(i).setHasSelect(true);
                        dayOfWeekModelArrayList.get(i).setValue("true");
                        workDay.append(dayOfWeekModelArrayList.get(i).getLabel()).append(",");
                        break;
                    }

                }

            }
        }

        binder.tvAvailWeek.setText(workDay.toString());
    }

    void fillDietary() {
        StringBuilder dietary = new StringBuilder();
        for (int i = 0; i < dietaryItemModelArrayList.size(); i++) {
            for (int j = 0; j < data.getDietaryLabels().size(); j++) {
                if (dietaryItemModelArrayList.get(i).getName().equalsIgnoreCase(data.getDietaryLabels().get(j).getName())) {
                    if (data.getDietaryLabels().get(j).getValue().equalsIgnoreCase("true")) {
                        dietaryItemModelArrayList.get(i).setHasSelect(true);
                        dietary.append(dietaryItemModelArrayList.get(i).getName()).append(",");
                        break;
                    }
                }
            }
        }

        binder.tvDietLevel.setText(dietary.toString());
    }


    void fillFoodavailibility() {
        StringBuilder foodavailability = new StringBuilder();
        for (int i = 0; i < foodAvailModelArrayList.size(); i++) {
            for (int j = 0; j < data.getMealAvailability().size(); j++) {
                if (foodAvailModelArrayList.get(i).getName().equalsIgnoreCase(data.getMealAvailability().get(j).getName())) {
                    foodAvailModelArrayList.get(i).setHasSelect(true);
                    foodavailability.append(foodAvailModelArrayList.get(i).getName()).append(",");
                    break;
                }

            }
        }

        binder.tvAvailibility.setText(foodavailability.toString());
    }

    private Target getTarget() {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap result, Picasso.LoadedFrom from) {
                Log.e("@@@@@@", "Image loded");
                restaurantImage = result;
                binder.imgPic.setImageBitmap(restaurantImage);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e("@@@@@@", "Image failed");
                restaurantImage = null;
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.e("@@@@@@", "prepare Image loded");
            }
        };
        return target;
    }


    private void getFoodAvailibility() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemModificationActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ItemModificationActivity.this);
        apiInterface.getFoodAvailability()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<MealAvailabilityModel>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        getDietary();
                        ResponseDialog.showErrorDialog(ItemModificationActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<MealAvailabilityModel>> response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            foodAvailModelArrayList = response.getData();
                            getDietary();
                        } else {
                            ResponseDialog.showErrorDialog(ItemModificationActivity.this, response.getMessage());
                        }

                    }
                });
    }


    private void getDietary() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemModificationActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ItemModificationActivity.this);
        apiInterface.getDietary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel<ArrayList<DietryLabelModel>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ItemModificationActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel<ArrayList<DietryLabelModel>> response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            dietaryItemModelArrayList = response.getData();

                            if (isEdit) {
                                fillData();
                            }

                        } else {
                            ResponseDialog.showErrorDialog(ItemModificationActivity.this, response.getMessage());
                        }

                    }
                });
    }


    void showAvailabilityPopup(View v) {

        PopupWindow mPopupWindow;
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.tag_popup_view, null);
        mPopupWindow = new PopupWindow(customView, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        RecyclerView rv = customView.findViewById(R.id.rv_tag);
        rv.setLayoutManager(new LinearLayoutManager(ItemModificationActivity.this));
        foodAvailAdapter = new FoodAvailAdapter(new RecycleItemClickListener<MealAvailabilityModel>() {
            @Override
            public void onItemClicked(int position, MealAvailabilityModel data) {
                data.setHasSelect(!data.isHasSelect());
                foodAvailAdapter.notifyDataSetChanged();
                binder.tvAvailibility.setText("");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < foodAvailModelArrayList.size(); i++) {
                    if (foodAvailModelArrayList.get(i).isHasSelect()) {
                        sb.append(foodAvailModelArrayList.get(i).getName()).append(",");
                    }
                }
                binder.tvAvailibility.setText(sb.toString());
            }
        }, foodAvailModelArrayList);
        rv.setAdapter(foodAvailAdapter);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setWidth(margin());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mPopupWindow.showAsDropDown(v, Gravity.CENTER, 0, 0);
        }
    }

    void showDietaryPopup(View v) {
        PopupWindow mPopupWindow;
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.tag_popup_view, null);
        mPopupWindow = new PopupWindow(customView, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        RecyclerView rv = customView.findViewById(R.id.rv_tag);
        rv.setLayoutManager(new LinearLayoutManager(ItemModificationActivity.this));
        dietaryItemAdapter = new DietaryItemAdapter(new RecycleItemClickListener<DietryLabelModel>() {
            @Override
            public void onItemClicked(int position, DietryLabelModel data) {
                data.setHasSelect(!data.isHasSelect());
                dietaryItemAdapter.notifyDataSetChanged();
                binder.tvDietLevel.setText("");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < dietaryItemModelArrayList.size(); i++) {
                    if (dietaryItemModelArrayList.get(i).isHasSelect()) {
                        sb.append(dietaryItemModelArrayList.get(i).getName()).append(",");
                    }
                }
                binder.tvDietLevel.setText(sb.toString());
            }
        }, dietaryItemModelArrayList);
        rv.setAdapter(dietaryItemAdapter);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setWidth(margin());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mPopupWindow.showAsDropDown(v, Gravity.CENTER, 0, 0);
        }
    }

    void showWeekDayPopup(View v) {
        PopupWindow mPopupWindow;
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.tag_popup_view, null);
        mPopupWindow = new PopupWindow(customView, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        RecyclerView rv = customView.findViewById(R.id.rv_tag);
        rv.setLayoutManager(new LinearLayoutManager(ItemModificationActivity.this));
        weekdayAdapter = new WeekdayAdapter(new RecycleItemClickListener<DayOfWeekModel>() {
            @Override
            public void onItemClicked(int position, DayOfWeekModel data) {
                boolean b = data.isHasSelect();
                data.setHasSelect(!b);
                data.setValue("" + (!b));
                weekdayAdapter.notifyDataSetChanged();
                binder.tvAvailWeek.setText("");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < dayOfWeekModelArrayList.size(); i++) {
                    if (dayOfWeekModelArrayList.get(i).isHasSelect()) {
                        sb.append(dayOfWeekModelArrayList.get(i).getLabel()).append(",");
                    }
                }
                binder.tvAvailWeek.setText(sb.toString());
            }
        }, dayOfWeekModelArrayList);
        rv.setAdapter(weekdayAdapter);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setWidth(margin());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mPopupWindow.showAsDropDown(v, Gravity.CENTER, 0, 0);
        }
    }

    int margin() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return (size.x) - getResources().getDimensionPixelOffset(R.dimen._20_px);
    }

    public void onCalender(View v) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        if (binder.tvDisableDate.getText().toString().trim().length() > 0) {

            String date[] = binder.tvDisableDate.getText().toString().trim().split("-");
            mYear = Integer.parseInt(date[0]);
            mMonth = Integer.parseInt(date[1]) - 1;
            mDay = Integer.parseInt(date[2]);
        }


        DatePickerDialog dp = new DatePickerDialog(
                ItemModificationActivity.this,
                (view, year, monthOfYear, dayOfMonth) -> binder.tvDisableDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth),
                mYear, mMonth, mDay);
        dp.show();
        dp.getDatePicker().setMinDate(new Date().getTime());
    }

    void createEmptyBitmap() {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        emptyBitmap = Bitmap.createBitmap(1, 1, conf);
    }

    private void editFoodItem() {
        MultipartBody.Part image = null;
        if (restaurantImage == null) {
            image = MultipartUtils.createFile(ItemModificationActivity.this, emptyBitmap, "item_image", "food_image.jpg");
        } else {
            image = MultipartUtils.createFile(ItemModificationActivity.this, restaurantImage, "item_image", "food_image.jpg");
        }


        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), binder.etProductName.getText().toString().trim());
        RequestBody item_id = RequestBody.create(MediaType.parse("multipart/form-data"), data.get_id());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), binder.tvDesc.getText().toString().trim());


        RequestBody isUpdate = RequestBody.create(MediaType.parse("multipart/form-data"), "1");
        RequestBody restaurent_id = RequestBody.create(MediaType.parse("multipart/form-data"), MySharedPreference.getInstance(this).getUser().getRestaurants().get_id());
        RequestBody category_id = RequestBody.create(MediaType.parse("multipart/form-data"), catId);
        RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), binder.etProductPrice.getText().toString().trim());
        String isActive = "1";
        if (binder.tvProductAvailSwitch.getTag().equals(getResources().getString(R.string.un_available))) {
            isActive = "0";
        }
        RequestBody is_available = RequestBody.create(MediaType.parse("multipart/form-data"), isActive);

        RequestBody minQty = RequestBody.create(MediaType.parse("multipart/form-data"), binder.etMin.getText().toString().trim());
        RequestBody maxQty = RequestBody.create(MediaType.parse("multipart/form-data"), binder.etMax.getText().toString().trim());
        RequestBody mealAvail = RequestBody.create(MediaType.parse("multipart/form-data"), new Gson().toJson(getAvailList()));
        RequestBody dietaryLebel = RequestBody.create(MediaType.parse("multipart/form-data"), new Gson().toJson(getDietList()));
        RequestBody dayOfweek = RequestBody.create(MediaType.parse("multipart/form-data"), new Gson().toJson(dayOfWeekModelArrayList));
        RequestBody date = RequestBody.create(MediaType.parse("multipart/form-data"), binder.tvDisableDate.getText().toString().trim());
        String isHide = "false";
        if (binder.ivDisableDate.getTag().equals(getResources().getString(R.string.available))) {
            isHide = "true";
        }
        RequestBody isHidden = RequestBody.create(MediaType.parse("multipart/form-data"), isHide);

        RequestBody imageRemoved = null;
        if (restaurantImage == null) {
            imageRemoved = RequestBody.create(MediaType.parse("multipart/form-data"), "1");
        } else {
            imageRemoved = RequestBody.create(MediaType.parse("multipart/form-data"), "0");
        }


        final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemModificationActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ItemModificationActivity.this);
        apiInterface.editMenuItem(image, name, item_id, isUpdate, restaurent_id, category_id, price, is_available, mealAvail, minQty, maxQty, dietaryLebel, dayOfweek, date, isHidden, imageRemoved, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ItemModificationActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            Toast.makeText(ItemModificationActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(ItemModificationActivity.this, response.getMessage());
                        }

                    }
                });
    }


    ArrayList<DietryLabelModel> getDietList() {
        ArrayList<DietryLabelModel> list = new ArrayList<>();
        for (int i = 0; i < dietaryItemModelArrayList.size(); i++) {
            DietryLabelModel model = new DietryLabelModel();
            model.setDietary_id(dietaryItemModelArrayList.get(i).get_id());
            model.setValue("" + dietaryItemModelArrayList.get(i).isHasSelect());
            model.setName(dietaryItemModelArrayList.get(i).getName());
            list.add(model);

            /*if (dietaryItemModelArrayList.get(i).isHasSelect()) {
                model.setDietary_id(dietaryItemModelArrayList.get(i).get_id());
                model.setValue("" + dietaryItemModelArrayList.get(i).isHasSelect());
                model.setName(dietaryItemModelArrayList.get(i).getName());
                list.add(model);
            }*/


        }
        return list;

    }


    ArrayList<MealAvailabilityModel> getAvailList() {
        ArrayList<MealAvailabilityModel> list = new ArrayList<>();
        for (int i = 0; i < foodAvailModelArrayList.size(); i++) {
            MealAvailabilityModel model = new MealAvailabilityModel();
            if (foodAvailModelArrayList.get(i).isHasSelect()) {
                model.set_id(foodAvailModelArrayList.get(i).get_id());
                model.setName(foodAvailModelArrayList.get(i).getName());
                list.add(model);
            }


        }
        return list;


    }

    private void addFoodItem() {
        MultipartBody.Part image = null;
        if (restaurantImage == null) {
            image = MultipartUtils.createFile(ItemModificationActivity.this, emptyBitmap, "item_image", "food_image.jpg");
        } else {
            image = MultipartUtils.createFile(ItemModificationActivity.this, restaurantImage, "item_image", "food_image.jpg");
        }
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), binder.etProductName.getText().toString().trim());
        RequestBody restaurent_id = RequestBody.create(MediaType.parse("multipart/form-data"), MySharedPreference.getInstance(this).getUser().getRestaurants().get_id());
        RequestBody category_id = RequestBody.create(MediaType.parse("multipart/form-data"), catId);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), binder.tvDesc.getText().toString().trim());
        RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), binder.etProductPrice.getText().toString().trim());
        String isActive = "1";
        if (binder.tvProductAvailSwitch.getTag().equals(getResources().getString(R.string.un_available))) {
            isActive = "0";
        }
        RequestBody is_available = RequestBody.create(MediaType.parse("multipart/form-data"), isActive);
        RequestBody minQty = RequestBody.create(MediaType.parse("multipart/form-data"), binder.etMin.getText().toString().trim());
        RequestBody maxQty = RequestBody.create(MediaType.parse("multipart/form-data"), binder.etMax.getText().toString().trim());
        RequestBody date = RequestBody.create(MediaType.parse("multipart/form-data"), binder.tvDisableDate.getText().toString().trim());

        RequestBody mealAvail = RequestBody.create(MediaType.parse("multipart/form-data"), new Gson().toJson(getAvailList()));
        RequestBody dietaryLebel = RequestBody.create(MediaType.parse("multipart/form-data"), new Gson().toJson(getDietList()));
        RequestBody dayOfweek = RequestBody.create(MediaType.parse("multipart/form-data"), new Gson().toJson(dayOfWeekModelArrayList));


        String isHide = "false";
        if (binder.ivDisableDate.getTag().equals(getResources().getString(R.string.available))) {
            isHide = "true";
        }
        RequestBody isHidden = RequestBody.create(MediaType.parse("multipart/form-data"), isHide);


        final Dialog progressDialog = ResponseDialog.showProgressDialog(ItemModificationActivity.this);
        ((MyApplication) getApplication()).getConfiguration().inject(ItemModificationActivity.this);
        apiInterface.addFoodItem(image, name, restaurent_id, category_id, price, is_available, mealAvail, minQty, maxQty, dietaryLebel, dayOfweek, date, isHidden, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(ItemModificationActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@@@@", "" + new Gson().toJson(response.getData()));
                        if (response.getStatus().equals("200")) {
                            Toast.makeText(ItemModificationActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            ResponseDialog.showErrorDialog(ItemModificationActivity.this, response.getMessage());
                        }

                    }
                });
    }

    public class Click {
        public void onClose(View v) {
            finish();
        }

        public void onImageCapture(View v) {
            startCapture();
        }

        public void availbale(View v) {
            if (v.getTag().equals(getResources().getString(R.string.available))) {
                v.setTag(getResources().getString(R.string.un_available));
                v.setBackgroundResource(R.drawable.ic_toggle_unavailable);
            } else {
                v.setTag(getResources().getString(R.string.available));
                v.setBackgroundResource(R.drawable.ic_toggle_available);
            }
        }

        public void dateAvailable(View v) {
            if (v.getTag().equals(getResources().getString(R.string.available))) {
                v.setTag(getResources().getString(R.string.un_available));
                v.setBackgroundResource(R.drawable.ic_toggle_unavailable);
                binder.llHideDateSection.setVisibility(View.GONE);
            } else {
                v.setTag(getResources().getString(R.string.available));
                v.setBackgroundResource(R.drawable.ic_toggle_available);
                binder.llHideDateSection.setVisibility(View.VISIBLE);
            }
        }

        public void weekDayClick(View v) {
            showWeekDayPopup(v);
        }

        public void avail(View v) {
            showAvailabilityPopup(v);
        }

        public void dietLabel(View v) {
            showDietaryPopup(v);
        }

        public void hideDateSection(View v) {
            onCalender(v);
        }


        public void save(View v) {
            if (binder.etProductName.getText().toString().trim().length() <= 0) {
                Toast.makeText(ItemModificationActivity.this, "Please enter the item name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.etProductPrice.getText().toString().trim().length() <= 0) {
                Toast.makeText(ItemModificationActivity.this, "Please enter the item price", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.tvDesc.getText().toString().trim().length() <= 0) {
                Toast.makeText(ItemModificationActivity.this, "Please enter the item description", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.etMin.getText().toString().trim().length() <= 0) {
                Toast.makeText(ItemModificationActivity.this, "Please enter the minimum qty.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binder.etMin.getText().toString().trim().equalsIgnoreCase("0")) {
                Toast.makeText(ItemModificationActivity.this, "Minimum qty. should be 1 or more than 1", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Integer.parseInt(binder.etMin.getText().toString().trim()) > Integer.parseInt(binder.etMax.getText().toString().trim())) {
                Toast.makeText(ItemModificationActivity.this, "Max qty. can't be less than Min. qty. ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isEdit) {
                editFoodItem();
            } else {
                addFoodItem();
            }
        }
    }

}
