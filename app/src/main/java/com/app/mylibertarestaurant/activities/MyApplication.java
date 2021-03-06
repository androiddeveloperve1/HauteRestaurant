package com.app.mylibertarestaurant.activities;

import android.app.Application;


import com.app.mylibertarestaurant.network.ApplicationComponent;
import com.app.mylibertarestaurant.network.DaggerApplicationComponent;
import com.app.mylibertarestaurant.network.NetworkModule;
import com.crashlytics.android.Crashlytics;

import java.io.File;

import io.fabric.sdk.android.Fabric;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */

public class MyApplication extends Application {
    private ApplicationComponent configuration;

    public void onCreate() {
        initDagger();
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }

    private void initDagger() {
        File cacheFile = new File(getCacheDir(), "responses");
        configuration = DaggerApplicationComponent.builder().networkModule(new NetworkModule(getApplicationContext(), cacheFile)).build();
    }


    public ApplicationComponent getConfiguration() {
        return configuration;
    }

}
