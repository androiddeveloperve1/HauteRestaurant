package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityAddEditOptionBinding;
import com.app.mylibertarestaurant.model.ApiResponseModel;
import com.app.mylibertarestaurant.model.newP.MainOptionModel;
import com.app.mylibertarestaurant.network.APIInterface;
import com.app.mylibertarestaurant.prefes.MySharedPreference;
import com.app.mylibertarestaurant.utils.ResponseDialog;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddEditOptionActivity extends AppCompatActivity {
    @Inject
    APIInterface apiInterface;
    private ActivityAddEditOptionBinding binder;
    private MainOptionModel mainOptionModel = new MainOptionModel();
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_option);
        binder.setClick(new Click());
        isEdit = getIntent().getBooleanExtra("edit", false);
        if (isEdit) {
            mainOptionModel = new Gson().fromJson(getIntent().getStringExtra("data"), MainOptionModel.class);
        } else {
            mainOptionModel.setCustomerPrompt("");
            mainOptionModel.setName("");
            mainOptionModel.setMaxSelection("" + 1);
            mainOptionModel.setMinSelection("" + 0);
        }
        binder.setModel(mainOptionModel);
    }

    private void addUpdateOption() {
        final Dialog progressDialog = ResponseDialog.showProgressDialog(AddEditOptionActivity.this);
        HashMap<String, String> param = new HashMap<>();
        param.put("name", mainOptionModel.getName());
        param.put("customerPrompt", "" + mainOptionModel.getCustomerPrompt());
        param.put("minSelection", "" + mainOptionModel.getMinSelection());
        param.put("maxSelection", "" + mainOptionModel.getMaxSelection());
        param.put("restaurent_id", "" + MySharedPreference.getInstance(this).getUser().getRestaurants().get_id());
        param.put("location", "" + mainOptionModel.getLocation());
        param.put("item_id", "" + mainOptionModel.getItem_id());
        param.put("category_id", "" + mainOptionModel.getCategory_id());
        if (isEdit) {
            param.put("is_update", "1");
            param.put("option_id", mainOptionModel.get_id());
        }
        ((MyApplication) AddEditOptionActivity.this.getApplication()).getConfiguration().inject(AddEditOptionActivity.this);
        apiInterface.updateMenuStatus(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        progressDialog.dismiss();
                        ResponseDialog.showErrorDialog(AddEditOptionActivity.this, throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ApiResponseModel response) {
                        progressDialog.dismiss();
                        Log.e("@@@@@@@@", new Gson().toJson(response));
                        if (response.getStatus().equals("200")) {
                            Toast.makeText(AddEditOptionActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            ResponseDialog.showErrorDialog(AddEditOptionActivity.this, response.getMessage());
                        }

                    }
                });
    }

    public class Click {
        public void onBack(View v) {
            finish();
        }
        public void onSave(View v) {
            if (mainOptionModel.getName().trim().length() > 0) {
                if (mainOptionModel.getCustomerPrompt().trim().length() > 0) {
                    if (mainOptionModel.getMinSelection().trim().length() > 0) {
                        if (mainOptionModel.getMaxSelection().trim().length() > 0) {
                            addUpdateOption();
                        } else {
                            Toast.makeText(AddEditOptionActivity.this, "Please enter the maximum selection value", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddEditOptionActivity.this, "Please enter the minimum selection value", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddEditOptionActivity.this, "Please enter the customer prompt", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(AddEditOptionActivity.this, "Please enter the title of option", Toast.LENGTH_SHORT).show();
            }


        }
    }

}
