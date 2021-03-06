package com.app.mylibertarestaurant.prefes;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.mylibertarestaurant.constants.Constants;
import com.app.mylibertarestaurant.model.RestaurantDetailModel;
import com.google.gson.Gson;


/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */
public class MySharedPreference {

    public static final String APP_PREFERENCE = "haute_restaurantr_ve";
    private static final String User_Detail = "user_data";
    private static final String USER_SESSION_TOKEN = "user_session_token";
    private static final String FCM_TOKEN = "fcm_token";
    private static final String filter_type = "filter_type";


    private static MySharedPreference instence;
    private static Context mContext;
    private static SharedPreferences sharedpreferences;


    public MySharedPreference(Context mContext) {
        this.mContext = mContext;
        sharedpreferences = mContext.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
    }


    public static MySharedPreference getInstance(Context mContext) {
        if (instence == null) {
            instence = new MySharedPreference(mContext);
        }
        return instence;
    }


    public void clearMyPreference() {
        sharedpreferences.edit().clear().commit();
    }


    public String getSessionToken() {
        return sharedpreferences.getString(USER_SESSION_TOKEN, null);
    }

    public void setSessionToken(String data) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USER_SESSION_TOKEN, data);
        editor.commit();
    }


    public String getFCM() {
        return sharedpreferences.getString(FCM_TOKEN, null);
    }

    public void setFCM(String data) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(FCM_TOKEN, data);
        editor.commit();
    }




    public RestaurantDetailModel getUser() {
        return new Gson().fromJson(sharedpreferences.getString(User_Detail, null), RestaurantDetailModel.class);
    }

    public void setUser(RestaurantDetailModel data) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(User_Detail, new Gson().toJson(data));
        editor.commit();
    }

    public int getFilter() {
        return sharedpreferences.getInt(filter_type, Constants.FILTER_ALL);
    }

    public void setFilter(int data) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(filter_type, data);
        editor.commit();
    }

}
