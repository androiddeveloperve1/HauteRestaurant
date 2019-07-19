package com.app.mylibertarestaurant.network;


import com.app.mylibertarestaurant.activities.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Create By Rahul Mangal
 * Project Haute Delivery
 */
@Singleton
@Component(modules = {NetworkModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity model);


}
