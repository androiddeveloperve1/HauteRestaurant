package com.app.mylibertarestaurant.activities;

import android.app.Application;


import com.app.mylibertarestaurant.network.ApplicationComponent;
import com.app.mylibertarestaurant.network.DaggerApplicationComponent;
import com.app.mylibertarestaurant.network.NetworkModule;

import java.io.File;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public class MyApplication extends Application {
    private ApplicationComponent configuration;

    public void onCreate() {
        initDagger();
        super.onCreate();
    }

    private void initDagger() {
        File cacheFile = new File(getCacheDir(), "responses");
        configuration = DaggerApplicationComponent.builder().networkModule(new NetworkModule(getApplicationContext(), cacheFile)).build();
    }


    public ApplicationComponent getConfiguration() {
        return configuration;
    }

}
